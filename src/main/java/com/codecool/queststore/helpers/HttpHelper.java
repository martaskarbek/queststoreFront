package com.codecool.queststore.helpers;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class HttpHelper {

    public static final String SESSION_COOKIE_NAME = "SessionID";
    public static final int CREATED = 201;
    public static final int OK = 200;
    public static final int NOT_FOUND = 404;
    public static final int TEAPOT = 418;
    public static final int MOVED_PERMANENTLY = 301;

    public void sendResponse(HttpExchange httpExchange, String response, int status) throws IOException {
        httpExchange.sendResponseHeaders(status, response.length());
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(response.getBytes());
        outputStream.close();
    }

    public Map<String, String> getFormData(HttpExchange httpExchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        return parseFormData(br.readLine());
    }

    private Map<String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map<String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for(String pair : pairs){
            String[] keyValue = pair.split("=");
            // We have to decode the value because it's urlencoded. see: https://en.wikipedia.org/wiki/POST_(HTTP)#Use_for_submitting_web_forms
            String value = new URLDecoder().decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
