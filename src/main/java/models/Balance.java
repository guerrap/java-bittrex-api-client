package models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Balance {

    @JsonProperty( "Currency" )
    private String currency;

    @JsonProperty( "Balance" )
    private double balance;

    @JsonProperty( "Available" )
    private double available;

    @JsonProperty( "Pending" )
    private double pending;

    @JsonProperty( "CryptoAddress" )
    private String walletAddress;

    @JsonProperty( "Requested" )
    private boolean requested;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency( String currency ) {
        this.currency = currency;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance( double balance ) {
        this.balance = balance;
    }

    public double getAvailable() {
        return available;
    }

    public void setAvailable( double available ) {
        this.available = available;
    }

    public double getPending() {
        return pending;
    }

    public void setPending( double pending ) {
        this.pending = pending;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress( String walletAddress ) {
        this.walletAddress = walletAddress;
    }

    public boolean isRequested() {
        return requested;
    }

    public void setRequested( boolean requested ) {
        this.requested = requested;
    }

}
