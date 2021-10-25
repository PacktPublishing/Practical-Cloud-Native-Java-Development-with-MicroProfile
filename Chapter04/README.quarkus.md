# README.quarkus.md

## Build and excute
```
mvn -f pom.xml.quarkus clean package quarkus:dev
```

## Endpoints

http://localhost:8080/rest
```
overview page of runtime.
```

http://localhost:8080/rest/hello
```
Hello World!
```

http://localhost:8080/rest/client/mp/funny
```
result: silly,hilarious,jovial
```

http://localhost:8080/rest/sse
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

http://localhost:8080/rest/test/sse
```
result: foo bar baz 
```

# END.