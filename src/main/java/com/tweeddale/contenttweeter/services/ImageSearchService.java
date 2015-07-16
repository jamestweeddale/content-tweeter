package com.tweeddale.contenttweeter.services;
import java.util.List;
/**
 * Created by James on 7/4/2015.
 *
 * An image search service returns a list of image URLs
 */
public interface ImageSearchService {

    public List<String> search(String searchTerms);
}
