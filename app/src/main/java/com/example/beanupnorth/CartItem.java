package com.example.beanupnorth;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.security.PrivateKey;

public class CartItem implements Parcelable {

    private String name;
    private double price;
    private int quantity;


    public CartItem (String name, double price, int quantity){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName(){
        return name;
    }

    public double getPrice(){
        return  price;
    }

    public int getQuantity(){
        return quantity;
    }


    protected CartItem(Parcel in) {
        name = in.readString();
        price = in.readDouble();
        quantity = in.readInt();
    }

    public static final Creator<CartItem> CREATOR = new Creator<CartItem>() {
        @Override
        public CartItem createFromParcel(Parcel in) {
            return new CartItem(in);
        }

        @Override
        public CartItem[] newArray(int size) {
            return new CartItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(price);
        dest.writeInt(quantity);


    }
}
