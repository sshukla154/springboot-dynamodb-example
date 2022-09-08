package sshukla.learning.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@Configuration
@Profile("local")
public class LocalPropertyConfiguration implements IPropertyConfiguration {

    @Value("${dynamodb.endpoint.url}")
    private String dynamoDBEndpoint;

    @Value("${amazon.region}")
    private String region;

    @Override
    public String getEnv() {
        return "local";
    }

    public String getRegion() {
        return region;
    }

    public String getDynamoDBEndpoint() {
        return dynamoDBEndpoint;
    }

}
