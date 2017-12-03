package handlers;

import org.apache.http.HttpResponse;
import org.apache.http.client.ServiceUnavailableRetryStrategy;
import org.apache.http.protocol.HttpContext;

public class BittrexRetryHandler implements ServiceUnavailableRetryStrategy {

    private static final short MAX_ATTEMPTS = 10;
    private static final short RETRY_INTERVAL = 2500;
    private static final int[] IDEMPOTENT_HTTP_STATUS_CODES = new int[] {
        524,
        502,
        504,
        522,
        503,
        1016
    };

    @Override
    public boolean retryRequest( HttpResponse response, int executionCount, HttpContext context ) {

        int statusCode = response.getStatusLine().getStatusCode();
        for( int code : IDEMPOTENT_HTTP_STATUS_CODES ) {

            if( statusCode == code && executionCount <= MAX_ATTEMPTS ) {
                return true;
            }

        }
        return false;

    }

    @Override
    public long getRetryInterval() {
        return RETRY_INTERVAL;
    }

}
