package laxstats;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.session.web.http.HeaderHttpSessionStrategy;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import laxstats.web.thymeleaf.LocalDateFormatter;
import laxstats.web.thymeleaf.LocalDateTimeFormatter;
import laxstats.web.thymeleaf.MinuteSecondFormatter;

@Configuration
@EnableSpringDataWebSupport
public class AppConfiguration extends WebMvcConfigurerAdapter {

   /*---------- Validation ----------*/

   @Bean
   public Validator localValidatorFactoryBean() {
      return new LocalValidatorFactoryBean();
   }

   /*---------- Formatting/Conversion ----------*/

   @Bean
   public FormattingConversionService conversionService() {
      final FormattingConversionServiceFactoryBean bean =
               new FormattingConversionServiceFactoryBean();

      bean.setRegisterDefaultFormatters(true);
      bean.setFormatters(getFormatters());
      return bean.getObject();
   }

   private Set<Formatter<?>> getFormatters() {
      final Set<Formatter<?>> converters = new HashSet<Formatter<?>>();
      converters.add(new LocalDateTimeFormatter());
      converters.add(new LocalDateFormatter());
      converters.add(new MinuteSecondFormatter());
      return converters;
   }

   /*---------- Session ----------*/

   @Bean
   public HeaderHttpSessionStrategy sessionStrategy() {
      return new HeaderHttpSessionStrategy();
   }

   /*---------- Spring Mobile ----------*/

   @Bean
   public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
      return new DeviceResolverHandlerInterceptor();
   }

   @Bean
   public DeviceHandlerMethodArgumentResolver deviceHandlerMethodArgumentResolver() {
      return new DeviceHandlerMethodArgumentResolver();
   }

   @Override
   public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(deviceResolverHandlerInterceptor());
   }

   @Override
   public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
      argumentResolvers.add(deviceHandlerMethodArgumentResolver());
   }
}
