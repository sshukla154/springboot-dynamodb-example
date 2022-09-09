package sshukla.learning.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import sshukla.learning.config.AWSServiceConfig;
import sshukla.learning.entity.Movie;
import sshukla.learning.service.DynamoDbTableService;
import sshukla.learning.service.MovieService;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private AWSServiceConfig awsServiceConfig;

    @Autowired
    private DynamoDbTableService dynamoDbTableService;

    public MovieServiceImpl(AWSServiceConfig awsServiceConfig, DynamoDbTableService dynamoDbTableService) {
        this.awsServiceConfig = awsServiceConfig;
        this.dynamoDbTableService = dynamoDbTableService;
    }

    @Override
    public Movie createMovieTable(String tableName, String partitionKey, String sortKey) {

        DynamoDbClient dynamoDbClient = awsServiceConfig.amazonDynamoDB();

        if (tableName==null || partitionKey==null)
            throw new RuntimeException("Table name and Partition key cannot be Null or blank");

        if(sortKey==null)
            dynamoDbTableService.createTable(dynamoDbClient, tableName, partitionKey);
        else
            dynamoDbTableService.compositeKeyCreateTable(dynamoDbClient, tableName, partitionKey, sortKey);

        return null;
    }
}
