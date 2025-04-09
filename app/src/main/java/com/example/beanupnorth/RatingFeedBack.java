package com.example.beanupnorth;

public class RatingFeedBack {

    public float rating;
    public String email, comment;

    public RatingFeedBack(){

    }
    public RatingFeedBack(float rating, String email, String comment){
        this.rating = rating;
        this.email = email;
        this.comment = comment;
    }
}
