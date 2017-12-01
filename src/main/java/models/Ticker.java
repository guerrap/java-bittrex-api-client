package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Ticker {

    @JsonProperty( "Bid" )
    private double bid;

    @JsonProperty( "Ask" )
    private double ask;

    @JsonProperty( "Last" )
    private double last;

    public double getBid() {
        return bid;
    }

    public void setBid( double bid ) {
        this.bid = bid;
    }

    public double getAsk() {
        return ask;
    }

    public void setAsk( double ask ) {
        this.ask = ask;
    }

    public double getLast() {
        return last;
    }

    public void setLast( double last ) {
        this.last = last;
    }

}
