# README.payara.md

**NOTE: Payara is not ready to execute this code.**

**NOTE: Tested LATEST (6.2021.1.Alpha1) and 5.2021.1 till 5.2021.8 and FAILED.**

**NOTE: In case we change the code on @RestClient injection and move the @Consumes / @Produces annotations directly to the GET, PUT, POST, DELETE and UPDATE method we get it working ...**

**PROBLEM: liberty, quarkus and wíldfly microprofile runtimes are working well with the current code base unchanged.**

**ERROR:**

```aidl
2021-10-27T23:05:30.166+0200] [] [SEVERE] [] [javax.enterprise.system.core] [tid: _ThreadID=1 _ThreadName=main] [timeMillis: 1635368730166] [levelValue: 1000] [[
  Exception while loading the app : CDI deployment failure:WELD-001408: Unsatisfied dependencies for type ThesaurusClient with qualifiers @RestClient
  at injection point [BackedAnnotatedField] @Inject @RestClient com.packt.microprofile.book.ch4.cdi.MyCdiResource.thesaurusClient
  at com.packt.microprofile.book.ch4.cdi.MyCdiResource.thesaurusClient(MyCdiResource.java:0)
 -- WELD-001408: Unsatisfied dependencies for type ThesaurusClient with qualifiers @RestClient
  at injection point [BackedAnnotatedField] @Inject @RestClient com.packt.microprofile.book.ch4.cdi.MyCdiResource.thesaurusClient
  at com.packt.microprofile.book.ch4.cdi.MyCdiResource.thesaurusClient(MyCdiResource.java:0)
]]


```

Disregard below details as all of them do not work:

## Build and execute
```
mvn -f pom.xml.payara clean package
java -jar target/ch4-microbundle.jar --port 9080 --contextroot ch4
```

## Endpoints

### hello world

http://localhost:9080/ch4/rest/hello
```
Hello World!
```

### beanparam

http://localhost:9080/ch4/rest/beanparam/myPath?id=1234
```
result: ID: 1234 X-SomeHeader: null path: myPath
```

curl with header
```
curl "http://localhost:9080/ch4/rest/beanparam/myPath?id=1234" -H "X-SomeHeader: MyHeaderValue"

result: ID: 1234 X-SomeHeader: MyHeaderValue path: myPath
```

### Person

#### get person/0 with initial Color.RED

http://localhost:9080/ch4/rest/person/0
```
result: {
 "firstName": "John",
 "lastName": "Doe",
 "age": 33,
 "favoriteColor":"RED"
}
```

#### PATCH: person/0?color=BLUE

```
curl -v -X PATCH http://localhost:9080/ch4/rest/person/0?color=BLUE

result: {
 "firstName": "John",
 "lastName": "Doe",
 "age": 33,
 "favoriteColor":"BLUE"
}
```

#### get person/0 now BLUE

http://localhost:9080/ch4/rest/person/0
```
result: {
 "firstName": "John",
 "lastName": "Doe",
 "age": 33,
 "favoriteColor":"BLUE"
}
```

#### PATCH: person/0?color=red

```
curl -v -X PATCH http://localhost:9080/ch4/rest/person/0?color=red

result: {
 "firstName": "John",
 "lastName": "Doe",
 "age": 33,
 "favoriteColor":"RED"
}
```

#### PATCH: person/0?color=blue

```
curl -v -X PATCH http://localhost:9080/ch4/rest/person/0?color=blue

result: {
 "firstName": "John",
 "lastName": "Doe",
 "age": 33,
 "favoriteColor":"BLUE"
}
```

### SYNC/ASYNC person

#### SYNC

http://localhost:9080/ch4/rest/person/sync/1
```
result: {
 "firstName": "Richard",
 "lastName": "Smith",
 "age": 21,
 "favoriteColor":"ORANGE"
}
```

#### ASYNC

http://localhost:9080/ch4/rest/person/async/1
```
result: {
 "firstName": "Richard",
 "lastName": "Smith",
 "age": 21,
 "favoriteColor":"ORANGE"
}
```

### SERVER SIDE EVENTS

http://localhost:9080/ch4/rest/sse
```
result:

event: fooEvent
id: 1
data: foo

event: barEvent
id: 2
data: bar

event: bazEvent
id: 3
data: baz
```

#### curl - server side events

```
curl http://localhost:9080/ch4/rest/sse

result:
event: fooEvent
id: 1
data: foo

event: barEvent
id: 2
data: bar

event: bazEvent
id: 3
data: baz
```

#### curl - server side events broadcast

http://localhost:9080/ch4/rest/sse/broadcast
```
    UnnamedEvent
data: new registrant

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping
```

curl access
```
curl http://localhost:9080/ch4/rest/sse/broadcast

result:
    UnnamedEvent
data: new registrant

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

    UnnamedEvent
data: ping

^C
```

http://localhost:9080/ch4/rest/test/sse
```
result: foo bar baz 
```

### JSON-P

com.packt.microprofile.book.ch4.jsonp.JsonpConverter.main

