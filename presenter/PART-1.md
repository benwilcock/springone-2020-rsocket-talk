# Part 1: Create the RSocket Server Project

Get started with RSocket the easy way with Spring Boot

## Before You Start

* Install Java 11 (I recommend [SDKMAN][sdkman] for this)
* Install RSC (RSocket Client) ([Project][rsc], [Releases][rsc-releases])

## Start The RSocket Server Project

> Note: In RSocket there's no such thing as a server, only 'requesters' and 'responders,' but for the purpose of this demo I'm using the customary client/server terms to keep it simple.

1. Go to [start.spring.io](https://start.spring.io)

2. Add the required RSocket dependencies
   
   1. `rsocket-starter`

3. Add Lombok for our Data class (optional, but I like it)
   
   1. `lombok`

---

[blog-series]: https://benwilcock.wordpress.com/2020/06/25/getting-started-with-rsocket-on-spring-boot/
[rsc]: https://github.com/making/rsc
[rsc-releases]: https://github.com/making/rsc/releases
[start]: https://start.spring.io
[sdkman]: https://sdkman.io
