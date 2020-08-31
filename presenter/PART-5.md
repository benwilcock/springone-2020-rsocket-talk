# Part 5: Stream-Stream (Channels) With RSocket

Demonstrate channels (stream-in, stream-out) with RSocket.

## Add The Application Code

1. Add method: `Flux<Message> channel(final Flux<Integer> settings)`

1. Add message mapping `@MessageMapping("stream-stream")`

1. Return the same `Flux` (actually, it's mapped to a new flux)
    1. Log every setting received (inbound stream)
    1. Create new (outbound) flux for every setting received
    1. Index the new outbound flux using the duration in the setting
    1. Map the index to a `Message`
    1. Responses get logged to show what's happening.

## Test With RSC

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

## Test With Java

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
