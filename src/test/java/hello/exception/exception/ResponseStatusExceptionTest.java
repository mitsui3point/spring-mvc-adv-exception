package hello.exception.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.MessageSource;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResponseStatusExceptionTest extends TestRestTemplateExchanger {
    @LocalServerPort
    private Integer port;

    @Autowired
    private MessageSource messageSource;

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void responseStatusExceptionTest() throws JsonProcessingException {
        //given
        String url = "/api/response-status-ex2";
        String expectedMessage = messageSource.getMessage("error.bad", null, Locale.KOREA);
        String exceptedException = "org.springframework.web.server.ResponseStatusException";

        //when
        ResponseEntity<String> response = getResponseEntity(url, HttpMethod.GET, port);
        HttpStatusCode actualStatusCode = response.getStatusCode();
        Map actualBody = new ObjectMapper().readValue(response.getBody(), HashMap.class);

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(actualBody).extracting("message").isEqualTo(expectedMessage);
        assertThat(actualBody).extracting("exception").isEqualTo(exceptedException);
    }
}
