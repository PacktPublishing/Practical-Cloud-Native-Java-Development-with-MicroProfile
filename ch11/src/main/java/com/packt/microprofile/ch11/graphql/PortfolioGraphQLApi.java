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

import com.packt.microprofile.ch11.graphql.db.DuplicatePortfolioOwnerException;
import com.packt.microprofile.ch11.graphql.db.PortfolioDatabase;
import com.packt.microprofile.ch11.graphql.db.Tuple;
import com.packt.microprofile.ch11.graphql.db.UnknownPortfolioException;
import com.packt.microprofile.ch11.graphql.model.Loyalty;
import com.packt.microprofile.ch11.graphql.model.Portfolio;
import com.packt.microprofile.ch11.graphql.model.Stock;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import org.eclipse.microprofile.graphql.GraphQLApi;
import org.eclipse.microprofile.graphql.GraphQLException;
import org.eclipse.microprofile.graphql.Mutation;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.Query;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@GraphQLApi
public class PortfolioGraphQLApi {
    private static final Logger LOG = Logger.getLogger(PortfolioGraphQLApi.class.getName());

    @Inject
    private PortfolioDatabase portfolioDB;

    @Query
    @Description("Returns the portfolio of the given owner.")
    public Portfolio portfolio(@Name("owner") String owner) throws UnknownPortfolioException {
        LOG.log(Level.INFO, "portfolio invoked [{0}]", owner);
        return Optional.ofNullable(portfolioDB.getPortfolio(owner))
                                              .orElseThrow(() -> new UnknownPortfolioException(owner));
    }

    @Query
    @Description("List all portfolios managed by this service")
    public Collection<Portfolio> allPortfolios() {
        LOG.info("allPortfolios invoked");
        return portfolioDB.getAllPortfolios();
    }

    @Query("portfolios")
    @Description("List all portfolios managed by this service - pagination")
    public Collection<Portfolio> allPortfolios(@Name("pageNumber") int pageNumber,
                                               @Name("entriesPerPage") int entriesPerPage) {
        LOG.info(String.format("allPortfolios(%d, %d) invoked ", pageNumber, entriesPerPage));
        if (pageNumber < 1 || entriesPerPage < 1) {
            throw new IllegalArgumentException("Pagination arguments must be >= 1");
        }
        Portfolio[] allPortfolios = portfolioDB.getAllPortfolios().toArray(new Portfolio[]{});
        int start = (pageNumber - 1) * entriesPerPage;
        int end = start + entriesPerPage;
        return Arrays.asList(Arrays.copyOfRange(allPortfolios, start, end));
    }

    @Query
    @Description("List all portfolios whose total value is equal to or greater than the value specified")
    public Collection<Portfolio> portfoliosOver(@Name("minValue") double minVal) {
        return allPortfoliosByFilter(p -> p.getTotal() >= minVal);
    }

    @Query
    @Description("List all portfolios that contain at least the specified number of  shares of the specified stock")
    public Collection<Portfolio> portfoliosContainingAtLeast(@Name("num") @DefaultValue("1") int num,
                                                             @Name("symbol") String symbol) {
        return allPortfoliosByFilter(p -> p.getStocks().stream()
            .anyMatch(s -> s.getSymbol().equals(symbol) && s.getShares() >= num));
    }

    @Mutation
    public Portfolio createNewPortfolio(@Name("portfolio") Portfolio portfolio) throws DuplicatePortfolioOwnerException,
                                                                                       UnknownPortfolioException {
        LOG.log(Level.INFO, "createNewPortfolio invoked [{0}]", portfolio);
        portfolioDB.addPortfolio(portfolio);
        return portfolio(portfolio.getOwner());
    }

    @Mutation
    public Collection<Portfolio> createNewPortfolios(@Name("portfolios") List<Portfolio> newPortfolios) 
            throws GraphQLException, UnknownPortfolioException {
        LOG.log(Level.INFO, "createNewPortfolios invoked [{0}]", newPortfolios);
        Tuple<Collection<Portfolio>, Collection<String>> tuple = portfolioDB.addPortfolios(newPortfolios);
        if (!tuple.second.isEmpty()) {
            // some of the portfolios to be added already exist - throw an exception with partial results
            throw new GraphQLException("The following portfolios already exist and cannot be re-added: " + tuple.second,
                                       tuple.first); // these are the results that were added
        }
        return tuple.first;
    }

    @Mutation
    public boolean createRandomPortfolios(@Name("num") int num) 
            throws DuplicatePortfolioOwnerException, UnknownPortfolioException {
        for (int i=0; i<num; i++) {
            Portfolio p = new Portfolio(UUID.randomUUID().toString(), Math.random() * 1000, 
                    "" + (int) (Math.random() * 65535));
            switch((int) Math.random() * 3) {
                case 0: p.setLoyalty(Loyalty.BRONZE);
                case 1: p.setLoyalty(Loyalty.SILVER);
                default: p.setLoyalty(Loyalty.GOLD);
            }
            List<Stock> stocks = new ArrayList<>();
            stocks.add(new Stock("IBM"));
            stocks.get(0).setShares((int) (Math.random() * 65535));
            p.setStocks(stocks);
            createNewPortfolio(p);
        }
        return true;
    }

    private Collection<Portfolio> allPortfoliosByFilter(Predicate<Portfolio> predicate) {
        return portfolioDB.getAllPortfolios()
                .stream()
                .filter(predicate)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
