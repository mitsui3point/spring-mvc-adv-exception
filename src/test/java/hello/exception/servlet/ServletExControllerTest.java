package hello.exception.servlet;

import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServletExControllerTest extends TestRestTemplateExchanger {

    @LocalServerPort
    private Integer port;

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
    }

    @Test
    void errorExTest() {
        //given
        String expectedBody = getResponseEntity("/error-page/500", HttpMethod.GET, port).getBody();

        //when
        ResponseEntity<String> responseEntity = getResponseEntity("/error-ex", HttpMethod.GET, port);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void error404Test() {
        //given
        String expectedBody = getResponseEntity("/error-page/404", HttpMethod.GET, port).getBody();

        //when
        ResponseEntity<String> responseEntity = getResponseEntity("/error-404", HttpMethod.GET, port);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(NOT_FOUND);
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void error500Test() {
        //given
        String expectedBody = getResponseEntity("/error-page/500", HttpMethod.GET, port).getBody();

        //when
        ResponseEntity<String> responseEntity = getResponseEntity("/error-500", HttpMethod.GET, port);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(INTERNAL_SERVER_ERROR);
        assertThat(actualBody).isEqualTo(expectedBody);
    }
}
