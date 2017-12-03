import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.OrderBookType;
import errors.ApiException;
import errors.AuthenticationException;
import handlers.BittrexRetryHandler;
import models.*;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

/**
 * Represents a single Bittrex API client.
 */
public class BittrexApiClient {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String API_SCHEME = "https";
    private static final String API_HOST = "www.bittrex.com";
    private static final String API_PATH_ROOT = "/api/v1.1/";
    private static final String API_PARAM_TIMESTAMP = "nonce";
    private static final String API_PARAM_API_KEY = "apikey";
    private static final String API_PARAM_API_SIGN = "apisign";

    private String apiKey;
    private String apiSecret;

    /**
     * Initializes a new Bittrex API client.
     */
    public BittrexApiClient() {
        this( null, null );
    }

    /**
     * Initializes a new Bittrex API client through the personal
     * account's API key.
     *
     * @param apiKey The personal account API key.
     */
    public BittrexApiClient( String apiKey ) {
        this( apiKey , null );
    }

    /**
     * Initializes a new Bittrex API client through the personal
     * account's API key and API secret.
     *
     * @param apiKey    The personal account API key.
     * @param apiSecret The personal account API secret.
     */
    public BittrexApiClient( String apiKey, String apiSecret ) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /**
     * Interface to the "public/getmarkets" Bittrex's API operation.
     *
     * @return The open and available trading markets at Bittrex along
     *         with other meta data.
     */
    public List< Market > getMarkets()
        throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest( false, "public/getmarkets" ),
            new TypeReference< List< Market > >() {}
        );

    }

    /**
     * Interface to the "public/getcurrencies" Bittrex's API operation.
     *
     * @return All the supported currencies at Bittrex along with
     *         other meta data.
     */
    public List< Currency > getCurrencies()
        throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest( false, "public/getcurrencies" ),
            new TypeReference< List< Currency > >() {}
        );

    }

    /**
     * Interface to the "public/getticker" Bittrex's API operation.
     *
     * @param market The market of which we would like to retrieve
     *               the ticker.
     *
     * @return The current tick values for a market.
     */
    public Ticker getTicker(
        String market ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest(
                false,
                "public/getticker",
                new BasicNameValuePair( "market", market )
            ),
            Ticker.class
        );

    }

    /**
     * Interface to the "public/getmarketsummaries" Bittrex's API operation.
     *
     * @return The last 24 hour summary of all active markets.
     */
    public List< MarketSummary > getMarketSummaries()
        throws ApiException, URISyntaxException, IOException, AuthenticationException{

        return MAPPER.readValue(
            makeRequest( false, "public/getmarketsummaries" ),
            new TypeReference< List< MarketSummary > >() {}
        );

    }

    /**
     * Interface to the "public/getmarketsummary" Bittrex's API operation.
     *
     * @param market The market of which we would like to retrieve the summary.
     *
     * @return The last 24 hour summary of the specified market.
     */
    public MarketSummary getMarketSummary(
        String market ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        List< MarketSummary > marketSummaries = MAPPER.readValue(
            makeRequest(
                false,
                "public/getmarketsummary",
                new BasicNameValuePair( "market", market )
            ),
            new TypeReference< List< MarketSummary > >() {}
        );
        return marketSummaries.get( 0 );

    }

    /**
     * Interface to the "public/getorderbook" Bittrex's API operation.
     *
     * @param market The market of which we would like to retrieve
     *               the order book.
     * @param type   The type of the order book that we want to
     *               retrieve, depending on if we want only
     *               buys, sells, or both.
     *
     * @return The orderbook for a given market, respecting the
     *         given constraints.
     */
    public OrderBook getOrderBook(
        String market,
        OrderBookType type ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest(
                false,
                "public/getorderbook",
                new BasicNameValuePair( "market", market ),
                new BasicNameValuePair( "type", type == null ? null : type.toString() )
            ),
            OrderBook.class
        );

    }

    /**
     * Interface to the "public/getmarkethistory" Bittrex's API operation.
     *
     * @param market The market of which we would like to retrieve the history.
     *
     * @return The latest trades that have occured for a specific market.
     */
    public List< Trade > getMarketHistory(
        String market ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest(
                false,
                "public/getmarkethistory",
                new BasicNameValuePair( "market", market )
            ),
            new TypeReference< List< Trade > >() {}
        );

    }

    /**
     * Interface to the "public/buylimit" Bittrex's API operation.
     *
     * @param market   The market on which we would like to buy.
     * @param quantity The quantity that we would like to buy.
     * @param rate     The price at which we would like to buy.
     *
     * @return The placed order ID.
     */
    public String limitBuy(
        String market,
        double quantity,
        double rate ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        ObjectNode node = MAPPER.readValue(
            makeRequest(
                true,
                "market/buylimit",
                new BasicNameValuePair( "market", market ),
                new BasicNameValuePair( "quantity", String.valueOf( quantity ) ),
                new BasicNameValuePair( "rate", String.valueOf( rate ) )
            ),
            ObjectNode.class
        );
        return node.get( "uuid" ).textValue();

    }

    /**
     * Interface to the "public/selllimit" Bittrex's API operation.
     *
     * @param market   The market on which we would like to sell.
     * @param quantity The quantity that we would like to sell.
     * @param rate     The price at which we would like to sell.
     *
     * @return The placed order ID.
     */
    public String limitSell(
        String market,
        double quantity,
        double rate ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        ObjectNode node = MAPPER.readValue(
            makeRequest(
                true,
                "market/selllimit",
                new BasicNameValuePair( "market", market ),
                new BasicNameValuePair( "quantity", String.valueOf( quantity ) ),
                new BasicNameValuePair( "rate", String.valueOf( rate ) )
            ),
            ObjectNode.class
        );
        return node.get( "uuid" ).textValue();

    }

    /**
     * Interface to the "public/cancelorder" Bittrex's API operation.
     *
     * @param id The ID of the order we would like to cancel.
     */
    public void cancelOrder(
        String id ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        makeRequest(
            true,
            "market/cancel",
            new BasicNameValuePair( "uuid", id )
        );

    }

    /**
     * Interface to the "market/getopenorders" Bittrex's API operation.
     *
     * @param market Optional. The market on which we would like to retrieve
     *               the open orders.
     *
     * @return All the orders that you currently have opened.
     *         A specific market can be requested (if any)
     */
    public List< OpenOrder > getOpenOrders(
        String market ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest(
                true,
                "market/getopenorders",
                new BasicNameValuePair( "market", market )
            ),
            new TypeReference< List< OpenOrder > >() {}
        );

    }

    /**
     * Interface to the "account/getbalances" Bittrex's API operation.
     *
     * @return All the balances from your account.
     */
    public List< Balance > getBalances() throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest(
                true,
                "account/getbalances"
            ),
            new TypeReference< List< Balance > >() {}
        );

    }

    /**
     * Interface to the "account/getbalance" Bittrex's API operation.
     *
     * @param currency The currency for which we want to retrieve the balance.
     *
     * @return The balance from your account for a specific currency.
     */
    public Balance getBalance( String currency ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        return MAPPER.readValue(
            makeRequest(
                true,
                "account/getbalance",
                new BasicNameValuePair( "currency", currency )
            ),
            Balance.class
        );

    }

    /**
     * Interface to the "account/getdepositaddress" Bittrex's API operation.
     *
     * @param currency The currency of which we would like to retrieve
     *                 the deposit address.
     *
     * @return An address for a specific currency. If one does not exist, the call will
     *         fail and return ADDRESS_GENERATING until one is available.
     */
    public String getDepositAddress( String currency ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        ObjectNode node = MAPPER.readValue(
            makeRequest(
                true,
                "account/getdepositaddress",
                new BasicNameValuePair( "currency", currency )
            ),
            ObjectNode.class
        );
        return node.get( "Address" ).textValue();

    }

    /**
     * Interface to the "account/withdraw" Bittrex's API operation.
     *
     * @param currency  The currency which we would like to withdraw.
     * @param quantity  The quantity which we would like to withdraw.
     * @param address   The address to which we would like to withdraw.
     * @param paymentId Optional parameter used for CryptoNotes/BitShareX/Nxt.
     *
     * @return The withdrawal ID.
     */
    public String withdraw(
        String currency,
        double quantity,
        String address,
        String paymentId ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        ObjectNode node = MAPPER.readValue(
            makeRequest(
                true,
                "account/withdraw",
                new BasicNameValuePair( "currency", currency ),
                new BasicNameValuePair( "quantity", String.valueOf( quantity ) ),
                new BasicNameValuePair( "address", address ),
                new BasicNameValuePair( "paymentId", paymentId )
            ),
            ObjectNode.class
        );
        return node.get( "uuid" ).textValue();

    }

    /**
     * Utility method that sends a request to the Bittrex's API, handling the
     * authentication through the API key and API secret possibly given when
     * instantiating the client itself.
     *
     * @param signed     Used to tell if the operation must be signed using the
     *                   {@link #apiKey} and {@link #apiSecret} or not.
     * @param operation  The Bittrex's API operation that we would like to call.
     * @param parameters The parameters which the operation takes in.
     *
     * @return A serialized representation of Bittrex's response JSON response.
     */
    private String makeRequest(
        boolean signed,
        String operation,
        NameValuePair ...parameters ) throws ApiException, URISyntaxException, IOException, AuthenticationException {

        if( signed && ( StringUtils.isEmpty( apiKey ) || StringUtils.isEmpty( apiSecret ) ) ) {
            throw new AuthenticationException( operation );
        }

        URIBuilder apiEndpointUriBuilder = new URIBuilder()
            .setScheme( API_SCHEME )
            .setHost( API_HOST )
            .setPath( API_PATH_ROOT + operation );

        for( NameValuePair parameter : parameters ) {

            if( StringUtils.isEmpty( parameter.getValue() ) ) {
                continue;
            }
            apiEndpointUriBuilder.addParameter(
                parameter.getName(),
                parameter.getValue()
            );

        }

        String signature = null;
        if( signed ) {

            apiEndpointUriBuilder.addParameter( API_PARAM_API_KEY, apiKey );
            apiEndpointUriBuilder.addParameter(
                API_PARAM_TIMESTAMP,
                String.valueOf( new Date().getTime() )
            );

            signature = new HmacUtils( HmacAlgorithms.HMAC_SHA_512, apiSecret ).hmacHex(
                apiEndpointUriBuilder.build().toString()
            );

        }

        HttpGet httpGet = new HttpGet( apiEndpointUriBuilder.build().toString() );
        if( signed ) {
            httpGet.addHeader( API_PARAM_API_SIGN, signature );
        }

        HttpResponse rawResponse = HttpClientBuilder
            .create()
            .setServiceUnavailableRetryStrategy( new BittrexRetryHandler() )
            .build()
            .execute( httpGet );
        BittrexResponse parsedResponse = MAPPER.readValue(
            rawResponse.getEntity().getContent(),
            BittrexResponse.class
        );
        if( parsedResponse.isSuccess() ) {
            return parsedResponse.getResult();
        }
        throw new ApiException( parsedResponse );

    }

}