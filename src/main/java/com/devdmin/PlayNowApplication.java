package com.devdmin;

import com.devdmin.core.model.User;
import com.devdmin.core.model.util.Gender;
import com.devdmin.core.service.UserService;
import com.devdmin.core.service.impl.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.time.LocalDate;

@SpringBootApplication
@EnableAspectJAutoProxy
public class PlayNowApplication {

	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(PlayNowApplication.class, args);
		//User user = new User("admin","admin",18,Gender.MALE,"email@email.com");

		//context.getBean(UserService.class).addUser(user);

	}

}
