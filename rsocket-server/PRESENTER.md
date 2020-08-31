# Presenter Notes

## Part 1: Create the RSocket Server Project

1. Go to [start.spring.io](https://start.spring.io)
    1. `rsocket-starter`
    1. `lombok`
    
1. Download `rsc`

## Part 2: Request Response With RSocket

Demonstrate request-response behavior.

##### Add The Application Code

1. Add a Lombok `@Data` class called `Message`

1. Add `@Controller` annotation to `RSocketServerApplication`

1. Add method: `Mono<Message> requestResponse(final Message message)`
    1. Note: method signature controls behaviour (request, response)
    
1. Add annotation `@MessageMapping("request-response")` -- name could be anything
    
##### Test With RSC
 
1. Run the server

1. Use the `--request` option:
    1. `rsc --debug --request --data "{\"message\":\"Hello\"}" --route request-response --stacktrace tcp://localhost:7000`

1. Check the log message on the server: `Received request-response message: Message(message=Hello, created=1598864382)` 

> **NOTE:** Interaction is request-response, data is JSON, route is "request-response" as used in the message mapping.

##### Test With Java
 
1. Add `@SpringBootTest`

1. Add `@BeforeAll` - set up the RSocket `requester` (like a client)
    1. Spring supplies the `RSocketRequester.Builder`
    1. Spring supplied the `@LocalRSocketServerPort`
    1. Connect using the `Builder` - `connectTcp("localhost", port)`
    
1. `@Test`
    1. Use the `requester` to `.route("request-response")`, `data(new Message("TEST))`,`.retreiveMono()`
    1. Retrieve a `Mono<Message>` called `response`
    1. Use reactive testing's `StepVerifier`
        1. `create()` verifier using the `response`
        1. `consumeNextWith()` with lambda for `assertThat` (AssertJ)
        1. `verifyComplete()` expects test to be over
        
1. Run the tests
     
> **NOTE:** You may prefer to run these tests as Integration Tests as they do start a server.

## Part 3: Fire And Forget With RSocket

Demonstrate fire-and-forget behavior.

##### Add The Application Code

1. Add method `Mono<void> fireAndForget(final Message message)`
    1. Accepts `Message` returns `Mono<Void>`
1. Add `@MessageMapping` with route: `fire-and-forget`

##### Test With RSC

1. Run the server

1. Use the `--fnf` option:
    1. `rsc --debug --fnf --data "{\"message\":\"Hello\"}" --route fire-and-forget --stacktrace tcp://localhost:7000`

1. Check log message on server - `Received fire-and-forget request: Message(message=Hello, created=1598863560)`

##### Test With Java

1. Add `@Test` method for `fireAndForget()`
    1. Use the `requester` to `.route("fire-and-forget")`, `data(new Message("TEST))`, `.retreiveMono(Void.class)`
    1. Retrieve a `result`
    1. Use reactive testing's `StepVerifier`
        1. `create()` verifier using the `result`
        1. `verifyComplete()` expects test to be over    
1. Run the tests    

## Part 4: Request Stream With RSocket

Demonstrate request-stream behavior.

##### Add The Application Code

1. Add method: `Flux<Message> stream(final Message message)`

1. Add message mapping `@MessageMapping("request-stream")`

1. Return a `Flux`
    1. Flux has been indexed every second with `.interval(Duration.ofSeconds(1))`
    1. Indexed flux has been mapped to responses with `.map(index -> new Message("You said: " + message.getMessage() + ". Response #" + index))`
    1. Responses are logged to show what's happening.

##### Test With RSC

1. Run the server
1. Use the `--stream` option:
    1. `rsc --debug --stream --data "{\"message\":\"Hello\"}" --route request-stream --stacktrace tcp://localhost:7000`
1. Check log messages on server: `Received stream request: Message(message=Hello, created=1598868051)`

##### Test With Java

1. Add `@Test` method for `testRequestStream()`
    1. Use the `requester` to `.route("request-stream")`, `data(new Message("TEST))`, `.retreiveFlox(Message.class)`
    1. Retrieve a `stream`
    1. Use reactive testing's `StepVerifier`
        1. `create()` verifier using the `stream`
        1. `consumeNextWith()` - use lambda to assert message
        1. `expectNextCount(0)` - move on to next message
        1. `consumeNextWith()` - use lambda to assert message
        1. `thenCancel` - cancel the stream
        1. `verify` - were the expected signals received?
1. Run the tests

## Part 5: Channels (Stream-Stream) With RSocket

##### Add The Application Code

1. Add method: `Flux<Message> channel(final Flux<Integer> settings)`

1. Add message mapping `@MessageMapping("stream-stream")`

1. Return the same `Flux` (actually, it's mapped to a new flux)
    1. Log every setting received (inbound stream)
    1. Create new (outbound) flux for every setting received
    1. Index the new outbound flux using the duration in the setting
    1. Map the index to a `Message`
    1. Responses get logged to show what's happening.

##### Test With RSC

1. Run the server

1. Use the `--channel` option and interactive mode for `--data` (`-`):
    1. `rsc --debug --channel --data - --route stream-stream --stacktrace tcp://localhost:700`
    1. Ask for interval of 3 seconds
    1. Ask for interval of 1 seconds
    
1. Check log messages on server: 
    ``` bash
    Received stream-stream (channel) request...
    Requested interval is 3 seconds.
    onNext(Message(message=Stream Response #0, created=1598870524))
    Requested interval is 1 seconds.
    onNext(Message(message=Stream Response #0, created=1598870529))
    The client cancelled the channel.
    ```

##### Test With Java

1. Add `@Test` method for `testStreamGetsStream()`

1. Set up the test with 2 `Mono<Integer>` in one `Flux<Integer>` called `settings` (notice the timings)
    1. Use the `requester` to `.route("request-stream")`, `data(new Message("TEST))`, `.retreiveFlox(Message.class)`
    1. Retrieve a `Flux<Message>` called `stream`
        1. Route: `.route("stream-stream")`
        1. Data: `.data(settings)`
    1. Use reactive testing's `StepVerifier`
        1. `create()` verifier using the `stream`
        1. `consumeNextWith()` - use lambda to assert message
        1. `consumeNextWith()` - use lambda to assert message
        1. `thenCancel` - cancel the stream
        1. `verify` - were the expected signals received?
        
1. Run the tests