package models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import enums.ConditionType;
import enums.OrderType;

import java.util.Date;

public class Order {

    @JsonProperty( "OrderUuid" )
    private String id;

    @JsonProperty( "Exchange" )
    private String market;

    @JsonProperty( "Type" )
    private OrderType type;

    @JsonProperty( "Quantity" )
    private double quantity;

    @JsonProperty( "QuantityRemaining" )
    private double remainingQuantity;

    @JsonProperty( "Limit" )
    private double limit;

    @JsonProperty( "Reserved" )
    private double reserved;

    @JsonProperty( "ReserveRemaining" )
    private double remainingReserve;

    @JsonProperty( "CommissionReserved" )
    private double reservedCommission;

    @JsonProperty( "CommissionReserveRemaining" )
    private double remainingCommissionReserve;

    @JsonProperty( "CommissionPaid" )
    private double paidCommission;

    @JsonProperty( "Price" )
    private double price;

    @JsonProperty( "PricePerUnit" )
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private double pricePerUnit;

    @JsonProperty( "Opened" )
    private Date openedAt;

    @JsonProperty( "Closed" )
    private Date closedAt;

    @JsonProperty( "IsOpen" )
    private boolean open;

    @JsonProperty( "Sentinel" )
    private String sentinel;

    @JsonProperty( "CancelInitiated" )
    private boolean cancelInitiated;

    @JsonProperty( "ImmediateOrCancel" )
    private boolean immediateOrCancel;

    @JsonProperty( "IsConditional" )
    private boolean conditional;

    @JsonProperty( "Condition" )
    private ConditionType condition;

    @JsonProperty( "ConditionTarget" )
    @JsonInclude( JsonInclude.Include.NON_NULL )
    private double conditionTarget;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket( String market ) {
        this.market = market;
    }

    public OrderType getType() {
        return type;
    }

    public void setType( OrderType type ) {
        this.type = type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity( double quantity ) {
        this.quantity = quantity;
    }

    public double getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity( double remainingQuantity ) {
        this.remainingQuantity = remainingQuantity;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit( double limit ) {
        this.limit = limit;
    }

    public double getReserved() {
        return reserved;
    }

    public void setReserved( double reserved ) {
        this.reserved = reserved;
    }

    public double getRemainingReserve() {
        return remainingReserve;
    }

    public void setRemainingReserve( double remainingReserve ) {
        this.remainingReserve = remainingReserve;
    }

    public double getReservedCommission() {
        return reservedCommission;
    }

    public void setReservedCommission( double reservedCommission ) {
        this.reservedCommission = reservedCommission;
    }

    public double getRemainingCommissionReserve() {
        return remainingCommissionReserve;
    }

    public void setRemainingCommissionReserve( double remainingCommissionReserve ) {
        this.remainingCommissionReserve = remainingCommissionReserve;
    }

    public double getPaidCommission() {
        return paidCommission;
    }

    public void setPaidCommission( double paidCommission ) {
        this.paidCommission = paidCommission;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit( double pricePerUnit ) {
        this.pricePerUnit = pricePerUnit;
    }

    public Date getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt( Date openedAt ) {
        this.openedAt = openedAt;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public void setClosedAt( Date closedAt ) {
        this.closedAt = closedAt;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen( boolean open ) {
        this.open = open;
    }

    public String getSentinel() {
        return sentinel;
    }

    public void setSentinel( String sentinel ) {
        this.sentinel = sentinel;
    }

    public boolean isCancelInitiated() {
        return cancelInitiated;
    }

    public void setCancelInitiated( boolean cancelInitiated ) {
        this.cancelInitiated = cancelInitiated;
    }

    public boolean isImmediateOrCancel() {
        return immediateOrCancel;
    }

    public void setImmediateOrCancel( boolean immediateOrCancel ) {
        this.immediateOrCancel = immediateOrCancel;
    }

    public boolean isConditional() {
        return conditional;
    }

    public void setConditional( boolean conditional ) {
        this.conditional = conditional;
    }

    public ConditionType getCondition() {
        return condition;
    }

    public void setCondition( ConditionType condition ) {
        this.condition = condition;
    }

    public double getConditionTarget() {
        return conditionTarget;
    }

    public void setConditionTarget( double conditionTarget ) {
        this.conditionTarget = conditionTarget;
    }

}
