package enums;

public enum OrderBookType {

    BUYS( "buys" ),
    SELLS( "sells" ),
    BOTH( "both" );

    private String value;

    OrderBookType( String value ) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
