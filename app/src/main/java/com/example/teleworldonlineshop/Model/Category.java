package com.example.teleworldonlineshop.Model;

public class Category {
    public int Id;
    public String CategoryName;
    public String PictureURL;

    public Category(int id, String categoryName, String pictureURL) {
        Id = id;
        CategoryName = categoryName;
        PictureURL = pictureURL;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getPictureURL() {
        return PictureURL;
    }

    public void setPictureURL(String pictureURL) {
        PictureURL = pictureURL;
    }
}