In IDE click on main method and execute:
```
result:

/home/fschute/.sdkman/candidates/java/11.0.11-zulu/bin/java -javaagent:/opt/idea-IC-211.7628.21/lib/idea_rt.jar=46159:/opt/idea-IC-211.7628.21/bin -Dfile.encoding=UTF-8 -classpath /home/fschute/_work/_prj/book/Practical-Cloud-Native-Java-Development-with-MicroProfile/Chapter04/target/classes:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jaxb/2.3.1.Final/quarkus-resteasy-jaxb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jaxb/2.3.1.Final/quarkus-jaxb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jaxp/2.3.1.Final/quarkus-jaxp-2.3.1.Final.jar:/home/fschute/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.3-b02/jaxb-runtime-2.3.3-b02.jar:/home/fschute/.m2/repository/org/glassfish/jaxb/txw2/2.3.3-b02/txw2-2.3.3-b02.jar:/home/fschute/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.10/istack-commons-runtime-3.0.10.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-jaxb-provider/4.7.0.Final/resteasy-jaxb-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/home/fschute/.m2/repository/org/jboss/spec/javax/xml/bind/jboss-jaxb-api_2.3_spec/2.0.0.Final/jboss-jaxb-api_2.3_spec-2.0.0.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jsonb/2.3.1.Final/quarkus-resteasy-jsonb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jsonb/2.3.1.Final/quarkus-jsonb-2.3.1.Final.jar:/home/fschute/.m2/repository/org/eclipse/yasson/1.0.9/yasson-1.0.9.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jsonp/2.3.1.Final/quarkus-jsonp-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-json-binding-provider/4.7.0.Final/resteasy-json-binding-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/glassfish/jakarta.json/1.1.6/jakarta.json-1.1.6.jar:/home/fschute/.m2/repository/jakarta/json/bind/jakarta.json.bind-api/1.0.2/jakarta.json.bind-api-1.0.2.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-json-p-provider/4.7.0.Final/resteasy-json-p-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-multipart/2.3.1.Final/quarkus-resteasy-multipart-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-multipart-provider/4.7.0.Final/resteasy-multipart-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-core-spi/4.7.0.Final/resteasy-core-spi-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/spec/javax/ws/rs/jboss-jaxrs-api_2.1_spec/2.0.1.Final/jboss-jaxrs-api_2.1_spec-2.0.1.Final.jar:/home/fschute/.m2/repository/org/reactivestreams/reactive-streams/1.0.3/reactive-streams-1.0.3.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-core/4.7.0.Final/resteasy-core-4.7.0.Final.jar:/home/fschute/.m2/repository/com/ibm/async/asyncutil/0.1.0/asyncutil-0.1.0.jar:/home/fschute/.m2/repository/com/sun/mail/jakarta.mail/1.6.5/jakarta.mail-1.6.5.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-dom/0.8.3/apache-mime4j-dom-0.8.3.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-core/0.8.3/apache-mime4j-core-0.8.3.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-storage/0.8.3/apache-mime4j-storage-0.8.3.jar:/home/fschute/.m2/repository/org/jboss/logging/commons-logging-jboss-logging/1.0.0.Final/commons-logging-jboss-logging-1.0.0.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy/2.3.1.Final/quarkus-resteasy-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx-http/2.3.1.Final/quarkus-vertx-http-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-security-runtime-spi/2.3.1.Final/quarkus-security-runtime-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-mutiny/2.3.1.Final/quarkus-mutiny-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/reactive/mutiny/1.1.1/mutiny-1.1.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-smallrye-context-propagation/2.3.1.Final/quarkus-smallrye-context-propagation-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation/1.2.0/smallrye-context-propagation-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation-api/1.2.0/smallrye-context-propagation-api-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation-storage/1.2.0/smallrye-context-propagation-storage-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/mutiny-smallrye-context-propagation/1.1.1/mutiny-smallrye-context-propagation-1.1.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx-http-dev-console-runtime-spi/2.3.1.Final/quarkus-vertx-http-dev-console-runtime-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/security/quarkus-security/1.1.4.Final/quarkus-security-1.1.4.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx/2.3.1.Final/quarkus-vertx-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-netty/2.3.1.Final/quarkus-netty-2.3.1.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec/4.1.68.Final/netty-codec-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-http/4.1.68.Final/netty-codec-http-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-http2/4.1.68.Final/netty-codec-http2-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-handler/4.1.68.Final/netty-handler-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-haproxy/4.1.68.Final/netty-codec-haproxy-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-buffer/4.1.68.Final/netty-buffer-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-transport/4.1.68.Final/netty-transport-4.1.68.Final.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-annotation/1.6.0/smallrye-common-annotation-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-core/2.13.0/smallrye-mutiny-vertx-core-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-runtime/2.13.0/smallrye-mutiny-vertx-runtime-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/vertx-mutiny-generator/2.13.0/vertx-mutiny-generator-2.13.0.jar:/home/fschute/.m2/repository/io/vertx/vertx-codegen/4.1.4/vertx-codegen-4.1.4.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-fault-tolerance-vertx/5.2.1/smallrye-fault-tolerance-vertx-5.2.1.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-web/2.13.0/smallrye-mutiny-vertx-web-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-web-common/2.13.0/smallrye-mutiny-vertx-web-common-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-auth-common/2.13.0/smallrye-mutiny-vertx-auth-common-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-bridge-common/2.13.0/smallrye-mutiny-vertx-bridge-common-2.13.0.jar:/home/fschute/.m2/repository/io/vertx/vertx-web/4.1.4/vertx-web-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-web-common/4.1.4/vertx-web-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-auth-common/4.1.4/vertx-auth-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-bridge-common/4.1.4/vertx-bridge-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-core/4.1.4/vertx-core-4.1.4.jar:/home/fschute/.m2/repository/io/netty/netty-common/4.1.68.Final/netty-common-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-handler-proxy/4.1.68.Final/netty-handler-proxy-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-socks/4.1.68.Final/netty-codec-socks-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-resolver/4.1.68.Final/netty-resolver-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-resolver-dns/4.1.68.Final/netty-resolver-dns-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-dns/4.1.68.Final/netty-codec-dns-4.1.68.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-server-common/2.3.1.Final/quarkus-resteasy-server-common-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/validation/jakarta.validation-api/2.0.2/jakarta.validation-api-2.0.2.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-rest-client/2.3.1.Final/quarkus-rest-client-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-core/2.3.1.Final/quarkus-core-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/home/fschute/.m2/repository/jakarta/enterprise/jakarta.enterprise.cdi-api/2.0.2/jakarta.enterprise.cdi-api-2.0.2.jar:/home/fschute/.m2/repository/jakarta/el/jakarta.el-api/3.0.3/jakarta.el-api-3.0.3.jar:/home/fschute/.m2/repository/jakarta/inject/jakarta.inject-api/1.0/jakarta.inject-api-1.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-ide-launcher/2.3.1.Final/quarkus-ide-launcher-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-development-mode-spi/2.3.1.Final/quarkus-development-mode-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config/2.5.1/smallrye-config-2.5.1.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config-core/2.5.1/smallrye-config-core-2.5.1.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/config/microprofile-config-api/2.0/microprofile-config-api-2.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-expression/1.6.0/smallrye-common-expression-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-function/1.6.0/smallrye-common-function-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-constraint/1.6.0/smallrye-common-constraint-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-classloader/1.6.0/smallrye-common-classloader-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config-common/2.5.1/smallrye-config-common-2.5.1.jar:/home/fschute/.m2/repository/org/jboss/logmanager/jboss-logmanager-embedded/1.0.9/jboss-logmanager-embedded-1.0.9.jar:/home/fschute/.m2/repository/org/jboss/logging/jboss-logging-annotations/2.2.1.Final/jboss-logging-annotations-2.2.1.Final.jar:/home/fschute/.m2/repository/org/jboss/threads/jboss-threads/3.4.2.Final/jboss-threads-3.4.2.Final.jar:/home/fschute/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/home/fschute/.m2/repository/org/jboss/slf4j/slf4j-jboss-logmanager/1.1.0.Final/slf4j-jboss-logmanager-1.1.0.Final.jar:/home/fschute/.m2/repository/org/graalvm/sdk/graal-sdk/21.2.0/graal-sdk-21.2.0.jar:/home/fschute/.m2/repository/org/wildfly/common/wildfly-common/1.5.4.Final-format-001/wildfly-common-1.5.4.Final-format-001.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-bootstrap-runner/2.3.1.Final/quarkus-bootstrap-runner-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-common/2.3.1.Final/quarkus-resteasy-common-2.3.1.Final.jar:/home/fschute/.m2/repository/com/sun/activation/jakarta.activation/1.2.1/jakarta.activation-1.2.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-apache-httpclient/2.3.1.Final/quarkus-apache-httpclient-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-microprofile/4.7.0.Final/resteasy-client-microprofile-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-microprofile-base/4.7.0.Final/resteasy-client-microprofile-base-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client/4.7.0.Final/resteasy-client-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-api/4.7.0.Final/resteasy-client-api-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-cdi/4.7.0.Final/resteasy-cdi-4.7.0.Final.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/rest/client/microprofile-rest-client-api/2.0/microprofile-rest-client-api-2.0.jar:/home/fschute/.m2/repository/jakarta/interceptor/jakarta.interceptor-api/1.2.5/jakarta.interceptor-api-1.2.5.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpasyncclient/4.1.4/httpasyncclient-4.1.4.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpcore/4.4.14/httpcore-4.4.14.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpcore-nio/4.4.14/httpcore-nio-4.4.14.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-rest-client-jackson/2.3.1.Final/quarkus-rest-client-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jackson/2.3.1.Final/quarkus-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.5/jackson-databind-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.5/jackson-datatype-jsr310-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.5/jackson-datatype-jdk8-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.5/jackson-module-parameter-names-2.12.5.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-jackson2-provider/4.7.0.Final/resteasy-jackson2-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.5/jackson-core-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.5/jackson-annotations-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/jaxrs/jackson-jaxrs-json-provider/2.12.5/jackson-jaxrs-json-provider-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/jaxrs/jackson-jaxrs-base/2.12.5/jackson-jaxrs-base-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.12.5/jackson-module-jaxb-annotations-2.12.5.jar:/home/fschute/.m2/repository/com/github/java-json-tools/json-patch/1.13/json-patch-1.13.jar:/home/fschute/.m2/repository/com/github/java-json-tools/msg-simple/1.2/msg-simple-1.2.jar:/home/fschute/.m2/repository/com/github/java-json-tools/btf/1.3/btf-1.3.jar:/home/fschute/.m2/repository/com/github/java-json-tools/jackson-coreutils/2.0/jackson-coreutils-2.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jackson/2.3.1.Final/quarkus-resteasy-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-arc/2.3.1.Final/quarkus-arc-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/arc/arc/2.3.1.Final/arc-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/context-propagation/microprofile-context-propagation-api/1.2/microprofile-context-propagation-api-1.2.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-io/1.6.0/smallrye-common-io-1.6.0.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/home/fschute/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar com.packt.microprofile.book.ch4.jsonp.JsonpConverter
{"name":"Coreillian Freighter","hasHyperdrive":true,"speedRating":22,"weapons":[{"name":"Quad Blaster Turret","type":"Laser","damageRating":24}]}
name: Coreillian Freighter
hasHyperdrive: true
speedRating: 22
weapons:
  name: Quad Blaster Turret
  type: Laser
  damageRating: 24

Process finished with exit code 0

```

