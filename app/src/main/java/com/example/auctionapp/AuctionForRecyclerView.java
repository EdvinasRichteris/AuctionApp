package com.example.auctionapp;

public class AuctionForRecyclerView {
    String itemdesc;
    String itemname;
    String imageUrl;
    String address;
    String city;
    String country;
    String currentbid;
    String lastbidder;
    String startingprice;
    String realName;


    public AuctionForRecyclerView() {

    }
    public AuctionForRecyclerView(String itemname, String imageUrl, String itemdesc,
                                  String address, String city, String country,
                                  String currentbid, String lastbidder, String startingprice, String realName) {
        this.itemname = itemname;
        this.imageUrl = imageUrl;
        this.itemdesc = itemdesc;
        this.city = city;
        this.country = country;
        this.address = address;
        this.startingprice = startingprice;
        this.realName = realName;
        this.currentbid = currentbid;
        this.lastbidder = lastbidder;
    }

    public void setItemdesc(String itemdesc) {
        this.itemdesc = itemdesc;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCurrentbid(String currentbid) {
        this.currentbid = currentbid;
    }

    public void setLastbidder(String lastbidder) {
        this.lastbidder = lastbidder;
    }

    public void setStartingprice(String startingprice) {
        this.startingprice = startingprice;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRealName() {
        return realName;
    }

    public String getItemdesc() {
        return itemdesc;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getCurrentbid() {
        return currentbid;
    }

    public String getLastbidder() {
        return lastbidder;
    }

    public String getStartingprice() {
        return startingprice;
    }
}
