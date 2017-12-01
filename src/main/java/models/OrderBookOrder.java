package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OrderBookOrder {

    @JsonProperty( "Quantity" )
    private String quantity;

    @JsonProperty( "Rate" )
    private String rate;

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity( String quantity ) {
        this.quantity = quantity;
    }

    public String getRate() {
        return rate;
    }

    public void setRate( String rate ) {
        this.rate = rate;
    }

}
