package com.gdsc.todo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
class TodoApplication {

	static void main(String[] args) {
		SpringApplication.run(TodoApplication, args)
	}

}
