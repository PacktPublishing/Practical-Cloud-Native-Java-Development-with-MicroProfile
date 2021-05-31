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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.microprofile.graphql.Id;

/** JSON-B POJO class representing a Portfolio JSON object */
public class Portfolio {

    private String owner;

    private double total;

    @Id
    private String accountID;

    private Loyalty loyalty = Loyalty.BRONZE; //default

    private List<Stock> stocks = new ArrayList<>();

    public Portfolio() { //default constructor
    }

    public Portfolio(String initialOwner) { //primary key constructor
        setOwner(initialOwner);
    }

    public Portfolio(String initialOwner, double initialTotal) {
        setOwner(initialOwner);
        setTotal(initialTotal);
    }

    public Portfolio(String initialOwner, double initialTotal, String initialAccountID) {
        setOwner(initialOwner);
        setTotal(initialTotal);
        setAccountID(initialAccountID);
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String newOwner) {
        owner = newOwner;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double newTotal) {
        total = newTotal;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String newAccountID) {
        accountID = newAccountID;
    }

    public Loyalty getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(Loyalty newLoyalty) {
        loyalty = newLoyalty;
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public boolean equals(Object obj) {
        boolean isEqual = false;
        if ((obj != null) && (obj instanceof Portfolio)) isEqual = toString().equals(obj.toString());
        return isEqual;
   }

    public String toString() {
        return "{\"owner\": \""+owner+"\", \"total\": "+total+", \"accountID\": \""+accountID+"\", \"operation\": \""+loyalty+"\", \"stocks\": "+(stocks!=null?stocks.toString():"{}")+"}";
    }
}