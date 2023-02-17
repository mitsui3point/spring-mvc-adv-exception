package hello.exception.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DefaultHandlerExceptionResolverTest extends TestRestTemplateExchanger {

    @LocalServerPort
    private Integer port;

    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    void defaultHandlerExceptionResolverTest() throws JsonProcessingException {
        //given
        String url = "/api/default-handler-ex?data=string";
        String exceptedException = "org.springframework.web.method.annotation.MethodArgumentTypeMismatchException";

        //when
        /**
         * {@link DefaultHandlerExceptionResolver#doResolveException(HttpServletRequest, HttpServletResponse, Object, Exception)}
         * => else if (ex instanceof TypeMismatchException) {
         */
        ResponseEntity<String> response = getResponseEntity(url, HttpMethod.GET, port);
        HttpStatusCode actualStatusCode = response.getStatusCode();
        Map actualBody = new ObjectMapper().readValue(response.getBody(), HashMap.class);

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actualBody).extracting("exception").isEqualTo(exceptedException);
    }
}
