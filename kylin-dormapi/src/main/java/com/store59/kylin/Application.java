package com.store59.kylin;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext_common.xml")
public class Application {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);

		String name = ctx.getEnvironment().getProperty("name");
		String[] age = ctx.getEnvironment().getDefaultProfiles();
		for (String t : age) {
			System.out.println(t);
		}

		System.out.println("Let's inspect the beans provided by Spring Boot:"
				+ name);

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}

	}
}
