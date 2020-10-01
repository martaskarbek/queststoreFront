package com.codecool.queststore.helpers;

import com.codecool.queststore.helpers.CookieHelper;
import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CookieHelperTest {

    @Test
    void getSessionIdFromCookie() {
        HttpCookie cookie = new HttpCookie("Cookie", "this\"is\"cookie" );
        CookieHelper cookieHelper = new CookieHelper();
        String value = cookieHelper.getSessionIdFromCookie(cookie);
        Assertions.assertEquals(value, "thisiscookie");
    }

    @Test
    void parseCookies() {
        List<HttpCookie> cookies;
        CookieHelper cookieHelper = new CookieHelper();
        String cookieString = "this=iscookie";

        cookies = cookieHelper.parseCookies(cookieString);

        Assertions.assertAll(
            () -> Assertions.assertEquals(cookies.get(0).getName(), "this"),
            () -> Assertions.assertEquals(cookies.get(0).getValue(), "iscookie")
        );
    }

    @Test
    void findCookieByName() {
        CookieHelper cookieHelper = new CookieHelper();
        List<HttpCookie> cookies = new ArrayList<>();

        cookies.add(new HttpCookie("Cookie", "example"));
        Optional<HttpCookie> cookie = cookieHelper.findCookieByName("Cookie", cookies);
        Optional<HttpCookie> noCookie = cookieHelper.findCookieByName("noCookie", cookies);

        Assertions.assertAll(
                ()-> Assertions.assertEquals(cookie.isPresent(), true ),
                ()-> Assertions.assertEquals(noCookie, Optional.empty())
        );
    }

//    @Test
//    void getSessionIdCookie() {
//        CookieHelper cookieHelper = new CookieHelper();
//        HttpExchange exchange = Mockito.mock(HttpExchange.class);
//        Mockito.when(cookieHelper.getSessionIdFromCookie(new HttpCookie("cookie", "cookie")));
//
//        cookieHelper.getSessionIdCookie(exchange);
//
//        Mockito.verify(cookieHelper.);
//
//
//    }

    @Test
    void findSessionFromCookie() {
    }
}