#### com.packt.microprofile.book.ch4.jsonp.JsonpStreamingConverter.main

In IDE click on main method and execute:
```
result:

/home/fschute/.sdkman/candidates/java/11.0.11-zulu/bin/java -javaagent:/opt/idea-IC-211.7628.21/lib/idea_rt.jar=39201:/opt/idea-IC-211.7628.21/bin -Dfile.encoding=UTF-8 -classpath /home/fschute/_work/_prj/book/Practical-Cloud-Native-Java-Development-with-MicroProfile/Chapter04/target/classes:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jaxb/2.3.1.Final/quarkus-resteasy-jaxb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jaxb/2.3.1.Final/quarkus-jaxb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jaxp/2.3.1.Final/quarkus-jaxp-2.3.1.Final.jar:/home/fschute/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.3-b02/jaxb-runtime-2.3.3-b02.jar:/home/fschute/.m2/repository/org/glassfish/jaxb/txw2/2.3.3-b02/txw2-2.3.3-b02.jar:/home/fschute/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.10/istack-commons-runtime-3.0.10.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-jaxb-provider/4.7.0.Final/resteasy-jaxb-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/home/fschute/.m2/repository/org/jboss/spec/javax/xml/bind/jboss-jaxb-api_2.3_spec/2.0.0.Final/jboss-jaxb-api_2.3_spec-2.0.0.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jsonb/2.3.1.Final/quarkus-resteasy-jsonb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jsonb/2.3.1.Final/quarkus-jsonb-2.3.1.Final.jar:/home/fschute/.m2/repository/org/eclipse/yasson/1.0.9/yasson-1.0.9.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jsonp/2.3.1.Final/quarkus-jsonp-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-json-binding-provider/4.7.0.Final/resteasy-json-binding-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/glassfish/jakarta.json/1.1.6/jakarta.json-1.1.6.jar:/home/fschute/.m2/repository/jakarta/json/bind/jakarta.json.bind-api/1.0.2/jakarta.json.bind-api-1.0.2.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-json-p-provider/4.7.0.Final/resteasy-json-p-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-multipart/2.3.1.Final/quarkus-resteasy-multipart-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-multipart-provider/4.7.0.Final/resteasy-multipart-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-core-spi/4.7.0.Final/resteasy-core-spi-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/spec/javax/ws/rs/jboss-jaxrs-api_2.1_spec/2.0.1.Final/jboss-jaxrs-api_2.1_spec-2.0.1.Final.jar:/home/fschute/.m2/repository/org/reactivestreams/reactive-streams/1.0.3/reactive-streams-1.0.3.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-core/4.7.0.Final/resteasy-core-4.7.0.Final.jar:/home/fschute/.m2/repository/com/ibm/async/asyncutil/0.1.0/asyncutil-0.1.0.jar:/home/fschute/.m2/repository/com/sun/mail/jakarta.mail/1.6.5/jakarta.mail-1.6.5.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-dom/0.8.3/apache-mime4j-dom-0.8.3.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-core/0.8.3/apache-mime4j-core-0.8.3.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-storage/0.8.3/apache-mime4j-storage-0.8.3.jar:/home/fschute/.m2/repository/org/jboss/logging/commons-logging-jboss-logging/1.0.0.Final/commons-logging-jboss-logging-1.0.0.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy/2.3.1.Final/quarkus-resteasy-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx-http/2.3.1.Final/quarkus-vertx-http-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-security-runtime-spi/2.3.1.Final/quarkus-security-runtime-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-mutiny/2.3.1.Final/quarkus-mutiny-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/reactive/mutiny/1.1.1/mutiny-1.1.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-smallrye-context-propagation/2.3.1.Final/quarkus-smallrye-context-propagation-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation/1.2.0/smallrye-context-propagation-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation-api/1.2.0/smallrye-context-propagation-api-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation-storage/1.2.0/smallrye-context-propagation-storage-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/mutiny-smallrye-context-propagation/1.1.1/mutiny-smallrye-context-propagation-1.1.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx-http-dev-console-runtime-spi/2.3.1.Final/quarkus-vertx-http-dev-console-runtime-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/security/quarkus-security/1.1.4.Final/quarkus-security-1.1.4.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx/2.3.1.Final/quarkus-vertx-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-netty/2.3.1.Final/quarkus-netty-2.3.1.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec/4.1.68.Final/netty-codec-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-http/4.1.68.Final/netty-codec-http-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-http2/4.1.68.Final/netty-codec-http2-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-handler/4.1.68.Final/netty-handler-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-haproxy/4.1.68.Final/netty-codec-haproxy-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-buffer/4.1.68.Final/netty-buffer-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-transport/4.1.68.Final/netty-transport-4.1.68.Final.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-annotation/1.6.0/smallrye-common-annotation-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-core/2.13.0/smallrye-mutiny-vertx-core-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-runtime/2.13.0/smallrye-mutiny-vertx-runtime-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/vertx-mutiny-generator/2.13.0/vertx-mutiny-generator-2.13.0.jar:/home/fschute/.m2/repository/io/vertx/vertx-codegen/4.1.4/vertx-codegen-4.1.4.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-fault-tolerance-vertx/5.2.1/smallrye-fault-tolerance-vertx-5.2.1.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-web/2.13.0/smallrye-mutiny-vertx-web-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-web-common/2.13.0/smallrye-mutiny-vertx-web-common-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-auth-common/2.13.0/smallrye-mutiny-vertx-auth-common-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-bridge-common/2.13.0/smallrye-mutiny-vertx-bridge-common-2.13.0.jar:/home/fschute/.m2/repository/io/vertx/vertx-web/4.1.4/vertx-web-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-web-common/4.1.4/vertx-web-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-auth-common/4.1.4/vertx-auth-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-bridge-common/4.1.4/vertx-bridge-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-core/4.1.4/vertx-core-4.1.4.jar:/home/fschute/.m2/repository/io/netty/netty-common/4.1.68.Final/netty-common-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-handler-proxy/4.1.68.Final/netty-handler-proxy-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-socks/4.1.68.Final/netty-codec-socks-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-resolver/4.1.68.Final/netty-resolver-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-resolver-dns/4.1.68.Final/netty-resolver-dns-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-dns/4.1.68.Final/netty-codec-dns-4.1.68.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-server-common/2.3.1.Final/quarkus-resteasy-server-common-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/validation/jakarta.validation-api/2.0.2/jakarta.validation-api-2.0.2.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-rest-client/2.3.1.Final/quarkus-rest-client-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-core/2.3.1.Final/quarkus-core-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/home/fschute/.m2/repository/jakarta/enterprise/jakarta.enterprise.cdi-api/2.0.2/jakarta.enterprise.cdi-api-2.0.2.jar:/home/fschute/.m2/repository/jakarta/el/jakarta.el-api/3.0.3/jakarta.el-api-3.0.3.jar:/home/fschute/.m2/repository/jakarta/inject/jakarta.inject-api/1.0/jakarta.inject-api-1.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-ide-launcher/2.3.1.Final/quarkus-ide-launcher-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-development-mode-spi/2.3.1.Final/quarkus-development-mode-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config/2.5.1/smallrye-config-2.5.1.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config-core/2.5.1/smallrye-config-core-2.5.1.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/config/microprofile-config-api/2.0/microprofile-config-api-2.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-expression/1.6.0/smallrye-common-expression-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-function/1.6.0/smallrye-common-function-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-constraint/1.6.0/smallrye-common-constraint-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-classloader/1.6.0/smallrye-common-classloader-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config-common/2.5.1/smallrye-config-common-2.5.1.jar:/home/fschute/.m2/repository/org/jboss/logmanager/jboss-logmanager-embedded/1.0.9/jboss-logmanager-embedded-1.0.9.jar:/home/fschute/.m2/repository/org/jboss/logging/jboss-logging-annotations/2.2.1.Final/jboss-logging-annotations-2.2.1.Final.jar:/home/fschute/.m2/repository/org/jboss/threads/jboss-threads/3.4.2.Final/jboss-threads-3.4.2.Final.jar:/home/fschute/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/home/fschute/.m2/repository/org/jboss/slf4j/slf4j-jboss-logmanager/1.1.0.Final/slf4j-jboss-logmanager-1.1.0.Final.jar:/home/fschute/.m2/repository/org/graalvm/sdk/graal-sdk/21.2.0/graal-sdk-21.2.0.jar:/home/fschute/.m2/repository/org/wildfly/common/wildfly-common/1.5.4.Final-format-001/wildfly-common-1.5.4.Final-format-001.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-bootstrap-runner/2.3.1.Final/quarkus-bootstrap-runner-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-common/2.3.1.Final/quarkus-resteasy-common-2.3.1.Final.jar:/home/fschute/.m2/repository/com/sun/activation/jakarta.activation/1.2.1/jakarta.activation-1.2.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-apache-httpclient/2.3.1.Final/quarkus-apache-httpclient-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-microprofile/4.7.0.Final/resteasy-client-microprofile-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-microprofile-base/4.7.0.Final/resteasy-client-microprofile-base-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client/4.7.0.Final/resteasy-client-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-api/4.7.0.Final/resteasy-client-api-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-cdi/4.7.0.Final/resteasy-cdi-4.7.0.Final.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/rest/client/microprofile-rest-client-api/2.0/microprofile-rest-client-api-2.0.jar:/home/fschute/.m2/repository/jakarta/interceptor/jakarta.interceptor-api/1.2.5/jakarta.interceptor-api-1.2.5.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpasyncclient/4.1.4/httpasyncclient-4.1.4.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpcore/4.4.14/httpcore-4.4.14.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpcore-nio/4.4.14/httpcore-nio-4.4.14.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-rest-client-jackson/2.3.1.Final/quarkus-rest-client-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jackson/2.3.1.Final/quarkus-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.5/jackson-databind-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.5/jackson-datatype-jsr310-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.5/jackson-datatype-jdk8-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.5/jackson-module-parameter-names-2.12.5.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-jackson2-provider/4.7.0.Final/resteasy-jackson2-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.5/jackson-core-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.5/jackson-annotations-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/jaxrs/jackson-jaxrs-json-provider/2.12.5/jackson-jaxrs-json-provider-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/jaxrs/jackson-jaxrs-base/2.12.5/jackson-jaxrs-base-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.12.5/jackson-module-jaxb-annotations-2.12.5.jar:/home/fschute/.m2/repository/com/github/java-json-tools/json-patch/1.13/json-patch-1.13.jar:/home/fschute/.m2/repository/com/github/java-json-tools/msg-simple/1.2/msg-simple-1.2.jar:/home/fschute/.m2/repository/com/github/java-json-tools/btf/1.3/btf-1.3.jar:/home/fschute/.m2/repository/com/github/java-json-tools/jackson-coreutils/2.0/jackson-coreutils-2.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jackson/2.3.1.Final/quarkus-resteasy-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-arc/2.3.1.Final/quarkus-arc-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/arc/arc/2.3.1.Final/arc-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/context-propagation/microprofile-context-propagation-api/1.2/microprofile-context-propagation-api-1.2.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-io/1.6.0/smallrye-common-io-1.6.0.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/home/fschute/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar com.packt.microprofile.book.ch4.jsonp.JsonpStreamingConverter
{"name":"Coreillian Freighter","hasHyperdrive":true,"speedRating":22,"weapons":[{"name":"Quad Blaster Turret","type":"Laser","damageRating":24},{"name":"Proton Torpedoes","type":"Missile","damageRating":76}]}
name: Coreillian Freighter
hasHyperdrive: true
speedRating: 22
weapons:
  name: Quad Blaster Turret
  type: Laser
  damageRating: 24

  name: Proton Torpedoes
  type: Missile
  damageRating: 76


Process finished with exit code 0
```

