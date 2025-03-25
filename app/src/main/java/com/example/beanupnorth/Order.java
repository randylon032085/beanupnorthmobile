package com.example.beanupnorth;

import java.util.List;

public class Order {

    String orderId;
    List<CartItem> item;
    private Double subTotal;
    private Double discount;
    private Double tax;
    private Double total;
    private String status;
    private String customerId;
    private String date;

    public Order(){

    }

    public Order(String orderId, List<CartItem> item, Double subTotal, Double discount, Double tax, Double total, String status, String customerId, String date ){
        this.orderId = orderId;
        this.subTotal = subTotal;
        this.discount = discount;
        this.tax = tax;
        this.total = total;
        this.status = status;
        this.customerId = customerId;
        this.date = date;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<CartItem> getItem(){
        return item;
    }

    public Double getSubTotal(){
        return subTotal;
    }

    public Double getDiscount(){
        return discount;
    }

    public Double getTax(){
        return  tax;
    }

    public Double getTotal(){
        return total;
    }

    public String getStatus(){
        return  status;
    }

    public String getCustomerId(){
        return customerId;
    }

    public String getDate(){
        return date;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public void setItem(List<CartItem> item){
        this.item = item;
    }

    public void setSubTotal(Double subTotal){

        this.subTotal = subTotal;
    }

    public void setDiscount(Double discount){
        this.discount = discount;
    }

    public void setTax(Double tax){
       this.tax = tax;
    }

    public void setTotal(Double total){
        this.total = total;
    }

    public void setStatus(String status){
        this.status = status;
    }
    public void setCustomerId(String customerId){
        this.customerId = customerId;
    }

    public void setDate(String date){
        this.date = date;
    }
}


