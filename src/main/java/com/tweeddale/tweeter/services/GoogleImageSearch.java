package com.tweeddale.tweeter.services;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

/**
 * Created by James on 7/3/2015.
 *
 * This implementation of ImageSearchService queries Google Image Search via an HTTP request.
 */
public class GoogleImageSearch implements ImageSearchService {

    private final String searchUrlStr = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

    /**
     * Performs a request to Google Image search using the passed-in search string. Results are returned from the service
     * in a JSON object which is parsed to store only the image URLS in a List.
     *
     * @param searchStr   The search string used to query for a related image
     * @return a List of URL strings, each pointing to a related image result from the Googkle image search
     */
    public List<String> search(String searchStr) {
        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(this.searchUrlStr + URLEncoder.encode(searchStr.trim(), "UTF-8"));

            URLConnection connection = url.openConnection();

            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return parseOutResultImageUrls(builder.toString());
    }

    /**
     *
     * @param json the JSON object returned by the Google image search performed by the search() method.
     * @return List of strings, each one a URL from each search result in the JSON object that was passed-in
     */
    private List<String> parseOutResultImageUrls(String json){

        ArrayList resultsList = new ArrayList();

        JSONArray jsonResultsArr = new JSONObject(json).getJSONObject("responseData").getJSONArray("results");

        for(int i =0; i < jsonResultsArr.length(); i++) {
            JSONObject result = jsonResultsArr.getJSONObject(i);
            String imageUrl = result.getString("url");
            resultsList.add(imageUrl);
        }

        return resultsList;
    }

}
