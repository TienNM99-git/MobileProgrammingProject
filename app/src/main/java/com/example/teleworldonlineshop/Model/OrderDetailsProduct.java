package com.example.teleworldonlineshop.Model;

public class OrderDetailsProduct {
    public OrderDetailsProduct(int deviceId, String deviceName, long devicePrice, String pictureUrl, int quantity) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.devicePrice = devicePrice;
        this.pictureUrl = pictureUrl;
        this.quantity = quantity;
    }

    public int deviceId;
    public String deviceName;

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

    public long getDevicePrice() {
        return devicePrice;
    }

    public void setDevicePrice(long devicePrice) {
        this.devicePrice = devicePrice;
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

    public long devicePrice;
    public String pictureUrl;
    public int quantity;

}
