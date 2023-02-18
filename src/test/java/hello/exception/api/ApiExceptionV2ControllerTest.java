package hello.exception.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hello.exception.TestRestTemplateExchanger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

public class ApiExceptionV2ControllerTest extends TestRestTemplateExchanger {


    @Override
    public void addHeader(HttpHeaders headers) {
        headers.setAccept(of(APPLICATION_JSON));
    }

    @Test
    @DisplayName("회원 조회 API")
    void getMemberTest() {
        //given
        String url = "/api2/members/" + "user1";
        String expectedBody = "{\"memberId\":\"user1\",\"name\":\"hello user1\"}";

        //when
        String actualBody = getResponseEntity(url, GET).getBody();
        //then
        assertThat(actualBody).isEqualTo(expectedBody);
    }

    @Test
    @DisplayName("회원 조회 API Exception: @ExceptionHandler(ExceptionHandlerExceptionResolver)")
    void getMemberExceptionTest() throws Exception {
        //given
        String url = "/api2/members/" + "ex";

        //when
        ResponseEntity<String> responseEntity = getResponseEntity(url, GET);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        Map actualBody = new ObjectMapper().readValue(
                responseEntity.getBody(),
                HashMap.class
        );

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(actualBody).extracting("code").isEqualTo("EX");
        assertThat(actualBody).extracting("message").isEqualTo( "내부 오류");
    }

    @Test
    @DisplayName("회원 조회 API BadRequest: @ExceptionHandler(ExceptionHandlerExceptionResolver)")
    void getMemberBadRequestTest() throws Exception {
        //given
        String url = "/api2/members/" + "bad";
        //when
        ResponseEntity<String> responseEntity = getResponseEntity(url, GET);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        Map actualBody = new ObjectMapper().readValue(
                responseEntity.getBody(),
                HashMap.class
        );

        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actualBody).extracting("code").isEqualTo("BAD");
        assertThat(actualBody).extracting("message").isEqualTo("잘못된 입력 값");
    }

    @Test
    @DisplayName("회원 조회 API UserException: @ExceptionHandler(ExceptionHandlerExceptionResolver)")
    void getMemberUserExceptionTest() throws JsonProcessingException {
        //given
        String url = "/api2/members/user-ex";
        //when
        ResponseEntity<String> responseEntity = getResponseEntity(url, GET);
        HttpStatusCode actualStatusCode = responseEntity.getStatusCode();
        Map actualBody = new ObjectMapper().readValue(
                responseEntity.getBody(),
                HashMap.class
        );
        //then
        assertThat(actualStatusCode).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(actualBody).extracting("code").isEqualTo("USER-EX");
        assertThat(actualBody).extracting("message").isEqualTo("사용자 오류");
    }
}
