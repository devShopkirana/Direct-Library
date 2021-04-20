package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

public class AddReviewModel {

    @SerializedName("Id")
    private int Id;
    @SerializedName("Comment")
    private String Comment;
    @SerializedName("Rating")
    private int Rating;

    public AddReviewModel(int id, String comment, int rating) {
        Id = id;
        Comment = comment;
        Rating = rating;
    }
}
