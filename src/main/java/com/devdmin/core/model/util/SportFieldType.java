package com.devdmin.core.model.util;

public enum SportFieldType {
    VOLLEYBALL("Volleyball"),
    FOOTBALL("Football"),
    BASKETBALL("Basketball");
    private String type;
    public String type(){
        return type;
    }
    private SportFieldType(String type){
        this.type = type;
    }
}
