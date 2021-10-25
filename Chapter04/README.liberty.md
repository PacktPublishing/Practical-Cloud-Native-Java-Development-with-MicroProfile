# README.liberty.md

## Build and excute
```
mvn -f pom.xml.liberty.orig clean package liberty:run
```

## Endpoints

http://localhost:8080/
```
Entry page of runtime.
```

http://localhost:8080/ch4/rest/hello
```
Hello World!
```

http://localhost:8080/ch4/rest/client/mp/funny

**NOTE: Not working! Test data could not be loaded:**

funny not looked up resulting in below error:
```
result: 
Kontextstammverzeichnis nicht gefunden.
Lizenziertes Material — Eigentum von Open Liberty Project, Copyright IBM Corp und andere 1997, 2019. Das Projekt wird unter den Bedingungen von Eclipse Public License v1 verteilt.

com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException:

Expected result:
result: silly,hilarious,jovial
```

http://localhost:8080/ch4/rest/sse
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

http://localhost:8080/ch4/rest/test/sse

**NOTE: Not working! Test data could not be loaded:**
```
result: 
Kontextstammverzeichnis nicht gefunden.
Lizenziertes Material — Eigentum von Open Liberty Project, Copyright IBM Corp und andere 1997, 2019. Das Projekt wird unter den Bedingungen von Eclipse Public License v1 verteilt.

Expected result:
foo bar baz 
```

# END.