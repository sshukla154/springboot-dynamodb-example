package sshukla.learning.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import sshukla.learning.config.AWSServiceConfig;
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

    @Autowired
    public MovieServiceImpl(AWSServiceConfig awsServiceConfig, DynamoDbTableService dynamoDbTableService) {
        this.awsServiceConfig = awsServiceConfig;
        this.dynamoDbTableService = dynamoDbTableService;
    }

    @Override
    public String createMovieTable(String tableName, String partitionKey, String sortKey) {

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
}
