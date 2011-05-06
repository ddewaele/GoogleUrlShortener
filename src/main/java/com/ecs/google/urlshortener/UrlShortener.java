package com.ecs.google.urlshortener;

import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.GoogleTransport;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonHttpContent;
import com.google.api.client.json.JsonHttpParser;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;

public class UrlShortener {

	public static final String GOOGLE_SHORTENER_URL = "https://www.googleapis.com/urlshortener/v1/url?key=YOUR_API_KEY_HERE";
	 
	public static void main(String[] args) throws Exception {

		// setup the anonymous Google HTTP transport.
        HttpTransport transport = GoogleTransport.create();

        // configure the headers on the transport.
        GoogleHeaders defaultHeaders = new GoogleHeaders();
        transport.defaultHeaders = defaultHeaders;
        transport.defaultHeaders.put("Content-Type", "application/json");
        
        // configure the JSON parser on the transport.
        transport.addParser(new JsonHttpParser());

        // build the HTTP GET request and URL
        HttpRequest request = transport.buildPostRequest();
        request.setUrl(GOOGLE_SHORTENER_URL);
        
        // Prepare the JSON data structure.
        GenericData data = new GenericData();
        data.put("longUrl", "http://latifymobile.com/latify-features/google-fusion-tables/");
        JsonHttpContent content = new JsonHttpContent();
        content.data = data;
        
        // Set the JSON content on the request.
        request.content = content;

        // Execute the request, and parse the response using our Result class.
        HttpResponse response = request.execute();
        UrlShortenerResult result = response.parseAs(UrlShortenerResult.class);
        
        // Print the result.
        System.out.println(result.shortUrl);
        
        /*
         * The actual json response looks like this. 
         * 
         *         {
         *         	 "kind": "urlshortener#url",
         *         	 "id": "http://goo.gl/vl02B",
         *         	 "longUrl": "http://latifymobile.com/"
         *         	}
         *         
         * It can be retrieved using the following call 
         * String resultAsString = response.parseAsString();
         * 
         *         
        */

    }

	
	/**
	 * JSON UrlShortenerResult object, capturing the id as the shortUrl
	 */
    public static class UrlShortenerResult extends GenericJson {        
        @Key("id")
        public String shortUrl;
    }

}
