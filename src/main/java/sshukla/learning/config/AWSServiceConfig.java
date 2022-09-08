package sshukla.learning.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import sshukla.learning.util.CredentialUtility;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@Configuration
public class AWSServiceConfig {

    Logger log = LoggerFactory.getLogger(AWSServiceConfig.class);

    @Autowired
    IPropertyConfiguration property;

    @Bean
    public DynamoDbClient amazonDynamoDB() {
        if (property.getEnv().equals("local")) {
            LocalPropertyConfiguration props = (LocalPropertyConfiguration) property;
            log.info("Initializing Local dynamoDB connection with endpoint {}", props.getDynamoDBEndpoint());
            return DynamoDbClient.builder()
                    .region(Region.AP_EAST_1)
                    .credentialsProvider(CredentialUtility.getCredentails())
                    .build();

        }
        log.info("Initializing dynamoDB connection ");

        return DynamoDbClient.builder().build();
    }

}
