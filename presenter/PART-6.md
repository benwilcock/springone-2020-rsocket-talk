# Part 6: Basic Security With RSocket

Demonstrate basic security (authentication and authorisation) with RSocket

## Add The Security Dependencies

1. Add dependencies to the `POM.xml`
    1. `spring-boot-starter-security`
    1. `spring-security-rsocket`
    1. `spring-security-messaging`

## Add The Security Configuration Code

1. Add class: `RSocketSecurityConfiguration`

1. Annotate class: `@Configuration`, `@EnableRSocketSecurity`, `@EnableReactiveMethodSecurity`
 
1. Add bean method: `RSocketMessageHandler` with custom Auth Principal resolver

1. Add bean method: `MapReactiveUserDetailsService` (authentication creds & role for USER)

1. Add bean method: `PayloadSocketAcceptorInterceptor` (authorization required for ANY exchange of data)

## Secure The Server Methods

1. Add `@PreAuthorize("hasRole('USER')")` annotation to MessageMapping methods.

1. Add `@AuthenticationPrincipal UserDetails user` to methods signatures.

1. Add log entry `Request-response initiated by '{}' in the role '{}'", user.getUsername(), user.getAuthorities())`

## Test With Java

1. Add Creds and MimeType setup to `@BeforeAll` in the Test class:
    1. `UsernamePasswordMetadata credentials = new UsernamePasswordMetadata("user", "pass")`
    1. `MimeType mimeType = MimeTypeUtils.parseMimeType(WellKnownMimeType.MESSAGE_RSOCKET_AUTHENTICATION.getString())`

1. Add Creds and Auth Encoder to RSocket Requester:
    1. `.setupMetadata(credentials, mimeType)`
    1. `.rsocketStrategies(b -> b.encoder(new SimpleAuthenticationEncoder()))`

1. Run the tests

1. Break the tests:
    1. Send the wrong creds. 
    1. Request rejected - "Invalid Credentials"