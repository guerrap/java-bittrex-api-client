package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OrderBook {

    @JsonProperty( "buy" )
    private List< OrderBookOrder > buys;

    @JsonProperty( "sell" )
    private List< OrderBookOrder > sells;

    public List< OrderBookOrder > getBuys() {
        return buys;
    }

    public void setBuys( List< OrderBookOrder > buys ) {
        this.buys = buys;
    }

    public List< OrderBookOrder > getSells() {
        return sells;
    }

    public void setSells( List< OrderBookOrder > sells ) {
        this.sells = sells;
    }

}
