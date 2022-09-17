import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import sshukla.example.SpringbootDynamoDbExampleApplication;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = SpringbootDynamoDbExampleApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringbootDynamoDbExampleApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    private static final String PATTERN_FORMAT = "dd.MM.yyyy.hh.mm.ss";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
            .withZone(ZoneId.systemDefault());

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/v1");
    }

    @Test
    public void testCreateMovieTable() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String httpUrl = baseUrl + "/table/";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(httpUrl)
                .queryParam("tableName", "my-dynamodb-table")
                .queryParam("partitionKey", "my-partition-key")
                .queryParam("sortKey", "my-sort-key")
                .queryParam("localSecondaryIndex", "my-local-index");
        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);
        String tableName = restTemplate.postForObject(builder.toUriString(), request, String.class);
        assertEquals("Table my-dynamodb-table, is created", tableName);
    }

    @Test
    public void testDeleteMovieTable() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String httpUrl = baseUrl + "/table/";
        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);
        restTemplate.delete(httpUrl + "my-dynamodb-table" + "/", request, Void.class);
    }

}
