package laxstats.web;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global exception handler to return validation errors to REST clients.
 *
 * @see http 
 *      ://www.javacodegeeks.com/2013/05/spring-from-the-trenches-adding-validation-to-a-rest-api
 *      .html
 */
@ControllerAdvice
public class RestValidationErrorHandler {

   private final MessageSource messageSource;

   @Autowired
   public RestValidationErrorHandler(MessageSource messageSource) {
      this.messageSource = messageSource;
   }

   @ExceptionHandler(value = MethodArgumentNotValidException.class)
   @ResponseStatus(value = HttpStatus.BAD_REQUEST)
   @ResponseBody
   public ValidationErrorResource handleValidationError(MethodArgumentNotValidException ex) {
      final BindingResult result = ex.getBindingResult();
      final List<FieldError> fieldErrors = result.getFieldErrors();

      return processFieldErrors(fieldErrors);
   }

   private ValidationErrorResource processFieldErrors(List<FieldError> errors) {
      final ValidationErrorResource resource = new ValidationErrorResource();

      for (final FieldError error : errors) {
         final String localizedErrorMessage = resolveLocalizedErrorMessage(error);
         resource.addFieldError(error.getField(), localizedErrorMessage);
      }
      return resource;
   }

   private String resolveLocalizedErrorMessage(FieldError error) {
      final Locale currentLocale = LocaleContextHolder.getLocale();
      final String localizedErrorMessage = messageSource.getMessage(error, currentLocale);

      return localizedErrorMessage;
   }

}