### JSON-B

com.packt.microprofile.book.ch4.jsonb.JsonbConverter.main

In IDE click on main method and execute:
```
result:

/home/fschute/.sdkman/candidates/java/11.0.11-zulu/bin/java -javaagent:/opt/idea-IC-211.7628.21/lib/idea_rt.jar=35817:/opt/idea-IC-211.7628.21/bin -Dfile.encoding=UTF-8 -classpath /home/fschute/_work/_prj/book/Practical-Cloud-Native-Java-Development-with-MicroProfile/Chapter04/target/classes:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jaxb/2.3.1.Final/quarkus-resteasy-jaxb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jaxb/2.3.1.Final/quarkus-jaxb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jaxp/2.3.1.Final/quarkus-jaxp-2.3.1.Final.jar:/home/fschute/.m2/repository/org/glassfish/jaxb/jaxb-runtime/2.3.3-b02/jaxb-runtime-2.3.3-b02.jar:/home/fschute/.m2/repository/org/glassfish/jaxb/txw2/2.3.3-b02/txw2-2.3.3-b02.jar:/home/fschute/.m2/repository/com/sun/istack/istack-commons-runtime/3.0.10/istack-commons-runtime-3.0.10.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-jaxb-provider/4.7.0.Final/resteasy-jaxb-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/logging/jboss-logging/3.4.2.Final/jboss-logging-3.4.2.Final.jar:/home/fschute/.m2/repository/org/jboss/spec/javax/xml/bind/jboss-jaxb-api_2.3_spec/2.0.0.Final/jboss-jaxb-api_2.3_spec-2.0.0.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jsonb/2.3.1.Final/quarkus-resteasy-jsonb-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jsonb/2.3.1.Final/quarkus-jsonb-2.3.1.Final.jar:/home/fschute/.m2/repository/org/eclipse/yasson/1.0.9/yasson-1.0.9.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jsonp/2.3.1.Final/quarkus-jsonp-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-json-binding-provider/4.7.0.Final/resteasy-json-binding-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/glassfish/jakarta.json/1.1.6/jakarta.json-1.1.6.jar:/home/fschute/.m2/repository/jakarta/json/bind/jakarta.json.bind-api/1.0.2/jakarta.json.bind-api-1.0.2.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-json-p-provider/4.7.0.Final/resteasy-json-p-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/commons-io/commons-io/2.11.0/commons-io-2.11.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-multipart/2.3.1.Final/quarkus-resteasy-multipart-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-multipart-provider/4.7.0.Final/resteasy-multipart-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-core-spi/4.7.0.Final/resteasy-core-spi-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/spec/javax/ws/rs/jboss-jaxrs-api_2.1_spec/2.0.1.Final/jboss-jaxrs-api_2.1_spec-2.0.1.Final.jar:/home/fschute/.m2/repository/org/reactivestreams/reactive-streams/1.0.3/reactive-streams-1.0.3.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-core/4.7.0.Final/resteasy-core-4.7.0.Final.jar:/home/fschute/.m2/repository/com/ibm/async/asyncutil/0.1.0/asyncutil-0.1.0.jar:/home/fschute/.m2/repository/com/sun/mail/jakarta.mail/1.6.5/jakarta.mail-1.6.5.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-dom/0.8.3/apache-mime4j-dom-0.8.3.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-core/0.8.3/apache-mime4j-core-0.8.3.jar:/home/fschute/.m2/repository/org/apache/james/apache-mime4j-storage/0.8.3/apache-mime4j-storage-0.8.3.jar:/home/fschute/.m2/repository/org/jboss/logging/commons-logging-jboss-logging/1.0.0.Final/commons-logging-jboss-logging-1.0.0.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy/2.3.1.Final/quarkus-resteasy-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx-http/2.3.1.Final/quarkus-vertx-http-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-security-runtime-spi/2.3.1.Final/quarkus-security-runtime-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-mutiny/2.3.1.Final/quarkus-mutiny-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/reactive/mutiny/1.1.1/mutiny-1.1.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-smallrye-context-propagation/2.3.1.Final/quarkus-smallrye-context-propagation-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation/1.2.0/smallrye-context-propagation-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation-api/1.2.0/smallrye-context-propagation-api-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-context-propagation-storage/1.2.0/smallrye-context-propagation-storage-1.2.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/mutiny-smallrye-context-propagation/1.1.1/mutiny-smallrye-context-propagation-1.1.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx-http-dev-console-runtime-spi/2.3.1.Final/quarkus-vertx-http-dev-console-runtime-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/security/quarkus-security/1.1.4.Final/quarkus-security-1.1.4.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-vertx/2.3.1.Final/quarkus-vertx-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-netty/2.3.1.Final/quarkus-netty-2.3.1.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec/4.1.68.Final/netty-codec-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-http/4.1.68.Final/netty-codec-http-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-http2/4.1.68.Final/netty-codec-http2-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-handler/4.1.68.Final/netty-handler-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-haproxy/4.1.68.Final/netty-codec-haproxy-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-buffer/4.1.68.Final/netty-buffer-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-transport/4.1.68.Final/netty-transport-4.1.68.Final.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-annotation/1.6.0/smallrye-common-annotation-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-core/2.13.0/smallrye-mutiny-vertx-core-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-runtime/2.13.0/smallrye-mutiny-vertx-runtime-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/vertx-mutiny-generator/2.13.0/vertx-mutiny-generator-2.13.0.jar:/home/fschute/.m2/repository/io/vertx/vertx-codegen/4.1.4/vertx-codegen-4.1.4.jar:/home/fschute/.m2/repository/io/smallrye/smallrye-fault-tolerance-vertx/5.2.1/smallrye-fault-tolerance-vertx-5.2.1.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-web/2.13.0/smallrye-mutiny-vertx-web-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-web-common/2.13.0/smallrye-mutiny-vertx-web-common-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-auth-common/2.13.0/smallrye-mutiny-vertx-auth-common-2.13.0.jar:/home/fschute/.m2/repository/io/smallrye/reactive/smallrye-mutiny-vertx-bridge-common/2.13.0/smallrye-mutiny-vertx-bridge-common-2.13.0.jar:/home/fschute/.m2/repository/io/vertx/vertx-web/4.1.4/vertx-web-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-web-common/4.1.4/vertx-web-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-auth-common/4.1.4/vertx-auth-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-bridge-common/4.1.4/vertx-bridge-common-4.1.4.jar:/home/fschute/.m2/repository/io/vertx/vertx-core/4.1.4/vertx-core-4.1.4.jar:/home/fschute/.m2/repository/io/netty/netty-common/4.1.68.Final/netty-common-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-handler-proxy/4.1.68.Final/netty-handler-proxy-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-socks/4.1.68.Final/netty-codec-socks-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-resolver/4.1.68.Final/netty-resolver-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-resolver-dns/4.1.68.Final/netty-resolver-dns-4.1.68.Final.jar:/home/fschute/.m2/repository/io/netty/netty-codec-dns/4.1.68.Final/netty-codec-dns-4.1.68.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-server-common/2.3.1.Final/quarkus-resteasy-server-common-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/validation/jakarta.validation-api/2.0.2/jakarta.validation-api-2.0.2.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-rest-client/2.3.1.Final/quarkus-rest-client-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-core/2.3.1.Final/quarkus-core-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/annotation/jakarta.annotation-api/1.3.5/jakarta.annotation-api-1.3.5.jar:/home/fschute/.m2/repository/jakarta/enterprise/jakarta.enterprise.cdi-api/2.0.2/jakarta.enterprise.cdi-api-2.0.2.jar:/home/fschute/.m2/repository/jakarta/el/jakarta.el-api/3.0.3/jakarta.el-api-3.0.3.jar:/home/fschute/.m2/repository/jakarta/inject/jakarta.inject-api/1.0/jakarta.inject-api-1.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-ide-launcher/2.3.1.Final/quarkus-ide-launcher-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-development-mode-spi/2.3.1.Final/quarkus-development-mode-spi-2.3.1.Final.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config/2.5.1/smallrye-config-2.5.1.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config-core/2.5.1/smallrye-config-core-2.5.1.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/config/microprofile-config-api/2.0/microprofile-config-api-2.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-expression/1.6.0/smallrye-common-expression-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-function/1.6.0/smallrye-common-function-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-constraint/1.6.0/smallrye-common-constraint-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-classloader/1.6.0/smallrye-common-classloader-1.6.0.jar:/home/fschute/.m2/repository/io/smallrye/config/smallrye-config-common/2.5.1/smallrye-config-common-2.5.1.jar:/home/fschute/.m2/repository/org/jboss/logmanager/jboss-logmanager-embedded/1.0.9/jboss-logmanager-embedded-1.0.9.jar:/home/fschute/.m2/repository/org/jboss/logging/jboss-logging-annotations/2.2.1.Final/jboss-logging-annotations-2.2.1.Final.jar:/home/fschute/.m2/repository/org/jboss/threads/jboss-threads/3.4.2.Final/jboss-threads-3.4.2.Final.jar:/home/fschute/.m2/repository/org/slf4j/slf4j-api/1.7.30/slf4j-api-1.7.30.jar:/home/fschute/.m2/repository/org/jboss/slf4j/slf4j-jboss-logmanager/1.1.0.Final/slf4j-jboss-logmanager-1.1.0.Final.jar:/home/fschute/.m2/repository/org/graalvm/sdk/graal-sdk/21.2.0/graal-sdk-21.2.0.jar:/home/fschute/.m2/repository/org/wildfly/common/wildfly-common/1.5.4.Final-format-001/wildfly-common-1.5.4.Final-format-001.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-bootstrap-runner/2.3.1.Final/quarkus-bootstrap-runner-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-common/2.3.1.Final/quarkus-resteasy-common-2.3.1.Final.jar:/home/fschute/.m2/repository/com/sun/activation/jakarta.activation/1.2.1/jakarta.activation-1.2.1.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-apache-httpclient/2.3.1.Final/quarkus-apache-httpclient-2.3.1.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-microprofile/4.7.0.Final/resteasy-client-microprofile-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-microprofile-base/4.7.0.Final/resteasy-client-microprofile-base-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client/4.7.0.Final/resteasy-client-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-client-api/4.7.0.Final/resteasy-client-api-4.7.0.Final.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-cdi/4.7.0.Final/resteasy-cdi-4.7.0.Final.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/rest/client/microprofile-rest-client-api/2.0/microprofile-rest-client-api-2.0.jar:/home/fschute/.m2/repository/jakarta/interceptor/jakarta.interceptor-api/1.2.5/jakarta.interceptor-api-1.2.5.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpasyncclient/4.1.4/httpasyncclient-4.1.4.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpcore/4.4.14/httpcore-4.4.14.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpcore-nio/4.4.14/httpcore-nio-4.4.14.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-rest-client-jackson/2.3.1.Final/quarkus-rest-client-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-jackson/2.3.1.Final/quarkus-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-databind/2.12.5/jackson-databind-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.12.5/jackson-datatype-jsr310-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-jdk8/2.12.5/jackson-datatype-jdk8-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/module/jackson-module-parameter-names/2.12.5/jackson-module-parameter-names-2.12.5.jar:/home/fschute/.m2/repository/org/jboss/resteasy/resteasy-jackson2-provider/4.7.0.Final/resteasy-jackson2-provider-4.7.0.Final.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-core/2.12.5/jackson-core-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/core/jackson-annotations/2.12.5/jackson-annotations-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/jaxrs/jackson-jaxrs-json-provider/2.12.5/jackson-jaxrs-json-provider-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/jaxrs/jackson-jaxrs-base/2.12.5/jackson-jaxrs-base-2.12.5.jar:/home/fschute/.m2/repository/com/fasterxml/jackson/module/jackson-module-jaxb-annotations/2.12.5/jackson-module-jaxb-annotations-2.12.5.jar:/home/fschute/.m2/repository/com/github/java-json-tools/json-patch/1.13/json-patch-1.13.jar:/home/fschute/.m2/repository/com/github/java-json-tools/msg-simple/1.2/msg-simple-1.2.jar:/home/fschute/.m2/repository/com/github/java-json-tools/btf/1.3/btf-1.3.jar:/home/fschute/.m2/repository/com/github/java-json-tools/jackson-coreutils/2.0/jackson-coreutils-2.0.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-resteasy-jackson/2.3.1.Final/quarkus-resteasy-jackson-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/quarkus-arc/2.3.1.Final/quarkus-arc-2.3.1.Final.jar:/home/fschute/.m2/repository/io/quarkus/arc/arc/2.3.1.Final/arc-2.3.1.Final.jar:/home/fschute/.m2/repository/jakarta/transaction/jakarta.transaction-api/1.3.3/jakarta.transaction-api-1.3.3.jar:/home/fschute/.m2/repository/org/eclipse/microprofile/context-propagation/microprofile-context-propagation-api/1.2/microprofile-context-propagation-api-1.2.jar:/home/fschute/.m2/repository/io/smallrye/common/smallrye-common-io/1.6.0/smallrye-common-io-1.6.0.jar:/home/fschute/.m2/repository/org/apache/httpcomponents/httpclient/4.5.13/httpclient-4.5.13.jar:/home/fschute/.m2/repository/commons-codec/commons-codec/1.15/commons-codec-1.15.jar com.packt.microprofile.book.ch4.jsonb.JsonbConverter
{"hasHyperdrive":true,"name":"Coreillian Freighter","speedRating":22,"weapons":[{"damageRating":24,"name":"Quad Blaster Turret","type":"Laser"}]}

{
    "hasHyperdrive": true,
    "name": "Coreillian Freighter",
    "speedRating": 22,
    "weapons": [
        {
            "damageRating": 24,
            "name": "Quad Blaster Turret",
            "type": "Laser"
        }
    ]
}
name: Coreillian Freighter
hasHyperdrive: true
speedRating: 22
weapons:
  name: Quad Blaster Turret
  type: Laser
  damageRating: 24


{
    "age": 25,
    "familyName": "Doe",
    "favouriteColour": "Green",
    "firstName": "John"
}
John
null
Doe
null
0

Process finished with exit code 0
```

