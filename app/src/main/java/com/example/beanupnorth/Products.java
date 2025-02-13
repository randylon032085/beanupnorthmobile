package com.example.beanupnorth;

public class Products {
    private String productName, productType;

    public Products(){

    }
    public Products(String productName, String productType){
       this.productName = productName;
       this.productType = productType;
    }
    public String getProductName(){
        return productName;
    }

    public String getProductType(){
        return productType;
    }
}
