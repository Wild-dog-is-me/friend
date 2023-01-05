package org.dog.server.exception;

public class ServiceException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    private String code;

    public String getCode() {
        return code;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

}
