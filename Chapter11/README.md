# Sample code from Practical Cloud Native Development with MicroProfile 4.0, Chapter 11: MicroProfile GraphQL

## Introduction

The samples in this repository are intended to be a supplement to the book, _Practical Cloud Native Development with
MicroProfile 4.0_ by Emily Jiang, Andy McCright, John Alcorn, David Chan, and Alasdair Nottingham. Chapter 11:
MicroProfile GraphQL discusses how to build a GraphQL application from scratch using annotations and patterns similar
to other Jakarta EE and MicroProfile technologies like JAX-RS or Spring.
This repo contains the sample code featured in this chapter. All samples run on the Open Liberty application server.

## Running the samples

In order to run these samples, you will need to use [Git](https://git-scm.com/) and [Maven](https://maven.apache.org/)
along with a Java Development Kit v8 or higher - I recommend downloading from [AdoptOpenJDK](https://adoptopenjdk.net/).

These samples are best run in an IDE like VS Code, Eclipse, Intelli-J, etc. That way, you can view the source code while
running the sample in a separate window/tab.

To build the code samples and start the server, use the command: `mvn clean package liberty:dev`
This will clean any previously built code, build the source code in the project, download the latest version of Open
Liberty (unless previously downloaded), package the application into a server, and start the server. The first time you
run this command may take a little while, but once Open Liberty has been downloaded and the server packaged, subsequent
starts will be much faster.

This command will run the server in the foreground. Once you see the magic message, 
`CWWKF0011I: The ch11 server is ready to run a smarter planet.`, you are ready to test the application. While in dev
mode, you can run the SmallRye GraphQL Client integration tests simply by pressing `Enter` from the command line. To
view the application's schema or run your own queries and mutations from the GraphiQL web client, you will need to
browse to the application's index page at http://localhost:9080/ch11/index.html.

To stop the server in the current terminal window, press `Ctrl-C`.
