package com.example.SpringExercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringExerciseApplication.class, args);
	}

}
