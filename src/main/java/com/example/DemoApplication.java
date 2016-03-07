package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@SpringBootApplication
public class DemoApplication
{
	public static void main(String[] args)
	{
		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);

		System.out.println("=============\nBeans provided by Spring Boot:");
		Arrays.asList(applicationContext.getBeanDefinitionNames())
				.stream()
				.sorted()
				.forEach(System.out::println);
		System.out.println("=============");
	}
}

/*
TODO LIST
- Make sure expense statuses use name
- add logging (another mongo collection)

- delete - log that it happened

- update README
- tests
- extra credit
 */
