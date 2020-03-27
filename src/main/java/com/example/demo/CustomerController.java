package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping("/")
	public String displayCustomers(Model model) throws InterruptedException, ExecutionException {
	    List<Customer> customers = new ArrayList<>();
		
		// Kick off multiple asynchronous lookups
	    log.info("Calling service to retreive customers.");
	    CompletableFuture<Customer> customer0 = customerService.getCustomer(1);
	    CompletableFuture<Customer> customer1 = customerService.getCustomer(2);
	    CompletableFuture<Customer> customer2 = customerService.getCustomer(4);
	    CompletableFuture<Customer> customer3 = customerService.getCustomer(5);
	    
        // Wait until they are all done
        CompletableFuture.allOf(customer0, customer1, customer2, customer3).join();
        
	    customers.add(customer0.get());
	    customers.add(customer1.get());
	    customers.add(customer2.get());
	    customers.add(customer3.get());
	    
	    log.info("Retrieved customers:  " + customers);
	    
	    model.addAttribute("customers", customers);
	    
		return "greeting";
	}

}
