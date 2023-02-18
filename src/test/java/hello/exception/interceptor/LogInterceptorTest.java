package hello.exception.interceptor;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest
public class LogInterceptorTest {
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp() {
        mvc = webAppContextSetup(context).build();
    }

    @Test
    void preHandleAndAfterCompletionTest() throws Exception {
        //given
        MockHttpServletRequestBuilder requestBuilder = get("/error-404");
        //when
        mvc.perform(requestBuilder)
                .andDo(print());
        Optional<HandlerInterceptor> logInterceptor = getInterceptors(requestBuilder)
                .stream()
                .filter(interceptor -> interceptor instanceof LogInterceptor)
                .findFirst();
        //then
        assertThat(logInterceptor).isPresent();
    }

    private List<HandlerInterceptor> getInterceptors(RequestBuilder requestBuilder) throws Exception {
        MockHttpServletRequest request = requestBuilder.buildRequest(
                context.getServletContext()
        );

        return context.getBean(RequestMappingHandlerMapping.class)
                .getHandler(request)
                .getInterceptorList();
    }
}
