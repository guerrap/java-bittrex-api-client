package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.net.URL;
import java.util.Date;

public class Market {

    @JsonProperty( "MarketCurrency" )
    private String marketCurrencyShort;

    @JsonProperty( "BaseCurrency" )
    private String baseCurrencyShort;

    @JsonProperty( "MarketCurrencyLong" )
    private String marketCurrencyLong;

    @JsonProperty( "BaseCurrencyLong" )
    private String baseCurrencyLong;

    @JsonProperty( "MinTradeSize" )
    private double minimumTradeSize;

    @JsonProperty( "MarketName" )
    private String name;

    @JsonProperty( "IsActive" )
    private boolean active;

    @JsonProperty( "Created" )
    private Date createdAt;

    @JsonProperty( "Notice" )
    private String notice;

    @JsonProperty( "IsSponsored" )
    private boolean sponsored;

    @JsonProperty( "LogoUrl" )
    private URL logoUrl;

    public String getMarketCurrencyShort() {
        return marketCurrencyShort;
    }

    public void setMarketCurrencyShort( String marketCurrencyShort ) {
        this.marketCurrencyShort = marketCurrencyShort;
    }

    public String getBaseCurrencyShort() {
        return baseCurrencyShort;
    }

    public void setBaseCurrencyShort( String baseCurrencyShort ) {
        this.baseCurrencyShort = baseCurrencyShort;
    }

    public String getMarketCurrencyLong() {
        return marketCurrencyLong;
    }

    public void setMarketCurrencyLong( String marketCurrencyLong ) {
        this.marketCurrencyLong = marketCurrencyLong;
    }

    public String getBaseCurrencyLong() {
        return baseCurrencyLong;
    }

    public void setBaseCurrencyLong( String baseCurrencyLong ) {
        this.baseCurrencyLong = baseCurrencyLong;
    }

    public double getMinimumTradeSize() {
        return minimumTradeSize;
    }

    public void setMinimumTradeSize( double minimumTradeSize ) {
        this.minimumTradeSize = minimumTradeSize;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive( boolean active ) {
        this.active = active;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice( String notice ) {
        this.notice = notice;
    }

    public boolean isSponsored() {
        return sponsored;
    }

    public void setSponsored( boolean sponsored ) {
        this.sponsored = sponsored;
    }

    public URL getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl( URL logoUrl ) {
        this.logoUrl = logoUrl;
    }

}
