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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi;
import io.smallrye.graphql.client.typesafe.api.TypesafeGraphQLClientBuilder;

public class TypeSafeClientIT {

    static class OwnerProfile {
        String ownerId;
        String emailAddress;

        public String getOwnerId() {
            return ownerId;
        }
        public void setOwnerId(String ownerId) {
            this.ownerId = ownerId;
        }
        public String getEmailAddress() {
            return emailAddress;
        }
        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }
    }

    @GraphQLClientApi
    static interface ProfileApi {
        List<OwnerProfile> allProfiles();
    }

    @Test
    public void testAllProfiles() throws Exception {
        //System.out.println("classpath: " + System.getProperty("java.class.path"));
        ProfileApi api = TypesafeGraphQLClientBuilder.newBuilder()
                                                     .endpoint("http://localhost:9079/graphql")
                                                     .build(ProfileApi.class);
        List<OwnerProfile> allProfiles = api.allProfiles();
        assertNotNull(allProfiles);
        assertEquals(2, allProfiles.size());
        assertEquals("Emily J", allProfiles.get(0).getOwnerId());
        assertEquals("emilyj@notmyrealaddress.com", allProfiles.get(0).getEmailAddress());
        assertEquals("Andy M", allProfiles.get(1).getOwnerId());
        assertEquals("andym@notmyrealaddress.com", allProfiles.get(1).getEmailAddress());
    }
}
