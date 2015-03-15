package laxstats;

import java.util.HashSet;
import java.util.Set;

import laxstats.web.seasons.SeasonFormValidator;
import laxstats.web.site.SiteFormValidator;
import laxstats.web.teamSeasons.TeamSeasonFormValidator;
import laxstats.web.teams.TeamFormValidator;
import laxstats.web.thymeleaf.LocalDateFormatter;
import laxstats.web.thymeleaf.LocalDateTimeFormatter;
import laxstats.web.thymeleaf.MinuteSecondFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.format.Formatter;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableSpringDataWebSupport
public class AppConfiguration extends WebMvcConfigurerAdapter {

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

   @Bean
   public Validator localValidatorFactoryBean() {
      return new LocalValidatorFactoryBean();
   }

   @Bean
   public Validator seasonValidator() {
      return new SeasonFormValidator();
   }

   @Bean
   public Validator siteValidator() {
      return new SiteFormValidator();
   }

   @Bean
   public Validator teamValidator() {
      return new TeamFormValidator();
   }

   @Bean
   public Validator teamSeasonValidator() {
      return new TeamSeasonFormValidator();
   }
}
