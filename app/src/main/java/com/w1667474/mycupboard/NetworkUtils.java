package com.w1667474.mycupboard;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private static final String LOG_TAG =
            NetworkUtils.class.getSimpleName();
//sets the string as the url and id that is needed to access
    private static final String URL_BASE = "https://www.food2fork.com/api/search?key=005ec08440110ef2c33a6527e60e3bdb";
    private static final String QUERY = "q";

    static String getRecipeInfo(String queryString){
        //creates new connection, buffered reader for reading the repsonse
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString = null;

        try{
            //parses the above strings along with the query string that is passed to make the url query
            Uri uriBuilder = Uri.parse(URL_BASE).buildUpon()
                    .appendQueryParameter(QUERY, queryString).build();
            URL request = new URL(uriBuilder.toString());
            urlConnection = (HttpURLConnection) request.openConnection();
            //sets the type of method ie get
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.i("url REQUEST IS", uriBuilder.toString());

            InputStream iS = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(iS));
            StringBuilder build = new StringBuilder();
//while reader has a line to read adds the returns json to a string builder with\n for a new line
            String line;
            while ((line = reader.readLine())!=null){
                build.append(line);
                build.append("\n");
            }
            if (build.length() == 0){
                return null;
            }
            JSONString = build.toString();
        }catch (IOException e){
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        Log.d(LOG_TAG, JSONString);
        return JSONString;
    }
}
