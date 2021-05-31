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
package com.packt.microprofile.ch11.graphql;

import java.util.Collection;

import javax.inject.Inject;

import com.packt.microprofile.ch11.graphql.db.DuplicatePortfolioOwnerException;
import com.packt.microprofile.ch11.graphql.db.OwnerProfileDatabase;
import com.packt.microprofile.ch11.graphql.db.PortfolioDatabase;
import com.packt.microprofile.ch11.graphql.db.UnknownPortfolioException;
import com.packt.microprofile.ch11.graphql.model.Loyalty;
import com.packt.microprofile.ch11.graphql.model.ManagedOwnerProfileImpl;
import com.packt.microprofile.ch11.graphql.model.OwnerProfile;
import com.packt.microprofile.ch11.graphql.model.OwnerProfileImpl;

import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Query;
import org.eclipse.microprofile.graphql.Source;

@GraphQLApi
public class ProfileGraphQLApi {

    @Inject
    OwnerProfileDatabase db;

    @Inject
    PortfolioDatabase portfolioDB;

    @Query
    public OwnerProfile profile(String ownerId) {
        return db.getProfile(ownerId);
    }

    @Query
    public Collection<OwnerProfile> allProfiles() {
        return db.getAllProfiles();
    }

    @Mutation
    public boolean addProfile(OwnerProfileImpl profile) throws DuplicatePortfolioOwnerException {
        db.addProfile(profile);
        return true;
    }

    @Mutation
    public boolean addManagedProfile(ManagedOwnerProfileImpl profile) throws DuplicatePortfolioOwnerException {
        db.addProfile(profile);
        return true;
    }

    public Loyalty getLoyalty(@Source OwnerProfileImpl profile) throws UnknownPortfolioException {
        return portfolioDB.getPortfolio(profile.getOwnerId()).getLoyalty();
    }

    public Loyalty getLoyalty(@Source ManagedOwnerProfileImpl profile) throws UnknownPortfolioException {
        return getLoyalty((OwnerProfileImpl) profile);
    }
}
