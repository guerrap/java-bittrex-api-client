package models;

public class BittrexResponse {

    private boolean success;
    private String message;
    private String result;

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

    public String getResult() {
        return result;
    }

    public void setResult( String result ) {
        this.result = result;
    }

}
