package com.codecool.queststore.helpers;

public class Helpers {
    private final HttpHelper httpHelper;
    private final CookieHelper cookieHelper;
    private final HandlerHelper handlerHelper;

    public Helpers(HttpHelper httpHelper, CookieHelper cookieHelper, HandlerHelper handlerHelper) {
        this.httpHelper = httpHelper;
        this.cookieHelper = cookieHelper;
        this.handlerHelper = handlerHelper;
    }

    public HttpHelper getHttpHelper() {
        return httpHelper;
    }

    public CookieHelper getCookieHelper() {
        return cookieHelper;
    }

    public HandlerHelper getHandlerHelper() {
        return handlerHelper;
    }
}
