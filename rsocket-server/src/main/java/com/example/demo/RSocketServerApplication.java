package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

	@PreAuthorize("hasRole('USER')")
	@MessageMapping("request-response")
	Mono<Message> requestResponse(final Message message, @AuthenticationPrincipal UserDetails user) {
		log.info("Received request-response message: {}", message);
		log.info("Request-response initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
		return Mono.just(new Message("You said: " + message.getMessage()));
	}

	@PreAuthorize("hasRole('USER')")
	@MessageMapping("fire-and-forget")
	public Mono<Void> fireAndForget(final Message message, @AuthenticationPrincipal UserDetails user) {
		log.info("Received fire-and-forget request: {}", message);
		log.info("Fire-and-forget initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
		return Mono.empty();
	}

	@PreAuthorize("hasRole('USER')")
	@MessageMapping("request-stream")
	Flux<Message> stream(final Message message, @AuthenticationPrincipal UserDetails user) {
		log.info("Received stream request: {}", message);
		log.info("Request-stream initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
		return Flux
				// create a new indexed Flux emitting one element every second
				.interval(Duration.ofSeconds(1))
				// create a Flux of new Messages using the indexed Flux
				.map(index -> new Message("You said: " + message.getMessage() + ". Response #" + index))
				// show what's happening
				.log();
	}

	@PreAuthorize("hasRole('USER')")
	@MessageMapping("stream-stream")
	Flux<Message> channel(final Flux<Integer> settings, @AuthenticationPrincipal UserDetails user) {
		log.info("Received stream-stream (channel) request...");
		log.info("Stream-stream (channel) initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities());
		return settings
				.doOnNext(setting -> log.info("Requested interval is {} seconds.", setting))
				.doOnCancel(() -> log.warn("The client cancelled the channel."))
				.switchMap(setting -> Flux.interval(Duration.ofSeconds(setting))
						.map(index -> new Message("Stream Response #" + index)))
				.log();
	}
}