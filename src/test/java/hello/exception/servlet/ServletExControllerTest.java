package hello.exception.servlet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServletExControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void errorExTest() {
        //given
        String url = "/error-ex";
        String errorPageUrl = "/error-page/500";

        HttpStatus expectedStatus = INTERNAL_SERVER_ERROR;
        String expectedBody = getResponseEntity(errorPageUrl).getBody();

        //when
        ResponseEntity<String> responseEntity = getResponseEntity(url);

        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(expectedStatus);
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void error404Test() {
        //given
        String url = "/error-404";
        String errorPageUrl = "/error-page/404";

        HttpStatus expectedStatus = NOT_FOUND;
        String expectedBody = getResponseEntity(errorPageUrl).getBody();

        //when
        ResponseEntity<String> responseEntity = getResponseEntity(url);

        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(expectedStatus);
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void error500Test() {
        //given
        String url = "/error-500";
        String errorPageUrl = "/error-page/500";

        HttpStatus expectedStatus = INTERNAL_SERVER_ERROR;
        String expectedBody = getResponseEntity(errorPageUrl).getBody();

        //when
        ResponseEntity<String> responseEntity = getResponseEntity(url);

        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(expectedStatus);
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    private ResponseEntity<String> getResponseEntity(String url) {
        return new TestRestTemplate()
                .getForEntity("http://localhost:%d%s".formatted(port, url), String.class);
    }
}
