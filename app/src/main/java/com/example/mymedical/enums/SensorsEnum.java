package com.example.mymedical.enums;

public enum SensorsEnum {

    FREESTYLE_LIBRE("FreestyleLibre"),
    DEXCOM("Dexcom");

    private String name;

    SensorsEnum(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
