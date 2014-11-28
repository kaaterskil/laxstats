package laxstats;

import java.util.Arrays;

import laxstats.domain.people.Person;
import laxstats.domain.people.PersonCommandHandler;
import laxstats.domain.seasons.Season;
import laxstats.domain.seasons.SeasonCommandHandler;
import laxstats.domain.teams.Team;
import laxstats.domain.teams.TeamCommandHandler;
import laxstats.domain.users.User;
import laxstats.domain.users.UserCommandHandler;
import laxstats.query.people.PersonListener;
import laxstats.query.season.SeasonListener;
import laxstats.query.teams.TeamListener;
import laxstats.query.users.UserListener;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.common.jpa.ContainerManagedEntityManagerProvider;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.jpa.JpaEventStore;
import org.axonframework.repository.Repository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AxonConfiguration {

	@Bean
	public CommandBus commandBus() {
		SimpleCommandBus commandBus = new SimpleCommandBus();
		commandBus.setHandlerInterceptors(Arrays
				.asList(new BeanValidationInterceptor()));
		return commandBus;
	}

	@Bean
	public CommandGatewayFactoryBean<CommandGateway> commandGateway() {
		CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
		factory.setCommandBus(commandBus());
		return factory;
	}

	@Bean
	public EventStore eventStore() {
		return new JpaEventStore(new ContainerManagedEntityManagerProvider());
	}

	@Bean
	public EventBus eventBus() {
		return new SimpleEventBus();
	}

	// ---------- Command Handlers ----------//

	@Bean
	public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
		AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
		processor.setCommandBus(commandBus());
		return processor;
	}
	
	//---------- Event Listeners ----------//

	@Bean
	public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
		AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
		processor.setEventBus(eventBus());
		return processor;
	}

	//---------- Repositories ----------//

	@Bean
	public Repository<Person> personRepository() {
		EventSourcingRepository<Person> repository = new EventSourcingRepository<Person>(
				Person.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<Season> seasonRepository() {
		EventSourcingRepository<Season> repository = new EventSourcingRepository<Season>(
				Season.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<Team> teamRepository() {
		EventSourcingRepository<Team> repository = new EventSourcingRepository<Team>(
				Team.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<User> userRepository() {
		EventSourcingRepository<User> repository = new EventSourcingRepository<User>(
				User.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}
}
