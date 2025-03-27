package com.example.beanupnorth;

public class OrderItem {

    private String name, imgURl;
    private int quantity;

    private Double price;

    public OrderItem(){

    }

    public OrderItem(String name, String imgURl, Double price, int quantity){
        this.name = name;
        this.imgURl = imgURl;
        this.price = price;
        this.quantity = quantity;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImgURl(String imgURl){
        this.imgURl = imgURl;
    }

    public void setPrice(Double price){
        this.price = price;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public String getImgURl(){
        return  imgURl;
    }

    public Double getPrice(){
        return price;
    }

    public int getQuantity(){
        return quantity;
    }
    //override toString() to return meaningful data.
    @Override
    public String toString(){
        return " OrderItem{" + "ProductName='" + name + '\'' + ", Quantity=" + quantity + ", Price=" + price + ",image_url="+imgURl+"}";

    }

}
