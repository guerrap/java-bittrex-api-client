package errors;

import models.BittrexResponse;

public class ApiException extends Exception {

    public ApiException( BittrexResponse response ) {
        super( response.getMessage() );
    }

}
