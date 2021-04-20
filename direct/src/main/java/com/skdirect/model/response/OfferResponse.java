package com.skdirect.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class OfferResponse {
    @SerializedName("ResultItem")
    private ArrayList<Coupon> ResultItem;
    @SerializedName("IsSuccess")
    private boolean isSuccess;

    public ArrayList<Coupon> getResultItem() {
        return ResultItem;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public static class Coupon implements Parcelable {
        @SerializedName("Id")
        private int id;
        @SerializedName("CouponCode")
        private String couponCode;
        @SerializedName("CouponName")
        private String couponName;
        @SerializedName("MaxAmount")
        private double maxAmount;
        @SerializedName("Amount")
        private double amount;
        @SerializedName("AmountPercentage")
        private double amountPercentage;
        @SerializedName("MinOrderValue")
        private int minOrderValue;
        @SerializedName("NoOfTime")
        private int noOfTime;

        protected Coupon(Parcel in) {
            id = in.readInt();
            couponCode = in.readString();
            couponName = in.readString();
            maxAmount = in.readDouble();
            amount = in.readDouble();
            amountPercentage = in.readDouble();
            minOrderValue = in.readInt();
            noOfTime = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(couponCode);
            dest.writeString(couponName);
            dest.writeDouble(maxAmount);
            dest.writeDouble(amount);
            dest.writeDouble(amountPercentage);
            dest.writeInt(minOrderValue);
            dest.writeInt(noOfTime);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
            @Override
            public Coupon createFromParcel(Parcel in) {
                return new Coupon(in);
            }

            @Override
            public Coupon[] newArray(int size) {
                return new Coupon[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(String couponCode) {
            this.couponCode = couponCode;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public double getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(double maxAmount) {
            this.maxAmount = maxAmount;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getAmountPercentage() {
            return amountPercentage;
        }

        public void setAmountPercentage(double amountPercentage) {
            this.amountPercentage = amountPercentage;
        }

        public int getMinOrderValue() {
            return minOrderValue;
        }

        public void setMinOrderValue(int minOrderValue) {
            this.minOrderValue = minOrderValue;
        }

        public int getNoOfTime() {
            return noOfTime;
        }

        public void setNoOfTime(int noOfTime) {
            this.noOfTime = noOfTime;
        }
    }
}