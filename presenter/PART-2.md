# Part 2: Request Response With RSocket

Demonstrate request-response behavior.

## Add The Application Code

1. Add a Lombok `@Data` class called `Message`

1. Add `@Controller` annotation to `RSocketServerApplication`

1. Add method: `Mono<Message> requestResponse(final Message message)`
    1. Note: method signature controls behaviour (request, response)
    
1. Add annotation `@MessageMapping("request-response")` -- name could be anything
    
## Test With RSC
 
1. Run the server

1. Use the `--request` option:
    1. `rsc --debug --request --data "{\"message\":\"Hello\"}" --route request-response --stacktrace tcp://localhost:7000`

1. Check the log message on the server: `Received request-response message: Message(message=Hello, created=1598864382)` 

> **NOTE:** Interaction is request-response, data is JSON, route is "request-response" as used in the message mapping.

## Test With Java
 
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
