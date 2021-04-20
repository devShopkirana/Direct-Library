package com.skdirect.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchDataModel {

    @SerializedName("Table1")
    ArrayList<TableOneModel> tableOneModels;

    @SerializedName("Table2")
    ArrayList<TableOneTwo> tableTwoModels;

    public ArrayList<TableOneModel> getTableOneModels() {
        return tableOneModels;
    }

    public void setTableOneModels(ArrayList<TableOneModel> tableOneModels) {
        this.tableOneModels = tableOneModels;
    }

    public ArrayList<TableOneTwo> getTableTwoModels() {
        return tableTwoModels;
    }

    public void setTableTwoModels(ArrayList<TableOneTwo> tableTwoModels) {
        this.tableTwoModels = tableTwoModels;
    }

    public class TableOneModel {
        @SerializedName("row_num")
        private int row_num;

        @SerializedName("id")
        private int Id;

        @SerializedName("ProductName")
        private String ProductName;

        @SerializedName("Mrp")
        private double Mrp;

        @SerializedName("SellingPrice")
        private double SellingPrice;

        @SerializedName("SellerId")
        private int SellerId;

        @SerializedName("NoofView")
        private int NoofView;

        @SerializedName("ImagePath")
        private String ImagePath;

        @SerializedName("Uom")
        private String Uom;



        @SerializedName("Measurement")
        private int Measurement;

        public int getMeasurement() {
            return Measurement;
        }

        public void setMeasurement(int measurement) {
            Measurement = measurement;
        }

        public int getRow_num() {
            return row_num;
        }

        public void setRow_num(int row_num) {
            this.row_num = row_num;
        }

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getProductName() {
            return ProductName;
        }

        public void setProductName(String productName) {
            ProductName = productName;
        }

        public double getMrp() {
            return Mrp;
        }

        public void setMrp(double mrp) {
            Mrp = mrp;
        }

        public double getSellingPrice() {
            return SellingPrice;
        }

        public void setSellingPrice(double sellingPrice) {
            SellingPrice = sellingPrice;
        }

        public int getSellerId() {
            return SellerId;
        }

        public void setSellerId(int sellerId) {
            SellerId = sellerId;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }
        public int getNoofView() {
            return NoofView;
        }

        public void setNoofView(int noofView) {
            NoofView = noofView;
        }
        public String getUom() {
            return Uom;
        }

        public void setUom(String uom) {
            Uom = uom;
        }
    }

    public class TableOneTwo {

        @SerializedName("id")
        private int id;

        @SerializedName("ShopName")
        private String ShopName;

        @SerializedName("ImagePath")
        private String ImagePath;

        @SerializedName("AddressOne")
        private String AddressOne;

        @SerializedName("AddressTwo")
        private String AddressTwo;

        @SerializedName("AddressThree")
        private String AddressThree;

        @SerializedName("PinCode")
        private String PinCode;
        @SerializedName("CityName")
        private String CityName;
        @SerializedName("StateName")
        private String StateName;
        @SerializedName("Rating")
        private int Rating;

        @SerializedName("Distance")
        private double Distance;



        ArrayList<TableOneModel> productList;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShopName() {
            return ShopName;
        }

        public void setShopName(String shopName) {
            ShopName = shopName;
        }

        public String getImagePath() {
            return ImagePath;
        }

        public void setImagePath(String imagePath) {
            ImagePath = imagePath;
        }

        public String getAddressOne() {
            return AddressOne;
        }

        public void setAddressOne(String addressOne) {
            AddressOne = addressOne;
        }

        public String getAddressTwo() {
            return AddressTwo;
        }

        public void setAddressTwo(String addressTwo) {
            AddressTwo = addressTwo;
        }

        public String getAddressThree() {
            return AddressThree;
        }

        public void setAddressThree(String addressThree) {
            AddressThree = addressThree;
        }

        public String getPinCode() {
            return PinCode;
        }

        public void setPinCode(String pinCode) {
            PinCode = pinCode;
        }

        public String getCityName() {
            return CityName;
        }

        public void setCityName(String cityName) {
            CityName = cityName;
        }

        public String getStateName() {
            return StateName;
        }

        public void setStateName(String stateName) {
            StateName = stateName;
        }

        public int getRating() {
            return Rating;
        }

        public void setRating(int rating) {
            Rating = rating;
        }

        public ArrayList<TableOneModel> getProductList() {
            return productList;
        }
        public double getDistance() {
            return Distance;
        }

        public void setDistance(double distance) {
            Distance = distance;
        }

        public void setProductList(ArrayList<TableOneModel> productList) {
            this.productList = productList;
        }
    }
}
