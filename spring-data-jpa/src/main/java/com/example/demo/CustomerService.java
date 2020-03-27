package com.example.demo;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Async("threadPoolTaskExecutor")
	public CompletableFuture<Customer> getCustomer(long id) throws InterruptedException {

		log.info("Accepted service request for customer <" + id +">.");
		
		// Simulate delay
		Thread.sleep(1000);

		return CompletableFuture.completedFuture(customerRepository.findById(id));
	}

}
