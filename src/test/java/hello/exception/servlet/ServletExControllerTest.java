package hello.exception.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServletExControllerTest {

    @LocalServerPort
    private Integer port;

    @Test
    void errorTest() {
        //given
        HashMap<String, HttpStatus> urlStatus = new HashMap<>();
        urlStatus.put("/error-ex", INTERNAL_SERVER_ERROR);
        urlStatus.put("/error-404", NOT_FOUND);
        urlStatus.put("/error-500", INTERNAL_SERVER_ERROR);
        urlStatus.put("/error-400", BAD_REQUEST);

        urlStatus.forEach((url, expectedStatus) -> {
            System.out.println("url = " + url + "================================================");
            //when
            ResponseEntity<String> responseEntity = getResponseEntity(url);
            HttpStatusCode actualStatusCode = responseEntity.getStatusCode();

            //then
            assertThat(actualStatusCode).isEqualTo(expectedStatus);
            System.out.println("//url = " + url + "================================================\n");
        });
    }

    private ResponseEntity<String> getResponseEntity(String url) {
        return new TestRestTemplate()
                .getForEntity("http://localhost:%d%s".formatted(port, url), String.class);
    }
}
