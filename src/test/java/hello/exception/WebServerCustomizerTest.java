package hello.exception;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.*;

public class WebServerCustomizerTest {
    @Test
    public void testCustomizer() {
        // Create a mock ConfigurableWebServerFactory and ErrorPage
        ConfigurableWebServerFactory factoryMock = mock(ConfigurableWebServerFactory.class);
        ErrorPage[] errorPages = {new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404"),
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500"),
                new ErrorPage(RuntimeException.class, "/error-page/500")};

        // Create an instance of your customizer
        WebServerFactoryCustomizer<ConfigurableWebServerFactory> customizer = new WebServerCustomizer();

        // Call the customize method of the customizer with the mock objects
        customizer.customize(factoryMock);

        // Verify that the customizer has set the error page to the expected status code
        verify(factoryMock, times(1)).addErrorPages(errorPages);
    }
}