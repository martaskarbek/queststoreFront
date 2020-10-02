package com.codecool.queststore.helpers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class CookieHelperTest {

    CookieHelper cookieHelper = new CookieHelper();

    @Test
    void getSessionIdFromCookie() {
        //Arrange
        HttpCookie cookie = new HttpCookie("Cookie", "this\"is\"cookie");
        //Act
        String value = cookieHelper.getSessionIdFromCookie(cookie);
        //Assert
        Assertions.assertEquals(value, "thisiscookie");
    }

    @Test
    void parseCookies() {
        //Arrange
        List<HttpCookie> cookies;
        String cookieString = "this=iscookie";
        //Act
        cookies = cookieHelper.parseCookies(cookieString);
        //Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(cookies.get(0).getName(), "this"),
                () -> Assertions.assertEquals(cookies.get(0).getValue(), "iscookie")
        );
    }

    @Test
    void findCookieByName() {
        //Arrange
        List<HttpCookie> cookies = new ArrayList<>();
        //Act
        cookies.add(new HttpCookie("Cookie", "example"));
        Optional<HttpCookie> cookie = cookieHelper.findCookieByName("Cookie", cookies);
        Optional<HttpCookie> noCookie = cookieHelper.findCookieByName("noCookie", cookies);
        //Assert
        Assertions.assertAll(
                () -> Assertions.assertEquals(cookie.isPresent(), true),
                () -> Assertions.assertEquals(noCookie, Optional.empty())
        );
    }


    @Test
    void findSessionFromCookie() {
        //Arrange
        HttpCookie cookie = new HttpCookie("SessionID", "this=is=cookie");
        List<HttpCookie> cookies = new ArrayList<>();
        //Act
        cookies.add(cookie);
        String sessionFromCookie = cookieHelper.findSessionFromCookie(cookies);
        //Assert
        Assertions.assertEquals(sessionFromCookie, "this=is=cookie");
    }
}