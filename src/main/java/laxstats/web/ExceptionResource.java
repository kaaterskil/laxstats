package laxstats.web;

/**
 * {@code ExceptionResource} contains information about uncaught exceptions.
 */
public class ExceptionResource {

   private final String url;
   private final String message;

   /**
    * Creates an {@code ExceptionResource} with the given request URL and exception.
    * 
    * @param url
    * @param ex
    */
   public ExceptionResource(String url, Exception ex) {
      this.url = url;
      message = ex.getLocalizedMessage();
   }

   /**
    * Returns the request URL.
    * 
    * @return
    */
   public String getUrl() {
      return url;
   }

   /**
    * Returns the exception's localized message.
    * 
    * @return
    */
   public String getMessage() {
      return message;
   }

}
