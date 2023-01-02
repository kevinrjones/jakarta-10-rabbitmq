package com.knowledgespike.quotes.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InsuranceMessage {
    int price;
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "InsuranceMessage{" +
                "price=" + price +
                '}';
    }
}
