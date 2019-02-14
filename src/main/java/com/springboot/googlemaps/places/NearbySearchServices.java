/**
 * Build By SALMAN EL FADILI
 **/
package com.springboot.googlemaps.places;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NearbySearchServices {

    private static final String API_KEY = "AIzaSyDpqUSmO_slGvAHMyB5y24AZFqcbCnwNVI";

    private GeoApiContext contextBuilder() {
        return new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
    }

    private static String nextPageString = null;
    private static LatLng location;

    public SearchResponseModel byLocationAndDistance(LatLng location, int distance) throws Exception {

        // Set the global location parameter
        this.location = location;
        // Send the first request
        PlacesSearchResponse requestResponse = PlacesApi.nearbySearchQuery(this.contextBuilder(), location)
                .radius(distance)
                .type(PlaceType.FOOD,PlaceType.RESTAURANT)
                .await();

        // Now we will check if there's another page of results by checking if the results nextPageToken parameter is null
        boolean nextPageExist = false;
        try {
            if(requestResponse.nextPageToken != null) {
                this.nextPageString = requestResponse.nextPageToken;
                nextPageExist = true;
            }
        } catch (NullPointerException ex) {
            System.out.println("No other page.");
        }

        // Return the first page of request results because each Nearby Search or Text Search returns up to 20 establishment results
        return new SearchResponseModel(
                Arrays.asList(requestResponse.results), nextPageExist
        );
    }


    public SearchResponseModel nextPageResults() throws Exception {
        PlacesSearchResponse pageResponse = PlacesApi.nearbySearchQuery(this.contextBuilder(), this.location)
                .pageToken(this.nextPageString)
                .await();

        boolean nextPageExist = false;
        try {
            if(pageResponse.nextPageToken != null) {
                this.nextPageString = pageResponse.nextPageToken;
                nextPageExist = true;
            }
        } catch (NullPointerException ex) {
            System.out.println("No other page.");
        }

        return new SearchResponseModel(
                Arrays.asList(pageResponse.results), nextPageExist
        );
    }
}
