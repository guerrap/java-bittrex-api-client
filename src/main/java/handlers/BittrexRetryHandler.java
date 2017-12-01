package handlers;

import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class BittrexRetryHandler implements HttpRequestRetryHandler {

    // TODO: implement retry strategy.
    @Override
    public boolean retryRequest( IOException exception, int executionCount, HttpContext context ) {
        return false;
    }

}
