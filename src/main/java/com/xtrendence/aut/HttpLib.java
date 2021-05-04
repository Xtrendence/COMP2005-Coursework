package com.xtrendence.aut;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpLib implements IHttpLib {
    /* Make an API call to a given URL. (In this case, a GET request).
    *  @param url The URL to send a GET request to.
    *  @return Response the response code and data.
    */
    public Response call(String url) throws Exception {
        URL urlObject = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) urlObject.openConnection();
        connection.setRequestMethod("GET");

        int code = connection.getResponseCode();

        if(code != 200) {
            return new Response(code, null);
        }

        StringBuilder builder = new StringBuilder();

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String line;
        while((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        return new Response(code, builder.toString());
    }
}
