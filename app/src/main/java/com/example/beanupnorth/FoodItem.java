package com.example.beanupnorth;

public class FoodItem {
    private int imageResId;
    private String name;
    private String description;

    public FoodItem(int imageResId, String name, String description ){
        this.imageResId = imageResId;
        this.name = name;
        this.description = description;
    }

    public int getImageResId(){
    return imageResId;
    }

    public String getName(){
    return name;
    }

    public String getDescription(){
    return description;
    }
}
