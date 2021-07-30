package com.packt.microprofile.book.ch4.hello;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class HelloWorldApp extends Application { }