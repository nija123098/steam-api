package com.github.goive.steamapi.exceptions;

public class SteamApiException extends Exception {

    private static final long serialVersionUID = -3863510151151588226L;

    public SteamApiException(String message) {
        super(message);
    }

    public SteamApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public SteamApiException(Throwable cause) {
        super(cause);
    }

}
