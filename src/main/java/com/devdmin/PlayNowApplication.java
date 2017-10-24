package com.devdmin;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class PlayNowApplication {

	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PlayNowApplication.class, args);
		User user = new User();
		user.setUsername("admin");
		user.setVerified(true);
		user.setSignUpDate(LocalDate.now());
		user.setEmail("damian.tzn@outlook.com");
		user.setPassword("admin");
		user.setGender(Gender.MALE);

		User user2 = new User();
		user2.setUsername("damian");
		user2.setVerified(true);
		user2.setSignUpDate(LocalDate.now());
		user2.setEmail("damian2.tzn@outlook.com");
		user2.setPassword("damian");
		user2.setGender(Gender.MALE);
		context.getBean(UserService.class).addUser(user);
		context.getBean(UserService.class).addUser(user2);
	}

}
