package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Withdrawal {

    @JsonProperty( "PaymentUuid" )
    private String paymentUuid;

    @JsonProperty( "Currency" )
    private String currency;

    @JsonProperty( "Amount" )
    private double amount;

    @JsonProperty( "Address" )
    private String address;

    @JsonProperty( "Opened" )
    private Date opened;

    @JsonProperty( "Authorized" )
    private Boolean authorized;

    @JsonProperty( "PendingPayment" )
    private Boolean pendingPayments;

    @JsonProperty( "TxCost" )
    private double txCost;

    @JsonProperty( "TxId" )
    private String txId;

    @JsonProperty( "Canceled" )
    private Boolean canceled;

    @JsonProperty( "InvalidAddress" )
    private Boolean invalidAddress;

    public String getPaymentUuid() {
        return paymentUuid;
    }

    public void setPaymentUuid(String paymentUuid) {
        this.paymentUuid = paymentUuid;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOpened() {
        return opened;
    }

    public void setOpened(Date opened) {
        this.opened = opened;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public Boolean getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(Boolean pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public Double getTxCost() {
        return txCost;
    }

    public void setTxCost(Double txCost) {
        this.txCost = txCost;
    }

    public String getTxId() {
        return txId;
    }

    public void setTxId(String txId) {
        this.txId = txId;
    }

    public Boolean getCanceled() {
        return canceled;
    }

    public void setCanceled(Boolean canceled) {
        this.canceled = canceled;
    }

    public Boolean getInvalidAddress() {
        return invalidAddress;
    }

    public void setInvalidAddress(Boolean invalidAddress) {
        this.invalidAddress = invalidAddress;
    }
}
