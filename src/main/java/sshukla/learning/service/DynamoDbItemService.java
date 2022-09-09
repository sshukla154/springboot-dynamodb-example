package sshukla.learning.service;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import sshukla.learning.entity.Movie;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
@Component
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
            System.out.println(tableName + " was successfully created. The request id is " + putItemResponse.responseMetadata().requestId());
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The Amazon DynamoDB table \"%s\" can't be found.\n", tableName);
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
            e.printStackTrace();
        }
    }

}
