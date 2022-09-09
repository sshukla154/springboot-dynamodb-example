package sshukla.learning.service;

import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import software.amazon.awssdk.services.dynamodb.waiters.DynamoDbWaiter;

import java.util.List;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
@Service
public class DynamoDbTableService {

    public String createTable(DynamoDbClient ddb, String tableName, String partitionKey) {

        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                        .attributeName(partitionKey)
                        .attributeType(ScalarAttributeType.S)
                        .build())
                .keySchema(KeySchemaElement.builder()
                        .attributeName(partitionKey)
                        .keyType(KeyType.HASH)
                        .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(2L)
                        .writeCapacityUnits(2L)
                        .build())
                .tableName(tableName)
                .build();

        try {
            CreateTableResponse response = ddb.createTable(request);
            tableCreateWait(ddb, tableName);
            return response.tableDescription().tableName();
        } catch (DynamoDbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String compositeKeyCreateTable(DynamoDbClient ddb, String tableName, String partitionKey, String sortKey) {
        CreateTableRequest request = CreateTableRequest.builder()
                .attributeDefinitions(AttributeDefinition.builder()
                                .attributeName(partitionKey)
                                .attributeType(ScalarAttributeType.S)
                                .build(),
                        AttributeDefinition.builder()
                                .attributeName(sortKey)
                                .attributeType(ScalarAttributeType.S)
                                .build())
                .keySchema(KeySchemaElement.builder()
                                .attributeName(partitionKey)
                                .keyType(KeyType.HASH)
                                .build(),
                        KeySchemaElement.builder()
                                .attributeName(sortKey)
                                .keyType(KeyType.RANGE)
                                .build())
                .provisionedThroughput(ProvisionedThroughput.builder()
                        .readCapacityUnits(2L)
                        .writeCapacityUnits(2L).build())
                .tableName(tableName)
                .build();

        try {
            CreateTableResponse result = ddb.createTable(request);
            tableCreateWait(ddb, tableName);
            return result.tableDescription().tableName();
        } catch (DynamoDbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void listAllTables(DynamoDbClient ddb) {

        boolean moreTables = true;
        String lastName = null;

        while (moreTables) {
            try {
                ListTablesResponse response = null;
                if (lastName == null) {
                    ListTablesRequest request = ListTablesRequest.builder().build();
                    response = ddb.listTables(request);
                } else {
                    ListTablesRequest request = ListTablesRequest.builder()
                            .exclusiveStartTableName(lastName).build();
                    response = ddb.listTables(request);
                }

                List<String> tableNames = response.tableNames();
                if (tableNames.size() > 0) {
                    for (String curName : tableNames) {
                        System.out.format("* %s\n", curName);
                    }
                } else {
                    System.out.println("No tables found!");
                }

                lastName = response.lastEvaluatedTableName();
                if (lastName == null) {
                    moreTables = false;
                }

            } catch (DynamoDbException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        System.out.println("\nDone!");
    }

    public void deleteDynamoDBTable(DynamoDbClient ddb, String tableName) {

        DeleteTableRequest request = DeleteTableRequest.builder()
                .tableName(tableName)
                .build();

        try {
            ddb.deleteTable(request);

        } catch (DynamoDbException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println(tableName + " was successfully deleted!");
    }

    private void tableCreateWait(DynamoDbClient ddb, String tableName) {
        // Wait until the Amazon DynamoDB table is created.
        DynamoDbWaiter dbWaiter = ddb.waiter();
        dbWaiter.waitUntilTableExists(DescribeTableRequest.builder()
                .tableName(tableName)
                .build());
    }

}
