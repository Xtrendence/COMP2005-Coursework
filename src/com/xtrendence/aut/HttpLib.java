package com.xtrendence.aut;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpLib implements IHttpLib {
    public Response call(String url) throws Exception {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        int code = connection.getResponseCode();

        if(code != 200) {
            return new Response(code, null);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String data = reader.readLine();
        reader.close();

        return new Response(code, data);
    }
}
