package com.example.auctionapp;

public class Model {
    private static String imageUrl;
    public Model()
    {

    }

    public Model(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public static String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
