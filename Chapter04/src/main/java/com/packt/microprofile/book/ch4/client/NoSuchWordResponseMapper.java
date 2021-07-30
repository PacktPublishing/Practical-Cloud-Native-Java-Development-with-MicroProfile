package com.packt.microprofile.book.ch4.client;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import com.packt.microprofile.book.ch4.thesaurus.NoSuchWordException;

import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

public class NoSuchWordResponseMapper implements ResponseExceptionMapper<NoSuchWordException> {

    @Override
    public boolean handles(int status, MultivaluedMap<String, Object> headers) {
        return status == 404;
    }

    @Override
    public NoSuchWordException toThrowable(Response response) {
        if (response.hasEntity()) {
            return new NoSuchWordException(response.readEntity(String.class));
        }
        return new NoSuchWordException();
    }
}
