package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rsocket.server.LocalRSocketServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.List;

import static java.util.List.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MyHandlerExceptionResolverTest extends TestRestTemplateExchanger {

    @LocalServerPort
    private Integer port;

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void getMemberIllegalArgumentExceptionTest() throws Exception {
        //given
        String url = "/api/members/" + "bad";

        //when
        HashMap actualBody = new ObjectMapper().readValue(
                getResponseEntity(url, GET, port).getBody(),
                HashMap.class
        );

        //then
        assertThat(actualBody).extracting("status").isEqualTo(400);
        assertThat(actualBody).extracting("error").isEqualTo("Bad Request");
        assertThat(actualBody).extracting("exception").isEqualTo("java.lang.IllegalArgumentException");
        assertThat(actualBody).extracting("path").isEqualTo("/api/members/bad");
    }
}
