package com.example.beanupnorth;

public class FoodItem {
    private int imageResId, price;
    private String name, type, imgUrl ;
    private String description;

    public FoodItem( String name, String description, String type, int price, String imgUrl ){

        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.imgUrl= imgUrl;
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

    public int getPrice(){
        return price;
    }

    public String getType(){
        return type;
    }

    public String getImgUrl(){
        return imgUrl;
    }

}
