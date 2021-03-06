package laxstats.api.games;

/**
 * Exception thrown when the play service is unable to perform the requested action for some reason.
 */
public class InvalidPlayException extends RuntimeException {
   private static final long serialVersionUID = 5685624472612382641L;

   public InvalidPlayException() {
      super();
   }

   public InvalidPlayException(String message) {
      super(message);
   }

   public InvalidPlayException(Throwable cause) {
      super(cause);
   }

   public InvalidPlayException(String message, Throwable cause) {
      super(message, cause);
   }

   public InvalidPlayException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
      super(message, cause, enableSuppression, writableStackTrace);
   }

}
