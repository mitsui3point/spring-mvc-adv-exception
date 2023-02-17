package hello.exception.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = UserHandlerExceptionResolver.class)
public class UserHandlerExceptionResolverTest extends TestRestTemplateExchanger {

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
        assertThat(actualBody).extracting("status").isEqualTo(500);
        assertThat(actualBody).extracting("error").isEqualTo("Internal Server Error");
        assertThat(actualBody).extracting("exception").isEqualTo("hello.exception.exception.UserException");
        assertThat(actualBody).extracting("path").isEqualTo(url);
    }
}
