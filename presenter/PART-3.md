# Part 3: Fire And Forget With RSocket

Demonstrate fire-and-forget behavior.

## Add The Application Code

1. Add method `Mono<void> fireAndForget(final Message message)`
    1. Accepts `Message` returns `Mono<Void>`
    
1. Add `@MessageMapping` with route: `fire-and-forget`

## Test With RSC

1. Run the server

1. Use the `--fnf` option:
    1. `rsc --debug --fnf --data "{\"message\":\"Hello\"}" --route fire-and-forget --stacktrace tcp://localhost:7000`

1. Check log message on server - `Received fire-and-forget request: Message(message=Hello, created=1598863560)`

## Test With Java

1. Add `@Test` method for `fireAndForget()`

    1. Use the `requester` to `.route("fire-and-forget")`, `data(new Message("TEST))`, `.retreiveMono(Void.class)`
    1. Retrieve a `result`
    1. Use reactive testing's `StepVerifier`
        1. `create()` verifier using the `result`
        1. `verifyComplete()` expects test to be over   

1. Run the tests    
