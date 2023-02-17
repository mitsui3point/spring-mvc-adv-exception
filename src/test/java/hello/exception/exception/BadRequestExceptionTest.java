package hello.exception.exception;

import hello.exception.TestRestTemplateExchanger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BadRequestExceptionTest extends TestRestTemplateExchanger {

    @LocalServerPort
    private Integer port;

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void responseStatusAnnotationTest() {
        //given
        String url = "/api/response-status-ex1";
        //when
        ResponseEntity<String> response = getResponseEntity(url, HttpMethod.GET, port);
        HttpStatusCode actualStatusCode = response.getStatusCode();

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