### Consuming RESTful services with the MicroProfile Rest Client

#### JAX-RS Client APIs

see: com.packt.microprofile.book.ch4.client.JAXRSClient

##### /client/jaxrs/{word}

http://localhost:9080/ch4/rest/client/jaxrs/ThisIsfunny
```
result: ThisIsfunny
```

##### /client/jaxrs/async/future/{word}

http://localhost:9080/ch4/rest/client/jaxrs/async/future/ThisIsfunnyTooButAynsJaxRs
```
result: ThisIsfunnyTooButAynsJaxRs
```

##### /client/jaxrs/async/callback/{words}

First call below url to load the words and synonyms into the service:

http://localhost:9080/ch4/rest/client/mp/funny
```
result: silly,hilarious,jovial
```

Then execute this url that finds for word *funny* and *loud* the synonyms but not the other two:

http://localhost:9080/ch4/rest/client/jaxrs/async/callback/funny,JaxRsAsyncWord2,loud,JaxRsAsyncWord3
```
result: silly,hilarious,jovial cacophonous
```

See logging output from the runtime:

```
>>> 15 GET http://localhost:9080/ch4/rest/thesaurus/funny null
[INFO] <<< 15 http://localhost:9080/ch4/rest/thesaurus/funny silly,hilarious,jovial
[INFO] >>> 16 GET http://localhost:9080/ch4/rest/thesaurus/JaxRsAsyncWord2 null
[INFO] <<< 16 http://localhost:9080/ch4/rest/thesaurus/JaxRsAsyncWord2 JaxRsAsyncWord2
[INFO] [err] javax.ws.rs.NotFoundException: HTTP 404 Not Found
[INFO] [err] 	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
[INFO] [err] 	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
[INFO] [err] 	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
[INFO] [err] 	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
[INFO] [err] 	at org.apache.cxf.jaxrs.utils.ExceptionUtils.toWebApplicationException(ExceptionUtils.java:179)
[INFO] [err] 	at [internal classes]
[INFO] >>> 17 GET http://localhost:9080/ch4/rest/thesaurus/loud null
[INFO] [err] 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[INFO] [err] 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[INFO] [err] 	at java.base/java.lang.Thread.run(Thread.java:829)
[INFO] <<< 17 http://localhost:9080/ch4/rest/thesaurus/loud cacophonous
[INFO] >>> 18 GET http://localhost:9080/ch4/rest/thesaurus/JaxRsAsyncWord3 null
[INFO] <<< 18 http://localhost:9080/ch4/rest/thesaurus/JaxRsAsyncWord3 JaxRsAsyncWord3
[INFO] [err] javax.ws.rs.NotFoundException: HTTP 404 Not Found
[INFO] [err] 	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
[INFO] [err] 	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:62)
[INFO] [err] 	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
[INFO] [err] 	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:490)
[INFO] [err] 	at org.apache.cxf.jaxrs.utils.ExceptionUtils.toWebApplicationException(ExceptionUtils.java:179)
[INFO] [err] 	at [internal classes]
[INFO] [err] 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[INFO] [err] 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[INFO] [err] 	at java.base/java.lang.Thread.run(Thread.java:829)
```

