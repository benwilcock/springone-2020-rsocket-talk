# Getting Started With RSocket

RSocket is pretty awesome. It’s a fully reactive, message-driven, multi-modal, bidirectional communication protocol built with microservices in mind. But sometimes, being awesome just isn’t enough! Sometimes you need practical, step-by-step instructions on how to get started the easy way, with no complicated docs to read or sample code to grok. In this talk, Ben will tell you everything you need to know to get started with RSocket and a few tips and tricks he picked up on his personal road to RSocket enlightenment.

## What You Will Learn

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

## Lesson 1: Request response

Test your 'request-response' message handler by sending a test message containing the word "Hello" with the `rsc` tool:

```bash
rsc --debug --request --data "{\"message\":\"Hello\"}" --route request-response --stacktrace tcp://localhost:7000
```

The response will look something like this:

```bash
2020-08-28 14:17:06.987 DEBUG --- [actor-tcp-nio-1] i.r.FrameLogger : sending ->
Frame => Stream ID: 1 Type: REQUEST_RESPONSE Flags: 0b100000000 Length: 45
Metadata:
         +-------------------------------------------------+
         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
+--------+-------------------------------------------------+----------------+
|00000000| 10 72 65 71 75 65 73 74 2d 72 65 73 70 6f 6e 73 |.request-respons|
|00000010| 65                                              |e               |
+--------+-------------------------------------------------+----------------+
Data:
         +-------------------------------------------------+
         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
+--------+-------------------------------------------------+----------------+
|00000000| 7b 22 6d 65 73 73 61 67 65 22 3a 22 48 65 6c 6c |{"message":"Hell|
|00000010| 6f 22 7d                                        |o"}             |
+--------+-------------------------------------------------+----------------+
2020-08-28 14:17:07.234 DEBUG --- [actor-tcp-nio-1] i.r.FrameLogger : receiving ->
Frame => Stream ID: 1 Type: NEXT_COMPLETE Flags: 0b1100000 Length: 71
Data:
         +-------------------------------------------------+
         |  0  1  2  3  4  5  6  7  8  9  a  b  c  d  e  f |
+--------+-------------------------------------------------+----------------+
|00000000| 7b 22 6d 65 73 73 61 67 65 22 3a 22 54 68 61 6e |{"message":"Than|
|00000010| 6b 73 20 66 6f 72 20 79 6f 75 72 20 6d 65 73 73 |ks for your mess|
|00000020| 61 67 65 3a 20 48 65 6c 6c 6f 22 2c 22 63 72 65 |age: Hello","cre|
|00000030| 61 74 65 64 22 3a 31 35 39 38 36 32 34 32 32 37 |ated":1598624227|
|00000040| 7d                                              |}               |
+--------+-------------------------------------------------+----------------+
{"message":"Thanks for your message: Hello","created":1598624227}
```

Notice the response from the rsocket-server: "Thanks for your message: Hello"


## Keep Learning

This talk is based on my Spring.io blog series of the same name which you can find links to [here][blog-series]

---

[blog-series]: https://benwilcock.wordpress.com/2020/06/25/getting-started-with-rsocket-on-spring-boot/
[rsc]: https://github.com/making/rsc
[rsc-releases]: https://github.com/making/rsc/releases
[start]: https://start.spring.io
[sdkman]: https://sdkman.io
