package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImageListModel implements Serializable {

    @SerializedName("IsActive")
    private boolean IsActive;
    @SerializedName("IsDelete")
    private boolean IsDelete;
    @SerializedName("Id")
    private int Id;
    @SerializedName("IsDefaultShow")
    private boolean IsDefaultShow;
    @SerializedName("ImagePath")
    private String ImagePath;

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isDefaultShow() {
        return IsDefaultShow;
    }

    public void setDefaultShow(boolean defaultShow) {
        IsDefaultShow = defaultShow;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }
}
