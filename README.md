Spring Framework / Issue #30110
====

Overview
---
This repository contains the source for building and running the test sources used to demonstrate the issue above.

Much like Spring Framework issue [#29038](https://github.com/spring-projects/spring-framework/issues/29038),
raised against version 6.0.0-M5 back in August 2022, Spring Framework issue 
[#30110](https://github.com/spring-projects/spring-framework/issues/30110) reports incorrect error handling for
Controller methods returning a Flux Publisher. The said issue mentions that this behaviour is specific to methods
returning data structures (`Flux<Data>`) but not `Flux<String>`s.

However, either scenario is no longer working under Spring Boot *3.1.3* (Spring Framework *6.0.11*).

The following [StackOverflow](https://stackoverflow.com/) article describes the issue with a proposed workaround 
which reverts to a pre-Spring6 version of `JackSon2JsonEncoder`:

https://stackoverflow.com/questions/76663504/how-to-return-kotlin-flow-as-non-streaming-json-data-using-content-type-applica

Unfortunately, said workaround does not fix the problem for streaming consumers.


Test Summary
---
The following table lists some of the tests included within the main branch of this repository:

| Test                                                      | Expected HTTP response | Actual HTTP response |
|-----------------------------------------------------------|:----------------------:|:--------------------:|
| should return HTTP 200 when requesting Strings            |           200          |          200         |
| should return HTTP 400 when requesting Strings with error |           400          |          200         |
| should return HTTP 200 when requesting Data               |           200          |          200         |
| should return HTTP 400 when requesting Data with error    |           400          |          200         |

I also experimented with different versions and components in different branches within this repository, namely:
* `main` Spring Boot 3.1.3 / Spring WebFlux 6.0.11
* `feature/spring-boot2` Spring Boot 2.7.15 / Spring WebFlux 5.3.29 
* `feature/kotlin-flow` Return Flow<*> responses in the Spring WebFlux Controller
* `feature/armeria-spring-boot2` Armeria Server in combination with Spring Boot 2.7.15 libraries
* `feature/armeria-spring-boot3` Armeria Server in combination with Spring Boot 3.1.3 libraries


Error Details
---
The error can be summarised as: A Spring WebFlux REST endpoint that returns a Flux result, fails under the following conditions:
* One or more items are emitted successfully before an error occurs in the Flux pipeline
* A successful HTTP response code (e.g. HTTP 200) is still returned to the consumer with no response body
* The failure occurs regardless of whether JSON or plain text is returned to the consumer although, as pointed out in one of
  the linked resources, JSON encoding used to work in Spring Boot 2.+ presumably due to the relevant JSON encoder collecting 
  results in memory prior to marshalling results back to the consumer
* The said error occurs regardless of whether JSON streaming (`Accept: application/x-ndjson`) is configured at the client end
  or not

An edited version of the error output follows:

```bash
org.springframework.web.server.ResponseStatusException: 400 BAD_REQUEST
	at com.example.DataService$stringsWithError$1.invoke$lambda$0(DataService.kt:24) ~[classes/:na]
	Suppressed: reactor.core.publisher.FluxOnAssembly$OnAssemblyException: 
Error has been observed at the following site(s):
	*__checkpoint ⇢ Handler com.example.DataController#dataWithError() [DispatcherHandler]
	*__checkpoint ⇢ HTTP GET "/data-error" [ExceptionHandlingWebHandler]
Original Stack Trace:
		at com.example.DataService$stringsWithError$1.invoke$lambda$0(DataService.kt:24) ~[classes/:na]
		at reactor.core.publisher.MonoErrorSupplied.call(MonoErrorSupplied.java:61) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxFlatMap$FlatMapMain.onNext(FluxFlatMap.java:405) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxArray$ArraySubscription.slowPath(FluxArray.java:127) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxArray$ArraySubscription.request(FluxArray.java:100) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxFlatMap$FlatMapMain.onSubscribe(FluxFlatMap.java:371) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxArray.subscribe(FluxArray.java:53) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxArray.subscribe(FluxArray.java:59) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.InternalFluxOperator.subscribe(InternalFluxOperator.java:62) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.FluxDeferContextual.subscribe(FluxDeferContextual.java:57) ~[reactor-core-3.5.9.jar:3.5.9]
		at reactor.core.publisher.InternalFluxOperator.subscribe(InternalFluxOperator.java:62) ~[reactor-core-3.5.9.jar:3.5.9]
		at org.springframework.http.server.reactive.ChannelSendOperator.subscribe(ChannelSendOperator.java:77) ~[spring-web-6.0.11.jar:6.0.11]
...
reactor.core.Exceptions$ErrorCallbackNotImplemented: java.lang.IllegalStateException: Content has not been consumed, and an error was raised while attempting to produce it.
Caused by: java.lang.IllegalStateException: Content has not been consumed, and an error was raised while attempting to produce it.
	at org.springframework.test.web.reactive.server.WiretapConnector$WiretapRecorder.lambda$getContent$5(WiretapConnector.java:195) ~[spring-test-6.0.11.jar:6.0.11]
	at reactor.core.publisher.Flux.lambda$onErrorMap$27(Flux.java:7201) ~[reactor-core-3.5.9.jar:3.5.9]
	at reactor.core.publisher.FluxOnErrorResume$ResumeSubscriber.onError(FluxOnErrorResume.java:94) ~[reactor-core-3.5.9.jar:3.5.9]
Caused by: reactor.netty.http.client.PrematureCloseException: Connection prematurely closed DURING response
```


Prerequisites
-------------
In order to build the program, the following is required

* A working Internet connection (to download all library dependencies required to build the program).


Build and Run
------------

### To build and run the included test use:

```
    $ ./gradlew clean test
```


Links
-----

- [Error handling behaves differently in versions 5 and 6 for controller method returning Flux with non-String Object](https://github.com/spring-projects/spring-framework/issues/30110)
- [Error handling no longer works for JSON controller method returning Flux under 6.0.0-M5](https://github.com/spring-projects/spring-framework/issues/29038)
- [How to return Kotlin Flow as non-streaming JSON data using Content-Type: application/json in Spring Framework 6?](https://stackoverflow.com/questions/76663504/how-to-return-kotlin-flow-as-non-streaming-json-data-using-content-type-applica)


