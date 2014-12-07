package laxstats.web.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;

/**
 * Wraps Spring Security's @AuthenticationPrincipal annotation for easier
 * referencing.
 */

@Target({ ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal
public @interface CurrentUser {

}
