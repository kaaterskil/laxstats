package laxstats;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Configuration class for RESTful web services to include CORS access control headers in the
 * response. This class will response to all requests with <code>Access-Control-*</code> headers set
 * to allow POST, PUT, GET, OPTIONS or DELETE requests from clients originated from any host. The
 * results of a pre-flight request may be cached for up to 3,600 seconds (1 hour), and the request
 * may include <code>x-requested-with</code>, <code>x-auth-token</code>n, <code>authorization</code>
 * and <code>content-type</code> headers.
 *
 * @see https://spring.io/guides/gs/rest-service-cors/
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCORSFilter implements Filter {
   static String ORIGIN = "Origin";

   @Override
   public void destroy() {
      // Noop
   }

   @Override
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
      throws IOException, ServletException
   {
      String origin = ((HttpServletRequest)req).getHeader(ORIGIN);
      origin = origin != null ? origin : "*";

      final HttpServletResponse response = (HttpServletResponse)res;
      response.setHeader("Access-Control-Allow-Origin", origin);
      response.setHeader("Access-Control-Allow-Methods", "HEAD, POST, PUT, GET, OPTIONS, DELETE");
      response.setHeader("Access-Control-Allow-Credentials", "true");
      response.setHeader("Access-Control-Allow-Headers",
         "x-auth-token, x-requested-with, authorization, content-type, origin, accept");
      response.setHeader("Access-Control-Expose-Headers", "x-auth-token");
      response.setHeader("Access-Control-Max-Age", "3600");

      final HttpServletRequest request = (HttpServletRequest)req;
      if (!request.getMethod().equals("OPTIONS")) {
         chain.doFilter(req, res);
      }
   }

   @Override
   public void init(FilterConfig arg0) throws ServletException {
      // Noop
   }

}
