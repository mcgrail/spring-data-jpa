package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;
import net.bull.javamelody.MonitoredWithSpring;

@SpringBootApplication
@EnableAsync
@MonitoredWithSpring
@Slf4j
public class SpringDataJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaApplication.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return args -> {
			// save a few customers
			repository.save(new Customer("Ryan", "Murray"));
			repository.save(new Customer("Susan", "Huff"));
			repository.save(new Customer("Brenna", "Marie"));
			repository.save(new Customer("Lauren", "Mary"));
			repository.save(new Customer("Ethan", "Michael"));
			repository.save(new Customer("Catherine", "Darcy"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = repository.findById(1L);
			log.info("Customer found with findById(1L):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Murray'):");
			log.info("--------------------------------------------");
			repository.findByLastName("Murray").forEach(foundCustomer -> log.info(foundCustomer.toString()));
			log.info("");
		};
	}

	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;
	}

}
