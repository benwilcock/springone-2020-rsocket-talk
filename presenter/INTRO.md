# Getting Started With RSocket

RSocket is pretty awesome. It’s a fully reactive, message-driven, multi-modal, bidirectional communication protocol built with microservices in mind. But sometimes, being awesome just isn’t enough! Sometimes you need practical, step-by-step instructions on how to get started the easy way, with no complicated docs to read or sample code to grok. In this talk, Ben will tell you everything you need to know to get started with RSocket and a few tips and tricks he picked up on his personal road to RSocket enlightenment.

## About Me

1. I work in tech marketing for VMware
1. I work mostly on Spring marketing
1. I like learning new things
1. I like messaging & protocols
1. I've done lots of Integration before

## About You

1. You like learning new things
1. You like messaging & protocols
1. You have no problem with multiple ways of doing things
1. You haven't seen RSocket before or you want a refresher


## About RSocket

* Open Source
* Binary Protocol For Transports like TCP & WebSocket
* Connection-oriented
* Bidirectional (requesters & responders)
* Multiple Interaction Models
    * Request & Response
    * Fire & Forget
    * Request Stream
    * Channels (Stream Request, Stream Response)
* Multiple Implementations
    * Java
    * Kotlin
    * Javascript
    * .Net
    * And more...

## What You Will Learn

Time permitting, in this talk I'll take you through:

1. Setting up your project
1. Request-response messaging with RSocket
1. Fire-and-forget messaging with RSocket
1. Request-stream messaging with RSocket
1. Channels (stream-stream) messaging with RSocket
1. Basic-security with RSocket

## What You Will Need

* Java 11 (I recommend installing with [SDKMAN][sdkman])
* Spring Initializr ([start.spring.io][start])
* RSC RSocket Client ([Project][rsc], [Releases][rsc-releases])
* An IDE (I'm using IntelliJ IDEA CE)
* A terminal (MacOS, Linux, or Windows Subsystem for Linux)

