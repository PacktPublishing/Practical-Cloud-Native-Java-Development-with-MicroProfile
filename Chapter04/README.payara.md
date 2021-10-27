# README.payara.md

**NOTE: Payara is not ready to execute this code.**
**NOTE: Tested LATEST (6.2021.1.Alpha1) and 5.2021.1 till 5.2021.8 and FAILED.**
**NOTE: In case we change the code on @RestClient injection and move the @Consumes / @Produces annotations directly to the GET, PUT, POST, DELETE and UPDATE method we get it working ...**
**PROBLEM: liberty, quarkus and wíldfly microprofile runtimes are working well with the current code base unchanged.**

Disregard below details as all of them do not work:

## Build and excute
```
mvn -f pom.xml.payara clean package
java -jar target/ch4-microbundle.jar --port 9080 --contextroot ch4
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