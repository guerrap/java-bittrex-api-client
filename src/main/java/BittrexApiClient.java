import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import enums.OrderBookType;
import errors.ApiException;
import handlers.BittrexRetryHandler;
import models.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
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
    public List< Market > getMarkets() throws ApiException, URISyntaxException, IOException {

        return MAPPER.readValue(
            makeRequest( "public/getmarkets" ),
            new TypeReference< List< Market > >() {}
        );

    }

    /**
     * Interface to the "public/getcurrencies" Bittrex's API operation.
     *
     * @return All the supported currencies at Bittrex along with
     *         other meta data.
     */
    public List< Currency > getCurrencies() throws ApiException, URISyntaxException, IOException {

        return MAPPER.readValue(
            makeRequest( "public/getcurrencies" ),
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
    public Ticker getTicker( String market ) throws ApiException, URISyntaxException, IOException {

        return MAPPER.readValue(
            makeRequest(
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
    public List< MarketSummary > getMarketSummaries() throws ApiException, URISyntaxException, IOException {

        return MAPPER.readValue(
            makeRequest( "public/getmarketsummaries" ),
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
    public MarketSummary getMarketSummary( String market ) throws ApiException, URISyntaxException, IOException {

        List< MarketSummary > marketSummaries = MAPPER.readValue(
            makeRequest(
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
        OrderBookType type ) throws ApiException, URISyntaxException, IOException {

        return MAPPER.readValue(
            makeRequest(
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
        String market ) throws ApiException, URISyntaxException, IOException {

        return MAPPER.readValue(
            makeRequest(
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
        double rate ) throws ApiException, URISyntaxException, IOException {

        ObjectNode node = MAPPER.readValue(
            makeRequest(
                "/market/buylimit",
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
        double rate ) throws ApiException, URISyntaxException, IOException {

        ObjectNode node = MAPPER.readValue(
            makeRequest(
                "/market/selllimit",
                new BasicNameValuePair( "market", market ),
                new BasicNameValuePair( "quantity", String.valueOf( quantity ) ),
                new BasicNameValuePair( "rate", String.valueOf( rate ) )
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
     * @param operation  The Bittrex's API operation that we would like to call.
     * @param parameters The parameters which the operation takes in.
     *
     * @return A serialized representation of Bittrex's response JSON response.
     */
    private String makeRequest(
        String operation,
        NameValuePair ...parameters ) throws ApiException, URISyntaxException, IOException {

        URIBuilder apiEndpointUriBuilder = new URIBuilder()
            .setScheme( "https" )
            .setHost( "www.bittrex.com" )
            .setPath( "/api/v1.1/" + operation )
            .addParameter( "nonce", String.valueOf( new Date().getTime() ) );

        for( NameValuePair parameter : parameters ) {

            if( StringUtils.isEmpty( parameter.getValue() ) ) {
                continue;
            }
            apiEndpointUriBuilder.addParameter(
                parameter.getName(),
                parameter.getValue()
            );

        }

        if( StringUtils.isNotEmpty( apiKey ) ) {
            apiEndpointUriBuilder.addParameter( "apikey", apiKey );
        }

        HttpGet httpGet = new HttpGet( apiEndpointUriBuilder.build() );
        if( StringUtils.isNotEmpty( apiSecret ) ) {

            httpGet.addHeader(
                "apisign",
                DigestUtils.sha512Hex( apiSecret + apiEndpointUriBuilder.build().toString() )
            );

        }

        HttpClient httpClient = HttpClientBuilder
            .create()
            .setRetryHandler( new BittrexRetryHandler() )
            .build();

        HttpResponse rawResponse = httpClient.execute( httpGet );
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