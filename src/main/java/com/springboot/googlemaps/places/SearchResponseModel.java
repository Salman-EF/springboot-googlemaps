/**
 * Build By SALMAN EL FADILI
 **/
package com.springboot.googlemaps.places;

import com.google.maps.model.PlacesSearchResult;

import java.util.List;

public class SearchResponseModel {

    private List<PlacesSearchResult> places;
    private boolean nextPageExists;

    public SearchResponseModel(List<PlacesSearchResult> places, boolean nextPage) {
        this.places = places;
        this.nextPageExists = nextPage;
    }

    public List<PlacesSearchResult> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlacesSearchResult> places) {
        this.places = places;
    }

    public boolean isNextPageExists() {
        return nextPageExists;
    }

    public void setNextPage(boolean nextPageExists) {
        this.nextPageExists = nextPageExists;
    }
}
