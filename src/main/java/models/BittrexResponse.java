package models;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.JsonNode;

public class BittrexResponse {

    private boolean success;
    private String message;
    private Object result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess( boolean success ) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }

    @JsonRawValue()
    public String getResult() {
        return result == null ? null : result.toString();
    }

    public void setResult( JsonNode result ) {
        this.result = result;
    }

}
