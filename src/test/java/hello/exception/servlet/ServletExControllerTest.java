package hello.exception.servlet;

import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class ServletExControllerTest extends TestRestTemplateExchanger {

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
    }

    @Test
    void errorExTest() {

        //when
        ResponseEntity<String> responseEntity = getResponseEntity("/error-ex", HttpMethod.GET);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();

        //then
        assertThat(actualStatusCode).isEqualTo(INTERNAL_SERVER_ERROR);
    }

    @Test
    void error404Test() {
        //given

        //when
        ResponseEntity<String> responseEntity = getResponseEntity("/error-404", HttpMethod.GET);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();

        //then
        assertThat(actualStatusCode).isEqualTo(NOT_FOUND);
    }

    @Test
    void error500Test() {
        //given

        //when
        ResponseEntity<String> responseEntity = getResponseEntity("/error-500", HttpMethod.GET);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();

        //then
        assertThat(actualStatusCode).isEqualTo(INTERNAL_SERVER_ERROR);
    }
}
