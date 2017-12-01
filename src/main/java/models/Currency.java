package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( { "Notice" } )
public class Currency {

    @JsonProperty( "Currency" )
    private String shortName;

    @JsonProperty( "CurrencyLong" )
    private String longName;

    @JsonProperty( "MinConfirmation" )
    private int minimumConfirmations;

    @JsonProperty( "TxFee" )
    private double transactionFees;

    @JsonProperty( "IsActive" )
    private boolean active;

    @JsonProperty( "CoinType" )
    private String type;

    @JsonProperty( "BaseAddress" )
    private String address;

    public String getShortName() {
        return shortName;
    }

    public void setShortName( String shortName ) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName( String longName ) {
        this.longName = longName;
    }

    public int getMinimumConfirmations() {
        return minimumConfirmations;
    }

    public void setMinimumConfirmations( int minimumConfirmations ) {
        this.minimumConfirmations = minimumConfirmations;
    }

    public double getTransactionFees() {
        return transactionFees;
    }

    public void setTransactionFees( double transactionFees ) {
        this.transactionFees = transactionFees;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive( boolean active ) {
        this.active = active;
    }

    public String getType() {
        return type;
    }

    public void setType( String type ) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress( String address ) {
        this.address = address;
    }

}
