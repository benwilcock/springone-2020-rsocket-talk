package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootApplication
@Controller
public class RSocketServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RSocketServerApplication.class, args);
	}

	@MessageMapping("request-response")
	Mono<Message> requestResponse(final Message message) {
		log.info("Received request-response message: {}", message);
		return Mono.just(new Message("You said: " + message.getMessage()));
	}
}
