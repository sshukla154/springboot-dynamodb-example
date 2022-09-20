package sshukla.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import sshukla.example.entity.Movie;

import java.util.*;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
@Component
@Slf4j
public class DynamoDbItemService {

    public Movie createMovie(DynamoDbClient ddb, String tableName, Movie movie) {
        Map<String, AttributeValue> itemMap = new HashMap<>();
        itemMap.put("filmId", AttributeValue.builder().s(movie.getFilmId()).build());
        itemMap.put("title", AttributeValue.builder().s(movie.getTitle()).build());
        itemMap.put("releaseYear", AttributeValue.builder().s(movie.getReleaseYear()).build());
        itemMap.put("rated", AttributeValue.builder().s(movie.getRated()).build());
        itemMap.put("released", AttributeValue.builder().s(movie.getReleased()).build());
        itemMap.put("runtime", AttributeValue.builder().s(movie.getRuntime()).build());
        itemMap.put("genres", AttributeValue.builder().ss(movie.getGenres()).build());//List
        itemMap.put("actors", AttributeValue.builder().ss(movie.getActors()).build());//List
        itemMap.put("director", AttributeValue.builder().s(movie.getDirector()).build());
        itemMap.put("plot", AttributeValue.builder().s(movie.getPlot()).build());
        itemMap.put("language", AttributeValue.builder().s(movie.getLanguage()).build());
        itemMap.put("country", AttributeValue.builder().s(movie.getCountry()).build());
        itemMap.put("awards", AttributeValue.builder().s(movie.getAwards()).build());
        itemMap.put("imdbRating", AttributeValue.builder().s(movie.getImdbRating()).build());


        try {
            PutItemResponse putItemResponse = ddb.putItem(PutItemRequest.builder().tableName(tableName).item(itemMap).build());
            log.info("{} was successfully created. The request id is {}", tableName, putItemResponse.responseMetadata().requestId());
        } catch (ResourceNotFoundException e) {
            log.error("Error: The Amazon DynamoDB table \" " + tableName + " can't be found.\n");
            throw new RuntimeException("Be sure that it exists and that you've typed its name correctly!");
        } catch (DynamoDbException e) {
            throw new RuntimeException("Exception occurred while saving movie : " + e.getMessage());
        }
        return null;
    }

    public void scanItems(DynamoDbClient ddb, String tableName) {

        try {
            ScanRequest scanRequest = ScanRequest.builder()
                    .tableName(tableName)
                    .build();

            ScanResponse response = ddb.scan(scanRequest);
            for (Map<String, AttributeValue> item : response.items()) {
                Set<String> keys = item.keySet();
                for (String key : keys) {
                    System.out.println("The key name is " + key + "\n");
                    System.out.println("The value is " + item.get(key).s());
                }
            }

        } catch (DynamoDbException e) {
            throw new RuntimeException("Exception occurred while fetching all movies : " + e.getMessage());
        }
    }

    public Set<Movie> scanItemsByEnhancedClient(DynamoDbEnhancedClient enhancedClient, String tableName) {
        Set<Movie> movieSet = new HashSet<>();
        try {
            // Create a DynamoDbTable object
            DynamoDbTable<Movie> moviesTable = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
            Iterator<Movie> results = moviesTable.scan().items().iterator();

            while (results.hasNext()) {
                Movie rec = results.next();
                movieSet.add(rec);
            }

        } catch (DynamoDbException e) {
            System.err.println(e.getMessage());
        }
        return movieSet;
    }

    public void updateMovie(DynamoDbClient dynamoDbClient, String tableName, Movie movie) {
    }

    public Movie getMovieById(DynamoDbClient dynamoDbClient, String tableName, String filmId, String title) {

        Map<String, AttributeValue> keyName = new HashMap<>();
        keyName.put("filmId", AttributeValue.builder().s(filmId).build());
        keyName.put("title", AttributeValue.builder().s(title).build());

        GetItemRequest getItemRequest = GetItemRequest.builder()
                .tableName(tableName)
                .key(keyName)
                .build();
        GetItemResponse getItemResponse = dynamoDbClient.getItem(getItemRequest);
        Map<String, AttributeValue> hello = getItemResponse.item();

        for (String item : hello.keySet()) {
            String value = hello.get(item).s();
            System.out.println(item + " : " + value);

        }

        return new Movie();
    }

    public Movie getMovieByIdByEnhancedClient(DynamoDbEnhancedClient enhancedClient, String tableName, String filmId, String title) {
        DynamoDbTable<Movie> moviesTable = enhancedClient.table(tableName, TableSchema.fromBean(Movie.class));
        return moviesTable.getItem(GetItemEnhancedRequest.builder().key(Key.builder().partitionValue(filmId).sortValue(title).build()).build());
    }
}
