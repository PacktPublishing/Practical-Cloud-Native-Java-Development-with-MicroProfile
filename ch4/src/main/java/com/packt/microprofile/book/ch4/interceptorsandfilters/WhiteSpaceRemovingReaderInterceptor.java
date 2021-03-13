package com.packt.microprofile.book.ch4.interceptorsandfilters;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;

//@Provider
public class WhiteSpaceRemovingReaderInterceptor implements ReaderInterceptor {

    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {

        byte[] b = new byte[1024];
        InputStream originalStream = context.getInputStream();
        int bytesRead = originalStream.read(b);
        StringBuilder sb = new StringBuilder();
        while (bytesRead >= 0) {
            sb.append(new String(b, 0, bytesRead));
            bytesRead = originalStream.read(b);
        }

        String entity = sb.toString();
        System.out.println("PRE: " + entity);
        entity = entity.replaceAll("\\s","");
        System.out.println("POST: " + entity);

        ByteArrayInputStream bais = new ByteArrayInputStream(entity.getBytes());
        context.setInputStream(bais);

        return context.proceed();
    }
}
