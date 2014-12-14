package laxstats;

import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity3.dialect.SpringSecurityDialect;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class ThymeleafConfiguration {

	@Bean
	public ServletContextTemplateResolver templateResolver() {
		final ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("classpath:/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(1);
		resolver.setCharacterEncoding("UTF-8");
		resolver.setCacheable(false);
		return resolver;
	}

	@Bean
	public SpringTemplateEngine templateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		templateEngine.addTemplateResolver(templateResolver());
		templateEngine.addDialect(new SpringSecurityDialect());
		templateEngine.addDialect(new LayoutDialect());
		return templateEngine;
	}
}
