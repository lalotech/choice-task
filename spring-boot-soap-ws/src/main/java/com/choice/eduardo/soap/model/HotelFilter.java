package com.choice.eduardo.soap.model;

public enum HotelFilter {
    ADDRESS("address"),
    RANTING("rating"),
    NAME("name"),
    PAGE("page");
    private String key;
    HotelFilter(String key){
        this.key = key;
    }
    public String getKey() {return this.key;}
}
