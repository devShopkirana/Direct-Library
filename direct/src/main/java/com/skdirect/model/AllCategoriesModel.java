package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class AllCategoriesModel {

    @SerializedName("Id")
    private int Id;

    @SerializedName("CategoryName")
    private String CategoryName;

    @SerializedName("ImagePath")
    private String ImagePath;

    @SerializedName("Name")
    private String Name;


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

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
