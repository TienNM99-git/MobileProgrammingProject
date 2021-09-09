package com.example.teleworldonlineshop.Model;

public class Cart {
    public int deviceId;
    public String deviceName;
    public long totalPrice;
    public String pictureUrl;

    public Cart(int deviceId, String deviceName, long totalPrice, String pictureUrl, int quantity) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.totalPrice = totalPrice;
        this.pictureUrl = pictureUrl;
        this.quantity = quantity;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int quantity;

}
