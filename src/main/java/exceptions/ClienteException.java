package exceptions;

import java.util.Map;

public class ClienteException extends RuntimeException{


    private Map<String, String> errors;

    public ClienteException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> toMap() {
        return Map.copyOf(errors);
    }
}