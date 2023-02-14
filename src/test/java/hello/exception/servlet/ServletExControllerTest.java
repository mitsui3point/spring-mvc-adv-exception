package hello.exception.servlet;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ServletExControllerTest {
    private MockMvc mvc;
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ServletExController()).build();
    }

    @Test
    void errorExTest() {
        //given
        String url = "/error-ex";

        //then
        assertThatThrownBy(() -> {
            //when
            mvc.perform(get(url))
                    .andExpect(status().is5xxServerError());
        }).hasCauseInstanceOf(RuntimeException.class);
    }

    @Test
    void error404Test() throws Exception {
        //given
        String url = "/error-404";

        //when
        String errorMessage = mvc.perform(get(url))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andReturn()
                .getResponse()
                .getErrorMessage();

        //then
        Assertions.assertThat(errorMessage).isEqualTo("404 오류");
    }

    @Test
    void error500Test() throws Exception {
        //given
        String url = "/error-500";

        //when
        mvc.perform(get(url))
                .andExpect(status().is5xxServerError());

        //then
    }
}
