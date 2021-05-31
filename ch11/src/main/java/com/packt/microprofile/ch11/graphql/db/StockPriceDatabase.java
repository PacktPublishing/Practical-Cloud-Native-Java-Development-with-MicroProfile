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

import java.util.HashMap;
import java.util.Map;

public class StockPriceDatabase {

    private static StockPriceDatabase INSTANCE = new StockPriceDatabase();

    private Map<String, Double> stockPrices = new HashMap<>();

    private StockPriceDatabase() {
        stockPrices.put("IBM", 143.74);
        stockPrices.put("GOOG", 2411.56);
        stockPrices.put("MSFT", 249.68);
    }
    public static StockPriceDatabase instance() {
        return INSTANCE;
    }

    public Double getPrice(String symbol) {
        return stockPrices.get(symbol);
    }


}
