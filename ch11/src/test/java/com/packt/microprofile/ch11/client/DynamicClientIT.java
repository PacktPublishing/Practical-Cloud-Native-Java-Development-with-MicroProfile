/*
   Copyright 2021 IBM Corp All Rights Reserved

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.packt.microprofile.ch11.client;

import static io.smallrye.graphql.client.core.Document.document;
import static io.smallrye.graphql.client.core.Field.field;
import static io.smallrye.graphql.client.core.Operation.operation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.function.Supplier;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.junit.jupiter.api.Test;

import io.smallrye.graphql.client.Request;
import io.smallrye.graphql.client.Response;
import io.smallrye.graphql.client.core.Document;
import io.smallrye.graphql.client.core.Field;
import io.smallrye.graphql.client.core.OperationType;
import io.smallrye.graphql.client.dynamic.RequestImpl;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClient;
import io.smallrye.graphql.client.dynamic.api.DynamicGraphQLClientBuilder;

public class DynamicClientIT {
    private static final String URL = "http://localhost:9080/graphql";

    @Test
    public void testAllProfilesWithStringDocument() throws Exception {
        verify(() -> executeSync(new RequestImpl("query allProfiles {"
                                                +"  allProfiles {"
                                                +"     ownerId, emailAddress"
                                                +"  }"
                                                +"}")));
    }

    @Test
    public void testAllProfilesWithConstructedDocument() throws Exception {
        Field query = field("allProfiles");
        query.setFields(Arrays.asList(field("ownerId"), field("emailAddress")));
        verify(() -> executeSync(document(operation(OperationType.QUERY, "allProfiles", query))));
    }

    private DynamicGraphQLClient newClient() {
        return DynamicGraphQLClientBuilder.newBuilder().url(URL).build();
    }
    private Response executeSync(Request req) {
        try (DynamicGraphQLClient client = newClient()) {
            return client.executeSync(req);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }
    private Response executeSync(Document doc) {
        try (DynamicGraphQLClient client = newClient()) {
            return client.executeSync(doc);
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

    private void verify(Supplier<Response> responseSupplier) throws Exception {
        Response resp = responseSupplier.get();
        JsonObject data = resp.getData();
        assertNotNull(data);
        System.out.println(data);
        JsonArray allProfiles = data.getJsonArray("allProfiles");
        assertNotNull(allProfiles);
        JsonObject emily = allProfiles.getJsonObject(0);
        assertNotNull(emily);
        assertEquals("Emily J", emily.getString("ownerId"));
        assertEquals("emilyj@notmyrealaddress.com", emily.getString("emailAddress"));
        JsonObject andy = allProfiles.getJsonObject(1);
        assertNotNull(andy);
        assertEquals("Andy M", andy.getString("ownerId"));
        assertEquals("andym@notmyrealaddress.com", andy.getString("emailAddress"));
    }
}
