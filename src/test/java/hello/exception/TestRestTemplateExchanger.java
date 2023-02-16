package hello.exception;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

public abstract class TestRestTemplateExchanger {
    public abstract void addHeader(HttpHeaders headers);

    public ResponseEntity<String> getResponseEntity(String url, HttpMethod httpMethod, Integer port) {
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
