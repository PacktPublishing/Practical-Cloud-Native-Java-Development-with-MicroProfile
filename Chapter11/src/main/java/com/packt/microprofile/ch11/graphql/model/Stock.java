/*
   Copyright 2017-2021 IBM Corp All Rights Reserved

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
package com.packt.microprofile.ch11.graphql.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.packt.microprofile.ch11.graphql.db.StockPriceDatabase;

import org.eclipse.microprofile.graphql.DateFormat;
import org.eclipse.microprofile.graphql.Ignore;
import org.eclipse.microprofile.graphql.Name;
import org.eclipse.microprofile.graphql.NonNull;


/** JSON-B POJO class representing a Stock JSON object */
public class Stock {

    @NonNull
    private String symbol;
    private int shares;
    private double commission;
    @Name("pricePerShare")
    private double price;

    @Name("dateOfLastUpdate")
    @DateFormat("MM/dd/yyyy")
    private String date;

    public Stock() { // default constructor
    }

    public Stock(String initialSymbol) { // primary key constructor
        setSymbol(initialSymbol);
    }

    public Stock(String initialSymbol, int initialShares, double initialCommission, double initialPrice,
            double initialTotal, String initialDate) {
        setSymbol(initialSymbol);
        setShares(initialShares);
        setCommission(initialCommission);
        setPrice(initialPrice);
        setDate(initialDate);
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String newSymbol) {
        symbol = newSymbol;
        updateDate();
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int newShares) {
        shares = newShares;
        updateDate();
    }

    @Ignore
    public double getCommission() {
        return commission;
    }

    public void setCommission(double newCommission) {
        commission = newCommission;
        updateDate();
    }

    public double getPrice() {
        if (price == 0.0) {
            setPrice(StockPriceDatabase.instance().getPrice(symbol));
        }
        return price;
    }

    @Ignore
    public void setPrice(double newPrice) {
        price = newPrice;
        updateDate();
    }

    public double getTotal() {
        return price * shares;
    }

    public String getDate() {
        return date;
    }

    @Ignore
    public void setDate(String newDate) {
        date = newDate;
    }

    void updateDate() {
        setDate(LocalDate.now().format( DateTimeFormatter.ofPattern("MM/dd/yyyy")));
    }

    public String toString() {
        return "{\"symbol\": \"" + symbol + "\", \"shares\": " + shares + ", \"commission\": " + commission
                + ", \"price\": " + price + ", \"total\": " + getTotal() + ", \"date\": \"" + date + "\"}";
    }
}