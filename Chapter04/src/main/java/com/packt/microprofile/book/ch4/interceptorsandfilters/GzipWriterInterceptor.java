package com.packt.microprofile.book.ch4.interceptorsandfilters;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

//@Provider // this is commented out to allow the other samples to run without returning
            // Gzipped responses. To test this sample, please uncomment the @Provider
            // annotation and recompile.
public class GzipWriterInterceptor implements WriterInterceptor {

    @Override
    public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
        GZIPOutputStream gzos = new GZIPOutputStream(context.getOutputStream());
        context.getHeaders().putSingle(HttpHeaders.CONTENT_ENCODING, "gzip");
        context.getHeaders().putSingle("X-hello", "world");
        context.setOutputStream(gzos);
        context.proceed();
    }
}
