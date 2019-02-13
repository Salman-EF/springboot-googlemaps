/**
 * Build By SALMAN EL FADILI
 **/
package com.springboot.googlemaps.places;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class NearbySearchServices {

    private static final String API_KEY = "AIzaSyDpqUSmO_slGvAHMyB5y24AZFqcbCnwNVI";

    public static void nearByLocationAndDistance(LatLng location, int distance) throws Exception {

        // The list that will contain all results/places
        List<PlacesSearchResult> places=new ArrayList<>();
        // Send the first request
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();
        PlacesSearchResponse requestResults = PlacesApi.nearbySearchQuery(context, location)
                .radius(distance)
                .type(PlaceType.FOOD,PlaceType.RESTAURANT)
                .await();
        // Get the first page of request results because each Nearby Search or Text Search returns up to 20 establishment results
        places = Arrays.asList(requestResults.results);

        // Now we will check if there's another page of results by checking if the results nextPageToken parameter is null
        String nextPageToken = requestResults.nextPageToken;
        // In this loop we will retry the request with nextPageToken while it returned not null
        // And by that we'll get every page of results
        while(nextPageToken != null) {
            places.addAll(
                    Arrays.asList(
                            PlacesApi.nearbySearchQuery(context, location).pageToken(nextPageToken).await().results
                    )
            );
        }

		for (PlacesSearchResult place : places) {
            System.out.println(place.name);
		}
    }
}
