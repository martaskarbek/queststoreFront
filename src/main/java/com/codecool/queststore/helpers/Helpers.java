package com.codecool.queststore.helpers;

public class Helpers {
    private final HttpHelper httpHelper;
    private final CookieHelper cookieHelper;

    public Helpers(HttpHelper httpHelper, CookieHelper cookieHelper) {
        this.httpHelper = httpHelper;
        this.cookieHelper = cookieHelper;
    }

    public HttpHelper getHttpHelper() {
        return httpHelper;
    }

    public CookieHelper getCookieHelper() {
        return cookieHelper;
    }
}
