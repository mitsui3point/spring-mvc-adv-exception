package hello.exception.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LogFilterTest {
    @InjectMocks
    private LogFilter logFilter;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private FilterChain chain;
    @Mock
    private FilterConfig config;

    @Test
    void logFilterTest() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/");
        logFilter.init(config);
        logFilter.doFilter(request, response, chain);
        logFilter.destroy();
        verify(chain).doFilter(request,response);
    }
}