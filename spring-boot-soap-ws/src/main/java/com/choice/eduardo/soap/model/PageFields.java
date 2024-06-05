package com.choice.eduardo.soap.model;

public enum PageFields {
    PAGES("pages"),
    ELEMENTS("elements"),
    CURRENT_PAGE("current-page");
    private String key;
    PageFields(String key){
        this.key = key;
    }
    public String getKey() {return this.key;}
}
