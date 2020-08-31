# Part 4: Request Stream With RSocket

Demonstrate request-stream behavior.

## Add The Application Code

1. Add method: `Flux<Message> stream(final Message message)`

1. Add message mapping `@MessageMapping("request-stream")`

1. Return a `Flux`
    1. Flux has been indexed every second with `.interval(Duration.ofSeconds(1))`
    1. Indexed flux has been mapped to responses with `.map(index -> new Message("You said: " + message.getMessage() + ". Response #" + index))`
    1. Responses are logged to show what's happening.

## Test With RSC

1. Run the server

1. Use the `--stream` option:
    1. `rsc --debug --stream --data "{\"message\":\"Hello\"}" --route request-stream --stacktrace tcp://localhost:7000`

1. Check log messages on server: `Received stream request: Message(message=Hello, created=1598868051)`

## Test With Java

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
