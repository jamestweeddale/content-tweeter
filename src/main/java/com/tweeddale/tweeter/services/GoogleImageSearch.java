package com.tweeddale.tweeter.services;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.tweeddale.tweeter.util.ConfigWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.*;

/**
 * Created by James on 7/3/2015.
 *
 * This implementation of ImageSearchService queries Google Custom Search API via an HTTP request.
 */
public class GoogleImageSearch implements ImageSearchService {
    private static final Logger logger = LogManager.getLogger(GoogleImageSearch.class);

    private String googleApiKey = null;
    private String googleApiCx = null;
    private final String searchUrlStrTemplate = "https://www.googleapis.com/customsearch/v1?key=%s&cx=%s&searchType=image&q=";

    public GoogleImageSearch() {
        this(null, null);
    }

    public GoogleImageSearch(String googleApiKey, String googleApiCx) {
        this.googleApiKey = googleApiKey;
        this.googleApiCx = googleApiCx;

        if (googleApiKey == null) {
            this.googleApiKey = ConfigWrapper.getConfig().getString("services.google-custom-search.key");
        }

        if (googleApiCx == null) {
            this.googleApiCx = ConfigWrapper.getConfig().getString("services.google-custom-search.cx");
        }
    }


    /**
     * Performs a request to Google Image search using the passed-in search string. Results are returned from the service
     * in a JSON object which is parsed to store only the image URLS in a List.
     *
     * @param searchStr The search string used to query for a related image
     * @return a List of URL strings, each pointing to a related image result from the Google image search
     */
    public List<String> search(String searchStr) {
        if (this.googleApiKey == null || this.googleApiCx == null) {
            throw new IllegalStateException("API Key and CX must be set before search can be performed.");
        }

        List<String> imageUrls = new ArrayList<String>();
        StringBuilder builder = new StringBuilder();

        try {
            String completeSearchUrl = searchStr.format(searchUrlStrTemplate, googleApiKey, googleApiCx);
            String queryTerms = URLEncoder.encode(searchStr.trim(), "UTF-8");
            URL url = new URL(completeSearchUrl + queryTerms);

            URLConnection connection = url.openConnection();

            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            imageUrls = parseOutResultImageUrls(builder.toString());

        } catch (Exception e) {
            logger.warn("Exception occurred while obtaining image search results");
            e.printStackTrace();
        }


        return imageUrls;
    }

    /**
     * @param json the JSON object returned by the Google image search performed by the search() method.
     * @return List of strings, each one a URL from each search result in the JSON object that was passed-in
     */
    private List<String> parseOutResultImageUrls(String json) {

        ArrayList<String> resultsList = new ArrayList<String>();

        try {
            JSONArray jsonResultsArr = new JSONObject(json).getJSONArray("items");

            for (int i = 0; i < jsonResultsArr.length(); i++) {
                JSONObject result = jsonResultsArr.getJSONObject(i);
                String imageUrl = result.getString("link");
                resultsList.add(imageUrl);
            }
        }catch(Exception e){
            logger.warn("Exception occurred while parsing image result JSON");
            e.printStackTrace();
        }

        return resultsList;
    }

}
