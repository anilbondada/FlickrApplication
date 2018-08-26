package com.sampletest.anil.testapplication.utils;

/**
 * Created by H211060 on 8/21/2018.
 */

public abstract class Constants {

    public static final String FLICKR_API_KEY = "3e7cc266ae2b0e0d78e279ce8e361736";
    public static final String BASE_URL = "https://api.flickr.com/services/";
    public static final String METHOD_TYPE= "rest/";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = "3e7cc266ae2b0e0d78e279ce8e361736";
    public static final String API_FORMAT = "format";
    public static final String API_FORMAT_JSON = "json";
    public static final String API_PARMAMS_NO_JSON_CALLBACK = "nojsoncallback";
    public static final String API_PARMAMS_SAFE_SEARCH = "safe_search";
    public static final String API_PARMAMS_SEARCH_TEXT = "text";
    public static final String API_PARMAMS_METHOD_URL = "method";
    public static final String PAGES = "page";

    /*Try this call below:
    https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&
    format=json&nojsoncallback=1&safe_search=1&text=kittens*/


    //db operations
    public static final String DB_OPERATION_INSERT = "insert";
    public static final String DB_OPERATION_READ = "read";


}
