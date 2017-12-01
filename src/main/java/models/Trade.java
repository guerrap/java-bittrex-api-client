package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import enums.FillType;
import enums.OrderSide;

import java.util.Date;

public class Trade {

    @JsonProperty( "Id" )
    private long id;

    @JsonProperty( "TimeStamp" )
    private Date timestamp;

    @JsonProperty( "Quantity" )
    private double quantity;

    @JsonProperty( "Price" )
    private double unitPrice;

    @JsonProperty( "Total" )
    private double totalPrice;

    @JsonProperty( "FillType" )
    private FillType fillType;

    @JsonProperty( "OrderType" )
    private OrderSide side;

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp( Date timestamp ) {
        this.timestamp = timestamp;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity( double quantity ) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice( double unitPrice ) {
        this.unitPrice = unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice( double totalPrice ) {
        this.totalPrice = totalPrice;
    }

    public FillType getFillType() {
        return fillType;
    }

    public void setFillType( FillType fillType ) {
        this.fillType = fillType;
    }

    public OrderSide getSide() {
        return side;
    }

    public void setSide( OrderSide side ) {
        this.side = side;
    }

}
