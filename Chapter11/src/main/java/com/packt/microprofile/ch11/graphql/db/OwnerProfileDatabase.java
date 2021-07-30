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
package com.packt.microprofile.ch11.graphql.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import com.packt.microprofile.ch11.graphql.model.ManagedOwnerProfileImpl;
import com.packt.microprofile.ch11.graphql.model.OwnerProfile;
import com.packt.microprofile.ch11.graphql.model.OwnerProfileImpl;

@ApplicationScoped
public class OwnerProfileDatabase {

    private Map<String, OwnerProfile> owners = new HashMap<>();

    protected void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        owners.put("Emily J", new OwnerProfileImpl("Emily J", "emilyj@notmyrealaddress.com"));
        owners.put("Andy M", new ManagedOwnerProfileImpl("Andy M", "andym@notmyrealaddress.com",
                                                         "John A", "johna@notmyrealaddress.com"));
    }

    public OwnerProfile getProfile(String ownerId) {
        return owners.get(ownerId);
    }

    public Collection<OwnerProfile> getAllProfiles() {
        return owners.values().stream().collect(Collectors.toList());
    }

    public void addProfile(OwnerProfile newProfile) throws DuplicatePortfolioOwnerException {
        if (null != owners.putIfAbsent(newProfile.getOwnerId(), newProfile)) {
            throw new DuplicatePortfolioOwnerException(newProfile.getOwnerId());
        }
    }
}
