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

    @Override
    public String createTable(String tableName, String partitionKey, String sortKey) {

        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();

        if (tableName == null || partitionKey == null)
            throw new RuntimeException("Table name and Partition key cannot be Null or blank");

        String createdMovieTable = "";

        if (sortKey == null)
            createdMovieTable = dynamoDbTableService.createDDTable(dynamoDbClient, tableName, partitionKey);
        else
            createdMovieTable = dynamoDbTableService.compositeKeyCreateDDTable(dynamoDbClient, tableName, partitionKey, sortKey);

        return createdMovieTable;
    }

    @Override
    public void deleteTable(String tableName) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbTableService.deleteDynamoDBTable(dynamoDbClient, tableName);
    }

    @Override
    public Movie createMovie(String tableName, Movie movie) {
        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();
        dynamoDbItemService.createMovie(dynamoDbClient, tableName, movie);
        return null;
    }
}
