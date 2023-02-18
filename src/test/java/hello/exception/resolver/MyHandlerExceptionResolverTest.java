package hello.exception.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import java.util.HashMap;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class MyHandlerExceptionResolverTest extends TestRestTemplateExchanger {


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
                getResponseEntity(url, GET).getBody(),
                HashMap.class
        );

        //then
        assertThat(actualBody).extracting("status").isEqualTo(400);
        assertThat(actualBody).extracting("error").isEqualTo("Bad Request");
        assertThat(actualBody).extracting("exception").isEqualTo("java.lang.IllegalArgumentException");
        assertThat(actualBody).extracting("path").isEqualTo("/api/members/bad");
    }
}
