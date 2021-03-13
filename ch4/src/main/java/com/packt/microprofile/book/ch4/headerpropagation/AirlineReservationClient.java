package com.packt.microprofile.book.ch4.headerpropagation;

import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.eclipse.microprofile.rest.client.annotation.ClientHeaderParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
@ClientHeaderParam(name = "AgentID", value = "Bob's Travel Agency")
public interface AirlineReservationClient {

    @GET
    Reservation getReservation(String reservationID);

    @POST
    @ClientHeaderParam(name = "RequestID", value = "{generateId}")
    String makeReservation(Reservation r);

    default String generateId() {
        return UUID.randomUUID().toString();
    }
}
