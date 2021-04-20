package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class UpdateProfilePostModel {

    @SerializedName("Name")
    private String Name;

    @SerializedName("emailid")
    private String emailid;

    public UpdateProfilePostModel(String name, String emailid) {
        this.Name = name;
        this.emailid = emailid;
    }

}
