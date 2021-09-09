package com.example.teleworldonlineshop.Model;

import java.io.Serializable;

public class Device implements Serializable {
    public int DeviceId;
    public String DeviceName;
    public int Price;
    public String PictureURL;
    public String Description;
    public int CategoryId;

    public Device(int deviceId, String deviceName, int price, String pictureURL, String description, int catId) {
        DeviceId = deviceId;
        DeviceName = deviceName;
        Price = price;
        PictureURL = pictureURL;
        Description = description;
        CategoryId = catId;
    }

    public int getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(int deviceId) {
        DeviceId = deviceId;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(String pictureURL) {
        PictureURL = pictureURL;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }
}
