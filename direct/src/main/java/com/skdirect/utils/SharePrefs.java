package com.skdirect.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharePrefs {

    public static String PREFERENCE = "Direct";
    public static final String IS_SHOW_INTRO = "IsShowIntro";
    public static final String BUYER_URL = "BuyerUrl";
    public static final String SELLER_URL = "SellerUrl";
    public static final String APP_VERSION = "AppVersion";
    public static final String IS_SELLER = "IsSeller";
    public static final String IS_USER_EXISTS = "UserExists";
    public static final String RESULT = "Result";
    public static final String USER_ID = "Userid";
    public static final String IS_LOGIN ="is_loggedIn";
    public static final String Is_First_Time ="Is_First_Time";
    public static final String TOKEN ="access_token";
    public static final String USER_NAME ="user_name";
    public static final String USER_IMAGE ="user_image";
    public static final String FIRST_NAME ="first_name";
    public static final String MIDDLE_NAME ="middle_name";
    public static final String LAST_NAME ="last_name";
    public static final String MOBILE_NUMBER ="mobile";
    public static final String SHOP_NAME ="shop_name";
    public static final String EMAIL_ID ="email";
    public static final String STATE ="state";
    public static final String CART_ITEM_ID ="cart_item_id";
    public static final String CITYNAME ="city_name";
    public static final String IS_ACTIVE ="IsActive";
    public static final String USER_IS_ACTIVE ="user_IsActive";
    public static final String USER_IS_DELETE ="user_IsDelete";
    public static final String IS_DELETE ="IsDelete";
    public static final String IS_Mall ="IsMall";
    public static final String PIN_CODE_master ="Pincode_master";
    public static final String PIN_CODE ="Pincode";
    public static final String IMAGE_PATH ="image_path";
    public static final String IS_REGISTRATIONCOMPLETE ="IsRegistrationcomplete";
    public static final String IS_CONTACTREAD ="IscontactRead";
    public static final String IS_SUPER_ADMIN ="IsSuperAdmin";
    public static final String ASP_NET_USER_ID ="AspNetuserId";
    public static final String ID ="id";
    public static final String MALL_ID ="mall_id";
    public static final String USER_DC_ID ="_user_id";
    public static final String USER_DC_USER_ID ="user_dc_id";
    public static final String ENCRIPTED_ID ="encrited_id";
    public static final String LAT ="Latitiute";
    public static final String LON ="Longitude";
    public static final String BUSINESS_TYPE ="BusinessType";
    public static final String DELIVERY ="delivery";
    public static final String SELECTED_LANGUAGE ="selectedLanguage";
    public static final String IS_FETCH_LANGUAGE = "is_fetch_language";
    public static final String LAST_LANGUAGE_UPDATE_DATE = "last_language_update_date";
    public static final String FILTER_CATEGORY_LIST = "filter_category_list";
    public static final String FILTER_PRICE = "filter_price";
    public static final String TOKEN_PASSWORD = "Pwd";
    public static final String CAME_FROM_CART = "came_from_cart";

    public static final String PRIVACY_POLICY = "privacy_policy";
    public static final String TERMS_CONDITION = "terms_condition";
    public static final String ABOUT_APP = "about_app";
    public static final String SOURCEKEY ="SOURCEKEY";

    private Context ctx;
    public SharedPreferences sharedPreferences;
    private static SharePrefs instance;


    public SharePrefs(Context context) {
        ctx = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static SharePrefs getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharePrefs(ctx);
        }
        return instance;
    }

    public void putString(String key, String val) {
        sharedPreferences.edit().putString(key, val).apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putInt(String key, Integer val) {
        sharedPreferences.edit().putInt(key, val).apply();
    }


    public void putBoolean(String key, Boolean val) {
        sharedPreferences.edit().putBoolean(key, val).apply();
    }

    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public static Boolean getSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        return settings.getBoolean(name,false );
    }

    public static void setSharedPreference(Context context, String name, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(name, value);
        editor.apply();
    }
    public static String getStringSharedPreferences(Context context, String name) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        return settings.getString(name,"" );
    }

    public static void setStringSharedPreference(Context context, String name, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public static void clearPref(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }
}