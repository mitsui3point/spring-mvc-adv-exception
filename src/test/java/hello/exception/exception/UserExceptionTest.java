package hello.exception.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.List.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserExceptionTest extends TestRestTemplateExchanger {

    @LocalServerPort
    private Integer port;
    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void userExceptionTest() throws JsonProcessingException {
        //given
        String url = "/api/members/user-ex";
        //when
        ResponseEntity<String> response = getResponseEntity(url, GET, port);
        Map actualBody = new ObjectMapper().readValue(response.getBody(), HashMap.class);
        //then
        assertThat(actualBody).extracting("ex").isEqualTo("hello.exception.exception.UserException");
        assertThat(actualBody).extracting("message").isEqualTo("사용자 오류");
    }
}
