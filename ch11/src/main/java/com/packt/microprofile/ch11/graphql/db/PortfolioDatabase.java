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

import com.packt.microprofile.ch11.graphql.model.Portfolio;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

@ApplicationScoped
public class PortfolioDatabase {
    private final Map<String, Portfolio> allPortfolios = new HashMap<>();

    protected void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        try {
            Jsonb jsonb = JsonbBuilder.create();
            String mapJson = getInitalJson();
            addPortfolios(jsonb.fromJson(mapJson,
                    new ArrayList<Portfolio>() {
                    }.getClass().getGenericSuperclass()));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public Portfolio getPortfolio(String name) throws UnknownPortfolioException {
        Portfolio portfolio = allPortfolios.get(name);

        if (portfolio == null) {
            throw new UnknownPortfolioException(name);
        }

        return portfolio;
    }

    public Collection<Portfolio> getAllPortfolios() {
        return allPortfolios.values();
    }

    public Tuple<Collection<Portfolio>, Collection<String>> addPortfolios(Collection<Portfolio> portfolios) {
        List<Portfolio> addedPortfolios = new ArrayList<>();
        List<String> duplicatedOwners = new ArrayList<>();
        for (Portfolio portfolio : portfolios) {
            try {
                addPortfolio(portfolio);
                addedPortfolios.add(portfolio);
            } catch (DuplicatePortfolioOwnerException ex) {
                duplicatedOwners.add(ex.getMessage());
            }
        }
        return new Tuple<>(addedPortfolios, duplicatedOwners);
    }

    public void addPortfolio(Portfolio portfolio) throws DuplicatePortfolioOwnerException {
        if (null != allPortfolios.putIfAbsent(portfolio.getOwner(), portfolio)) {
            throw new DuplicatePortfolioOwnerException(portfolio.getOwner());
        };
    }

    public Portfolio removePortfolio(String owner) {
        Portfolio portfolio = allPortfolios.remove(owner);
        if (portfolio == null) {
            return null;
        }
        return portfolio;
    }

    private static String getInitalJson() {
        return "[" +
                    "{" + 
                         "\"owner\": \"Emily J\"," +
                         "\"total\": 50000.00," +
                         "\"accountID\": \"12345\"," +
                         "\"operation\": \"normal\"," +
                         "\"stocks\": [" +
                                         "{" +
                                             "\"symbol\": \"IBM\"," +
                                             "\"shares\": 500" +
                                         "}," +
                                         "{" +
                                             "\"symbol\": \"MSFT\"," +
                                             "\"shares\": 200" +
                                         "}" +
                                     "]" +
                    "}" +
               "]";
    }
}
