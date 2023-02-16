package hello.exception.api;

import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
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
    void getMemberTest() throws Exception {
        //given
        String url = "/api/members/" + "user1";
        String expectedBody = "{\"memberId\":\"user1\",\"name\":\"hello user1\"}";

        //when
        String actualBody = getResponseEntity(url, HttpMethod.GET, port).getBody();
        //then
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    void getMemberExceptionTest() throws Exception {
        //given
        String url = "/api/members/" + "ex";
        String expectedBody = "{\"message\":\"Request processing failed: java.lang.RuntimeException: 잘못된 사용자\",\"status\":500}";
        //when
        String actualBody = getResponseEntity(url, HttpMethod.GET, port).getBody();
        //then
        assertThat(actualBody).isEqualTo(expectedBody);
    }

}
