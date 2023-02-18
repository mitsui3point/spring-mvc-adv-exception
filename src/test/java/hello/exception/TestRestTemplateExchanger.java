package hello.exception;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class TestRestTemplateExchanger {
    public abstract void addHeader(HttpHeaders headers);

    @LocalServerPort
    private Integer port;

    public ResponseEntity<String> getResponseEntity(String url, HttpMethod httpMethod) {
        return new TestRestTemplate().exchange(
                "http://localhost:%d%s".formatted(port, url),
                httpMethod,
                new HttpEntity<>("body", getHttpHeaders()),
                String.class
        );
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        addHeader(headers);
        return headers;
    }
}
