package sshukla.learning.util;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;

/**
 * @author 'Seemant Shukla' on '08/09/2022'
 */
public class CredentialUtility {

    //TODO: UPDATE THE BELOW VARIABLES VALUE WITH CORRECT AWS CREDENTAILS
    public static final String AWS_ACCESS_KEY = "";
    public static final String AWS_SECRET_KEY = "";

    public static AwsCredentialsProvider getCredentails(){
        AwsCredentialsProvider awsCredentialsProvider = () -> new AwsCredentials() {
            @Override
            public String accessKeyId() {
                return AWS_ACCESS_KEY;
            }

            @Override
            public String secretAccessKey() {
                return AWS_SECRET_KEY;
            }
        };
        return awsCredentialsProvider;
    }

}
