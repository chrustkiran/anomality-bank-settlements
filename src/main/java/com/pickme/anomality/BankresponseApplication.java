package com.pickme.anomality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pickme.anomality"})
public class BankresponseApplication {


	private static final Logger logger = LogManager.getLogger(BankresponseApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BankresponseApplication.class, args);

	}

	public static Logger getLogger() {
		return logger;
	}
}
