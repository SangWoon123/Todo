package com.gdsc.todo

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@SpringBootApplication
@EnableScheduling
@EnableFeignClients
@EnableCaching
class TodoApplication {

	static void main(String[] args) {
		SpringApplication.run(TodoApplication, args)
	}

}
