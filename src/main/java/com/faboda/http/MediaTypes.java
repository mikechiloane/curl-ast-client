package com.faboda.http;

public enum MediaTypes {
    APPLICATION_JSON("application/json"),
    APPLICATION_X_WWW_FORM_URLENCODED("application/x-www-form-urlencoded");
    MediaTypes(String mediaType) {
        this.mediaType = mediaType;
    }

    private final String mediaType;

    public String getMediaTypeValue() {
        return mediaType;
    }
}
