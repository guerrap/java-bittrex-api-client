import com.fasterxml.jackson.databind.ObjectMapper;
import errors.ApiException;
import handlers.BittrexRetryHandler;
import models.BittrexResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.util.Date;

/**
 * Represents a single Bittrex API client.
 */
public class BittrexApiClient {

    private static final ObjectMapper mapper = new ObjectMapper();

    private String apiKey;
    private String apiSecret;

    /**
     * Initializes a new Bittrex API client.
     *
     * @param apiKey    The personal account API key.
     * @param apiSecret The personal account API secret.
     */
    public BittrexApiClient( String apiKey, String apiSecret ) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
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
    private Object makeRequest( String operation, NameValuePair ...parameters ) throws Exception {


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
        BittrexResponse parsedResponse = new ObjectMapper().readValue(
            rawResponse.getEntity().getContent(),
            BittrexResponse.class
        );
        if( parsedResponse.isSuccess() ) {
            return parsedResponse.getResult();
        }
        throw new ApiException( parsedResponse );

    }

}