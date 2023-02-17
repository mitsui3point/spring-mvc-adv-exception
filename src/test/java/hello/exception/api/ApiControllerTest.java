package hello.exception.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiControllerTest extends TestRestTemplateExchanger {
    @LocalServerPort
    private Integer port;

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void getMemberTest() {
        //given
        String url = "/api/members/" + "user1";
        String expectedBody = "{\"memberId\":\"user1\",\"name\":\"hello user1\"}";

        //when
        String actualBody = getResponseEntity(url, GET, port).getBody();
        //then
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void getMemberExceptionTest() throws Exception {
        //given
        String url = "/api/members/" + "ex";
        //String expectedBody = "{\"message\":\"Request processing failed: java.lang.RuntimeException: 잘못된 사용자\",\"status\":500}";

        //when
        /**
         {@link BasicErrorController#errorHtml(HttpServletRequest, HttpServletResponse)}
         => {@link DefaultErrorViewResolver#resolveErrorView(HttpServletRequest, HttpStatus, Map)} => SERIES_VIEWS.containsKey(status.series())
         */
        HashMap actualBody = new ObjectMapper().readValue(
                getResponseEntity(url, GET, port).getBody(),
                HashMap.class
        );

        //then
        assertThat(actualBody).extracting("status").isEqualTo(500);
        assertThat(actualBody).extracting("error").isEqualTo("Internal Server Error");
        assertThat(actualBody).extracting("exception").isEqualTo("java.lang.RuntimeException");
        assertThat(actualBody).extracting("path").isEqualTo("/api/members/ex");
    }
}
