package hello.exception.exception;

import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class BadRequestExceptionTest extends TestRestTemplateExchanger {

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void responseStatusAnnotationTest() {
        //given
        String url = "/api/response-status-ex1";
        //when
        ResponseEntity<String> response = getResponseEntity(url, HttpMethod.GET);
        HttpStatusCode actualStatusCode = response.getStatusCode();

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
