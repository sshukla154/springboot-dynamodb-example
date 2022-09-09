package sshukla.learning.service;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
// snippet-start:[dynamodb.java2.create_table_composite_key.import]

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;
import sshukla.learning.util.CredentialUtility;
// snippet-end:[dynamodb.java2.create_table_composite_key.import]

/**
 * Before running this Java V2 code example, set up your development environment, including your credentials.
 * <p>
 * For more information, see the following documentation topic:
 * <p>
 * https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */

public class CompositeKeyCreateTable {

    public static void main(String[] args) {

        String tableName = "composite-key-table";
        Region region = Region.EU_WEST_1;
        DynamoDbClient ddb = DynamoDbClient.builder()
                .credentialsProvider(CredentialUtility.getCredentails())
                .region(region)
                .build();

        System.out.format("Creating Amazon DynamoDB table %s\n with a composite primary key:\n", tableName);
        System.out.format("* Language - partition key\n");
        System.out.format("* Greeting - sort key\n");
        String tableId = createTableComKey(ddb, tableName, "Language", "Greeting");
        System.out.println("The Amazon DynamoDB table Id value is " + tableId);
        ddb.close();
    }

    // snippet-start:[dynamodb.java2.create_table_composite_key.main]
    public static String createTableComKey(DynamoDbClient ddb, String tableName, String partitionKey, String sortKey) {
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
                        .writeCapacityUnits(5L).build())
                .tableName(tableName)
                .build();

        String tableId = "";
        try {
            CreateTableResponse result = ddb.createTable(request);
            tableId = result.tableDescription().tableId();
            return tableId;
        } catch (DynamoDbException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    // snippet-end:[dynamodb.java2.create_table_composite_key.main]
}
