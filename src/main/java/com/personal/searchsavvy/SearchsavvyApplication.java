package com.personal.searchsavvy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@SpringBootApplication
@EnableTransactionManagement
public class SearchsavvyApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchsavvyApplication.class, args);
	}

}
