package hello.exception.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

import java.util.Locale;

/**
 * resolve response status
 * {@link ResponseStatusExceptionResolver#doResolveException(HttpServletRequest, HttpServletResponse, Object, Exception)}
 * => {@link ResponseStatusExceptionResolver#resolveResponseStatus(ResponseStatus, HttpServletRequest, HttpServletResponse, Object, Exception)}
 * => response.sendError(), return new ModelAndView()
 *
 * search reason
 * {@link ResponseStatusExceptionResolver#applyStatusAndReason(int, String, HttpServletResponse)}
 * => {@link MessageSource#getMessage(String, Object[], Locale)}
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "error.bad")//잘못된 요청 오류.
public class BadRequestException extends RuntimeException {

}
