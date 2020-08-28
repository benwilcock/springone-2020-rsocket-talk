# Presenter Notes

## Step 1: Create the Project

1. Go to [start.spring.io](https://start.spring.io)
    1. `rsocket-starter`
    1. `lombok`
1. Download `rsc`

## Step 2: Request Response With RSocket

1. Add annotation `@Controller`
1. Add method: `Mono<Message> requestResponse(final Message message)`
    1. method signature controls behaviour (request, response)
1. Add annotation `@MessageMapping("request-response")` -- name could be anything
    
##### Test with RSC
 
1. Run the server
1. Terminal, run:
    1. `rsc --debug --request --data "{\"message\":\"Hello\"}" --route request-response --stacktrace tcp://localhost:7000`

> **NOTE:** Interaction is request-response, data is JSON, route is "request-response" as used in the message mapping.

##### Test with Java
 
1. `@SpringBootTest`
1. `@BeforeAll` - set up the RSocket `requester` (like a client)
    1. Spring supplies the `RSocketRequester.Builder`
    1. Spring supplied the `@LocalRSocketServerPort`
    1. Connect using the `Builder` - `connectTcp("localhost", port)`
1. `@Test`
    1. Use the `requester` to `route`, `data`,`retreive`
    1. Retrieve a `Mono<Message>` called `response`
    1. Use reactive testing's `StepVerifier`
        1. `create()` verifier using the `response`
        1. `consumeNextWith()` with lambda for `assertThat` (AssertJ)
        1. `verifyComplete()` expects test to be over
1. Run the tests
     
> **NOTE:** You may prefer to run these tests as Integration Tests as they do start a server.