package errors;

public class AuthenticationException extends Exception {

    public AuthenticationException( String apiOperation ) {
        super( "Operation \"" + apiOperation + "\" requires an API key and API secret in order to be called" );
    }

}
