package laxstats.web;

import javax.servlet.http.HttpServletRequest;

import org.axonframework.repository.AggregateNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * {@code RestErrorHandler} handles uncaught exceptions thrown when a client sends a PUT request
 * with an invalid primary key.
 */
@ControllerAdvice
public class RestErrorHandler {

   /**
    * Returns an {@code ExceptionResource} with an HTTP status code of 403 containing information
    * about the invalid primary key and a localized message.
    *
    * @param req
    * @param ex
    * @return
    */
   @ResponseStatus(value = HttpStatus.BAD_REQUEST)
   @ExceptionHandler(value = { IllegalStateException.class, AggregateNotFoundException.class })
   @ResponseBody
   public ExceptionResource handleError(HttpServletRequest req, Exception ex) {
      final StringBuffer url = req.getRequestURL();
      return new ExceptionResource(url.toString(), ex);
   }
}
