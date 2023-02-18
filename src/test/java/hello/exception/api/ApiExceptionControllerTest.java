package hello.exception.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class ApiExceptionControllerTest extends TestRestTemplateExchanger {

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
        ResponseEntity<String> responseEntity = getResponseEntity(url, GET);

        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        String actualBody = responseEntity.getBody();

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.OK);
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void getMemberExceptionTest() throws Exception {
        //given
        String url = "/api/members/" + "ex";

        //when
        /**
         {@link BasicErrorController#errorHtml(HttpServletRequest, HttpServletResponse)}
         => {@link DefaultErrorViewResolver#resolveErrorView(HttpServletRequest, HttpStatus, Map)} => SERIES_VIEWS.containsKey(status.series())
         */
        HashMap actualBody = new ObjectMapper().readValue(
                getResponseEntity(url, GET).getBody(),
                HashMap.class
        );

        //then
        assertThat(actualBody).extracting("status").isEqualTo(500);
        assertThat(actualBody).extracting("error").isEqualTo("Internal Server Error");
        assertThat(actualBody).extracting("exception").isEqualTo("java.lang.RuntimeException");
        assertThat(actualBody).extracting("path").isEqualTo("/api/members/ex");
    }
}
