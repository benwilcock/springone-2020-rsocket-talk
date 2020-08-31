# Getting Started With RSocket

RSocket is pretty awesome. It’s a fully reactive, message-driven, multi-modal, bidirectional communication protocol built with microservices in mind. But sometimes, being awesome just isn’t enough! Sometimes you need practical, step-by-step instructions on how to get started the easy way, with no complicated docs to read or sample code to grok. In this talk, Ben will tell you everything you need to know to get started with RSocket and a few tips and tricks he picked up on his personal road to RSocket enlightenment.

## About Me

1. I work in tech marketing for VMware
1. I work mostly on Spring marketing
1. I like messaging & protocols
1. I've done lots of Integration before
1. I like learning new things

## About You

1. You like learning new things
1. You like messaging & protocols
1. You have no problem with multiple ways of doing things
1. You haven't seen RSocket before or you want a refresher

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
* An IDE
* A terminal (MacOS, Linux, or Windows Subsystem for Linux)

## Downloading The RSocket Client Binary

The RSocket Client project publishes releases in three formats at present.

1. Java JAR package (which you execute with `java -jar <jarname> <options>`).
2. Mac OS Binary, which is ideal if you're still paying the Apple TAX :)
3. Linux binary, ideal for free thinkers and folks using Windows Subsystem for Linux.

Either download manually by visiting the [releases page][rsc-releases] of the [RSC Project][rsc], or download the file using commands similar to these:

```bash
# For Linux/WSL use the next line, for MacOS switch the wget link for the right version.
wget https://github.com/making/rsc/releases/download/0.5.0/rsc-linux-x86_64
# Rename the binary to something easier to work with.
mv rsc-linux-x86_64 rsc
```

Make sure the RSocket Client program is executable and check it works:

```bash
# Make the binary executable.
chmod +x rsc
# Check the client is executing correctly.
./rsc --version
```

You should see output like this:

```bash
{"version": "0.5.0", "build": "2020-07-06T17:32:33Z"}
```

## Keep Learning

This talk is based on my Spring.io blog series of the same name which you can find links to [here][blog-series]

---

[blog-series]: https://benwilcock.wordpress.com/2020/06/25/getting-started-with-rsocket-on-spring-boot/
[rsc]: https://github.com/making/rsc
[rsc-releases]: https://github.com/making/rsc/releases
[start]: https://start.spring.io
[sdkman]: https://sdkman.io
