package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

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

	@MessageMapping("fire-and-forget")
	public Mono<Void> fireAndForget(final Message message) {
		log.info("Received fire-and-forget request: {}", message);
		return Mono.empty();
	}

	@MessageMapping("request-stream")
	Flux<Message> stream(final Message message) {
		log.info("Received stream request: {}", message);

		return Flux
				// create a new indexed Flux emitting one element every second
				.interval(Duration.ofSeconds(1))
				// create a Flux of new Messages using the indexed Flux
				.map(index -> new Message("You said: " + message.getMessage() + ". Response #" + index))
				// show what's happening
				.log();
	}
}