package sshukla.learning.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */

@Configuration
@Profile("local")
public class LocalPropertyConfiguration {

    public String getEnv() {
        return "local";
    }

}
