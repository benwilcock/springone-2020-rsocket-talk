package com.example.demo;

import io.rsocket.metadata.WellKnownMimeType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.rsocket.context.LocalRSocketServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.security.rsocket.metadata.SimpleAuthenticationEncoder;
import org.springframework.security.rsocket.metadata.UsernamePasswordMetadata;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RSocketServerApplicationTests {

	private static RSocketRequester requester;

	@BeforeAll
	public static void setupOnce(@Autowired RSocketRequester.Builder builder,
								 @LocalRSocketServerPort Integer port,
								 @Autowired RSocketStrategies strategies) {

		UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("user", "pass");
		MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString());

		requester = builder
				.setupMetadata(credentials, mimeType)
				.rsocketStrategies(b ->
						b.encoder(new SimpleAuthenticationEncoder()))
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

	@Test
	public void testFireAndForget() {
		// Send a fire-and-forget message
		Mono<Void> result = requester
				.route("fire-and-forget")
				.data(new Message("TEST"))
				.retrieveMono(Void.class);

		// Assert that the result is a completed Mono.
		StepVerifier
				.create(result)
				.verifyComplete();
	}

	@Test
	public void testRequestStream() {
		// Send a request message
		Flux<Message> stream = requester
				.route("request-stream")
				.data(new Message("TEST"))
				.retrieveFlux(Message.class);

		// Verify that the response messages contain the expected data
		StepVerifier
				.create(stream)
				.consumeNextWith(message -> {
					assertThat(message.getMessage()).isEqualTo("You said: TEST. Response #0");
				})
				.expectNextCount(0)
				.consumeNextWith(message -> {
					assertThat(message.getMessage()).isEqualTo("You said: TEST. Response #1");
				})
				.thenCancel()
				.verify();
	}

	@Test
	public void testStreamGetsStream() {
		// Create first setting after 0 seconds. Server starts sending after 2 seconds.
		Mono<Integer> setting1 = Mono.just(Integer.valueOf(2)).delayElement(Duration.ofSeconds(0));
		// Create next setting after 3 secconds. Server starts sending in after 1 second.
		Mono<Integer> setting2 = Mono.just(Integer.valueOf(1)).delayElement(Duration.ofSeconds(3));
		// Bundle settings into a Flux
		Flux<Integer> settings = Flux.concat(setting1, setting2);

		// Send a stream of request messages
		Flux<Message> stream = requester
				.route("stream-stream")
				.data(settings)
				.retrieveFlux(Message.class);

		// Verify that the response messages contain the expected data
		StepVerifier
				.create(stream)
				.consumeNextWith(message -> {
					assertThat(message.getMessage()).isEqualTo("Stream Response #0");
				})
				.consumeNextWith(message -> {
					assertThat(message.getMessage()).isEqualTo("Stream Response #0");
				})
				.thenCancel()
				.verify();
	}
}