#### MicroProfile Rest Client

##### SYNC - ThesaurusClient - /client/mp/{word}

*Client:*

See: com.packt.microprofile.book.ch4.client.MPRestClient

See: com.packt.microprofile.book.ch4.client.ThesaurusClient

*Implementation:*

See: com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource

ThesaurusClient / ThesaurusResource

@GET / com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.getSynonymsFor

@POST / com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.setSynonymsFor

@PUT / com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.updateSynonymsFor

@DELETE / com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.deleteSynonyms

@PATCH / com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource.addNewSynonymsFor

**@GET existing word with synonyms:**
http://localhost:9080/ch4/rest/client/mp/funny
```
result: silly,hilarious,jovial
```

**@GET not existing word with synonyms:**
http://localhost:9080/ch4/rest/client/mp/funnyXX
```
result: funnyXX
```

##### ASYNC - ThesaurusAsyncClient - /client/mp/async/{word}

*Client:*

See: com.packt.microprofile.book.ch4.client.MPRestClient

See: com.packt.microprofile.book.ch4.client.ThesaurusClient

*Implementation:*

See: com.packt.microprofile.book.ch4.thesaurus.ThesaurusResource

**@GET existing word with synonyms:**
http://localhost:9080/ch4/rest/client/mp/async/loud,UnKnown,funny
```
NOTE: UnKnown not found getting "unable to complete request"

result: cacophonous silly,hilarious,jovial unable to complete request
```

