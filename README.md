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


