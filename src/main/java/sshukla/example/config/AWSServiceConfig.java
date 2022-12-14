package sshukla.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import sshukla.example.util.CredentialUtility;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@Configuration
public class AWSServiceConfig {

    Logger log = LoggerFactory.getLogger(AWSServiceConfig.class);

    @Bean
    public DynamoDbClient amazonDynamoDB() {
        log.info("Initializing dynamoDB connection ");
        return DynamoDbClient.builder()
                .region(Region.EU_WEST_1)
                .credentialsProvider(CredentialUtility.getCredentials())
                .build();
    }

    @Bean
    public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
        return DynamoDbEnhancedClient.builder().dynamoDbClient(amazonDynamoDB()).build();
    }

}
