package com.example.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RSocketServerApplicationTests {

	private static RSocketRequester requester;

	@BeforeAll
	public static void setupOnce(@Autowired RSocketRequester.Builder builder,
								 @LocalRSocketServerPort Integer port,
								 @Autowired RSocketStrategies strategies) {

		requester = builder
				.connectTcp("localhost", port)
				.block();
	}

	@Test
	public void testRequestGetsResponse() {
		// Send a request message
		Mono<Message> response = requester
				.route("request-response")
				.data(new Message("TEST"))
				.retrieveMono(Message.class);

		// Verify that the response message contains the expected data
		StepVerifier
				.create(response)
				.consumeNextWith(message -> {
					assertThat(message.getMessage()).isEqualTo("You said: TEST");
				})
				.verifyComplete();
	}
}
