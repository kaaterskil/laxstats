package laxstats.web;

import java.lang.reflect.InvocationTargetException;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.data.rest.webmvc.support.ExceptionMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

   /**
    * Catch-all for non-specified exceptions
    *
    * @param e
    * @return
    */
   @ExceptionHandler({ Exception.class })
   public @ResponseBody ResponseEntity<?> handleAnyException(Exception e) {
      return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
   }

   /**
    * Handle failures commonly thrown from code.
    *
    * @param throwable
    * @return
    */
   @ExceptionHandler({ InvocationTargetException.class, IllegalArgumentException.class,
      ClassCastException.class, ConversionFailedException.class })
   public @ResponseBody ResponseEntity<?> handleMiscFailures(Throwable throwable) {
      return errorResponse(throwable, HttpStatus.BAD_REQUEST);
   }

   protected ResponseEntity<ExceptionMessage> errorResponse(Throwable throwable,
      HttpStatus httpStatus)
   {
      if (throwable != null) {
         return response(new ExceptionMessage(throwable), httpStatus);
      }
      return response(null, httpStatus);
   }

   protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
      return new ResponseEntity<>(body, status);
   }
}