**@GET not existing word with synonyms:**
http://localhost:9080/ch4/rest/client/mp/async/funnyXX
```
result: unable to complete request
```

#### headerpropagation

no working example: only code to look at that is not complete.

see: com.packt.microprofile.book.ch4.headerpropagation.AirlineReservationClient

not available:
http://localhost:9080/ch4/rest/AirlineReservationClient

#### dynamicbinding

see: com.packt.microprofile.book.ch4.dynamicbinding.DynamicResource

no working example: only code to look at that is not complete.

not available:
http://localhost:9080/ch4/rest/dynamic

#### cdi

see: com.packt.microprofile.book.ch4.cdi.MyCdiResource

http://localhost:9080/ch4/rest/cdi
```
result: -2147483648

result: from log

[INFO] >>> 1 GET http://localhost:9080/ch4/rest/cdi null
[INFO] com.packt.microprofile.book.ch4.cdi.SomeOtherBean.produceDependency() [return new MyProducedDependency(intRandom=0);]
[INFO] CONSTRUCTOR: com.packt.microprofile.book.ch4.cdi.MyProducedDependency.MyProducedDependency(randomNumber=0=)
[INFO] com.packt.microprofile.book.ch4.cdi.MyThirdDependencyImpl.getInstanceId() [return Integer.MIN_VALUE=-2147483648=]
[INFO] com.packt.microprofile.book.ch4.cdi.MyCdiResource.getDependencyInstanceId(): [dependency=com.packt.microprofile.book.ch4.cdi.MyThirdDependencyImpl@562fe0dc=] [result=-2147483648=]
[INFO] <<< 1 http://localhost:9080/ch4/rest/cdi -2147483648
```


http://localhost:9080/ch4/rest/cdi/produced
```
result: 0

result: from log

[INFO] >>> 5 GET http://localhost:9080/ch4/rest/cdi/produced null
[INFO] com.packt.microprofile.book.ch4.cdi.MyProducedDependency.getRandomNumber() [return randomNumber=0=]
[INFO] com.packt.microprofile.book.ch4.cdi.MyCdiResource.getProducedDependencyRandomNum(): [producedDependency=com.packt.microprofile.book.ch4.cdi.MyProducedDependency@2de3a06a=] [result=0=]
[INFO] <<< 5 http://localhost:9080/ch4/rest/cdi/produced 0
```

### cdi thesaurus

first thesaurus need to load data:

http://localhost:9080/ch4/rest/client/mp/async/funny

then check for word: funny

http://localhost:9080/ch4/rest/cdi/thesaurus/funny
```
result: silly,hilarious,jovial
```

then check for word: funnyXXX

http://localhost:9080/ch4/rest/cdi/thesaurus/funnyXXX
```
result: Sorry, that word is not found.
```

# END.