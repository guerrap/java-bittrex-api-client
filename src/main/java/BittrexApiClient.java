import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final ObjectMapper mapper = new ObjectMapper();

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

        return mapper.readValue(
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

        return mapper.readValue(
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

        return mapper.readValue(
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
     * @return The last 24 hour summary of all active exchanges.
     */
    public List< MarketSummary > getMarketSummaries() throws ApiException, URISyntaxException, IOException {

        return mapper.readValue(
            makeRequest( "public/getmarketsummaries" ),
            new TypeReference< List< MarketSummary > >() {}
        );

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
        BittrexResponse parsedResponse = mapper.readValue(
            rawResponse.getEntity().getContent(),
            BittrexResponse.class
        );
        if( parsedResponse.isSuccess() ) {
            return parsedResponse.getResult();
        }
        throw new ApiException( parsedResponse );

    }

}