package com.packt.microprofile.book.ch6.opentracing;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;;

@ApplicationScoped
@ApplicationPath("/")
public class TracingApplication extends Application {
	
}