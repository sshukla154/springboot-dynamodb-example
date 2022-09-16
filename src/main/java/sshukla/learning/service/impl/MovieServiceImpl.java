package sshukla.learning.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import sshukla.learning.config.AWSServiceConfig;
import sshukla.learning.entity.Movie;
import sshukla.learning.service.DynamoDbItemService;
import sshukla.learning.service.DynamoDbTableService;
import sshukla.learning.service.MovieService;

import java.util.List;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private final AWSServiceConfig awsServiceConfig;

    private final DynamoDbTableService dynamoDbTableService;

    private final DynamoDbItemService dynamoDbItemService;

    @Autowired
    public MovieServiceImpl(AWSServiceConfig awsServiceConfig, DynamoDbTableService dynamoDbTableService, DynamoDbItemService dynamoDbItemService) {
        this.awsServiceConfig = awsServiceConfig;
        this.dynamoDbTableService = dynamoDbTableService;
        this.dynamoDbItemService = dynamoDbItemService;
    }

    public String createTable(String tableName, String partitionKey, String sortKey, String globalSecondaryIndex, String localSecondaryIndex) {

        if (globalSecondaryIndex != null)
            throw new RuntimeException("Sorry, we cannot verify this Global Secondary Index (GSI) service, please keep it blank!!! Its a paid service :-)");

        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();

        if (tableName == null || partitionKey == null)
            throw new RuntimeException("Table name and Partition key cannot be Null or blank");

        String createdMovieTable = "";

        if (sortKey == null)
            createdMovieTable = dynamoDbTableService.createDDTable(dynamoDbClient, tableName, partitionKey, localSecondaryIndex);
        else
            createdMovieTable = dynamoDbTableService.compositeKeyCreateDDTable(dynamoDbClient, tableName, partitionKey, sortKey, localSecondaryIndex);

        return createdMovieTable;
    }

    @Override
    public void deleteTable(String tableName) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbTableService.deleteDynamoDBTable(dynamoDbClient, tableName);
    }

    @Override
    public void createMovie(String tableName, Movie movie) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbItemService.createMovie(dynamoDbClient, tableName, movie);
    }

    @Override
    public void updateMovie(String tableName, Movie movie) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbItemService.updateMovie(dynamoDbClient, tableName, movie);
    }

    @Override
    public List<Movie> getAllMovies(String tableName) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbItemService.scanItems(dynamoDbClient, tableName);
        return null;
    }

    @Override
    public Movie getMovieById(String tableName, String filmId, String title) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbItemService.getMovieById(dynamoDbClient, tableName);
        return null;
    }
}
