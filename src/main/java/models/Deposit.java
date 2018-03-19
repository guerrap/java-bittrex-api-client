package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Deposit {

    @JsonProperty( "Id" )
    private Long id;

    @JsonProperty( "Amount" )
    private double amount;

    @JsonProperty( "Currency" )
    private String currency;

    @JsonProperty( "Confirmations" )
    private Long confirmations;

    @JsonProperty( "LastUpdated" )
    private Date lastUpdated;

    @JsonProperty( "TxId" )
    private String TxId;

    @JsonProperty( "CryptoAddress" )
    private String cryptoAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getConfirmations() {
        return confirmations;
    }

    public void setConfirmations(Long confirmations) {
        this.confirmations = confirmations;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getTxId() {
        return TxId;
    }

    public void setTxId(String txId) {
        TxId = txId;
    }

    public String getCryptoAddress() {
        return cryptoAddress;
    }

    public void setCryptoAddress(String cryptoAddress) {
        this.cryptoAddress = cryptoAddress;
    }
}
