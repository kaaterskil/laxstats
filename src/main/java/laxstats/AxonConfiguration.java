package laxstats;

import java.io.File;
import java.util.Arrays;

import laxstats.domain.people.Person;
import laxstats.domain.plays.PenaltyType;
import laxstats.domain.seasons.Season;
import laxstats.domain.teams.Team;
import laxstats.domain.users.User;

import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.annotation.AnnotationCommandHandlerBeanPostProcessor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.commandhandling.interceptors.BeanValidationInterceptor;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.SimpleEventBus;
import org.axonframework.eventhandling.annotation.AnnotationEventListenerBeanPostProcessor;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventstore.EventStore;
import org.axonframework.eventstore.fs.FileSystemEventStore;
import org.axonframework.eventstore.fs.SimpleEventFileResolver;
import org.axonframework.repository.Repository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AxonConfiguration {

	@Bean
	public CommandBus commandBus() {
		final SimpleCommandBus commandBus = new SimpleCommandBus();
		commandBus.setHandlerInterceptors(Arrays
				.asList(new BeanValidationInterceptor()));
		return commandBus;
	}

	@Bean
	public CommandGatewayFactoryBean<CommandGateway> commandGateway() {
		final CommandGatewayFactoryBean<CommandGateway> factory = new CommandGatewayFactoryBean<>();
		factory.setCommandBus(commandBus());
		return factory;
	}

	@Bean
	public EventStore eventStore() {
		return new FileSystemEventStore(new SimpleEventFileResolver(new File(
				"./events")));
	}

	@Bean
	public EventBus eventBus() {
		return new SimpleEventBus();
	}

	// ---------- Command Handlers ----------//

	@Bean
	public AnnotationCommandHandlerBeanPostProcessor annotationCommandHandlerBeanPostProcessor() {
		final AnnotationCommandHandlerBeanPostProcessor processor = new AnnotationCommandHandlerBeanPostProcessor();
		processor.setCommandBus(commandBus());
		return processor;
	}

	// ---------- Event Listeners ----------//

	@Bean
	public AnnotationEventListenerBeanPostProcessor annotationEventListenerBeanPostProcessor() {
		final AnnotationEventListenerBeanPostProcessor processor = new AnnotationEventListenerBeanPostProcessor();
		processor.setEventBus(eventBus());
		return processor;
	}

	// ---------- Repositories ----------//

	@Bean
	public Repository<Person> personRepository() {
		final EventSourcingRepository<Person> repository = new EventSourcingRepository<Person>(
				Person.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<Season> seasonRepository() {
		final EventSourcingRepository<Season> repository = new EventSourcingRepository<Season>(
				Season.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<Team> teamRepository() {
		final EventSourcingRepository<Team> repository = new EventSourcingRepository<Team>(
				Team.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<User> userRepository() {
		final EventSourcingRepository<User> repository = new EventSourcingRepository<User>(
				User.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}

	@Bean
	public Repository<PenaltyType> penaltyTypeRepository() {
		final EventSourcingRepository<PenaltyType> repository = new EventSourcingRepository<PenaltyType>(
				PenaltyType.class, eventStore());
		repository.setEventBus(eventBus());
		return repository;
	}
}
