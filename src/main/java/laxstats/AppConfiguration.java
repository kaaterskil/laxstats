package laxstats;

import laxstats.web.seasons.SeasonFormValidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.joda.DateTimeFormatterFactoryBean;
import org.springframework.format.datetime.joda.JodaTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AppConfiguration extends WebMvcConfigurerAdapter {

	@Bean
	public FormattingConversionService conversionService() {
		final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();

		conversionService
				.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

		final DateTimeFormatterFactoryBean factory = new DateTimeFormatterFactoryBean();
		final JodaTimeFormatterRegistrar registrar = new JodaTimeFormatterRegistrar();

		factory.setPattern("MM/dd/yyyy");
		registrar.setDateFormatter(factory.createDateTimeFormatter());

		factory.setPattern("MM/dd/yyyy hh:mm aa");
		registrar.setDateTimeFormatter(factory.createDateTimeFormatter());

		factory.setPattern("mm:ss");
		registrar.setTimeFormatter(factory.createDateTimeFormatter());

		registrar.registerFormatters(conversionService);

		return conversionService;
	}

	@Bean
	public Validator localValidatorFactoryBean() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public Validator seasonValidator() {
		return new SeasonFormValidator();
	}
}
