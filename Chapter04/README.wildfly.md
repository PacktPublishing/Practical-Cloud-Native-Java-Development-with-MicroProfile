# README.wildfly.md

## Build and excute
```
mvn -f pom.xml.wildfly clean package
java -jar -Djboss.http.port=9080 target/ch4-bootable.jar
```

## Endpoints

http://localhost:9080/ch4/rest/hello
```
Hello World!
```

http://localhost:9080/ch4/rest/client/mp/funny
```
result: silly,hilarious,jovial
```

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

http://localhost:9080/ch4/rest/test/sse
```
result: foo bar baz 
```

# END.