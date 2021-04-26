package com.skdirect.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.PhoneNumberUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.skdirect.BuildConfig;
import com.skdirect.R;
import com.skdirect.model.TokenModel;

import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final String MOBILE_NO_PATTERN = "^[+]?[0-9]{10,13}$";
    private static final String EMAIL_NO_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static Dialog customDialog;
    public String myFormat = "yyyy-MM-dd'T'HH:mm:ss";
    private boolean status;
    private static Context context;
    public static String pattern = "##.##";
    public static Boolean isGPS = false;

    public Utils(Context _mContext) {
        context = _mContext;
    }

    public static void setToast(Context _mContext, String str) {
        Toast toast = Toast.makeText(_mContext, str, Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void setLongToast(Context _mContext, String str) {
        Toast toast = Toast.makeText(_mContext, str, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static boolean isValidMobile(String mobile) {
        Pattern pattern = Pattern.compile(MOBILE_NO_PATTERN);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_NO_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*\\d)(?=.*[#$^+=!*()@%&]).{6,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static void hideKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static boolean getConnectivityStatusString(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else return activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE;
        } else {
            return false;
        }
    }

    public static void showProgressDialog(Activity activity) {
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog = null;
        }
        customDialog = new Dialog(activity, R.style.CustomDialog);
        LayoutInflater inflater = LayoutInflater.from(activity);
        View mView = inflater.inflate(R.layout.progress_dialog, null);
        customDialog.setCancelable(false);
        customDialog.setContentView(mView);
        if (!customDialog.isShowing() && !activity.isFinishing()) {
            customDialog.show();
        }
    }

    public static void hideProgressDialog() {
        if (customDialog != null && customDialog.isShowing()) {
            customDialog.dismiss();
        }
    }


    public static void hideKeyboard(Activity context, View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static void showSnackBar(View view, String text, boolean isShowAction, String actionText) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        if (isShowAction)
            snackbar.setAction(actionText, view1 -> snackbar.dismiss());
        snackbar.show();
    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public static boolean ISGPSON(Context context) {
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );

        isGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPS;
    }

    public static String getToken(Context context) {
        return SharePrefs.getInstance(context).getString(SharePrefs.TOKEN);
    }

    public static String getDateFormate(String ServerDate) {
        // 2018-12-24T15:48:15.707+05:30
        if (!ServerDate.equalsIgnoreCase("") && !ServerDate.equalsIgnoreCase(null)) {
            DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());//These format come to server
            originalFormat.setTimeZone(TimeZone.getDefault());
            DateFormat targetFormat = new SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault());  //change to new format here  //dd-MM-yyyy HH:mm:ss

            Date date = null;
            String formattedDate = "";
            try {
                date = originalFormat.parse(ServerDate);

                formattedDate = targetFormat.format(date);  //result

            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formattedDate;


        } else {
            return "null";
        }

    }

    public static String getDeviceUniqueID(Context activity) {
        if (BuildConfig.DEBUG) {
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID) + "shinoo";
            // return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            return Settings.Secure.getString(activity.getContentResolver(), Settings.Secure.ANDROID_ID);
        }
    }

    public static String getFcmToken() {
        return  FirebaseInstanceId.getInstance().getToken();
    }

    public static String getFullAddress(Activity activity, double lat, double log) {
        String locationAddresh = null;
        try {
        Geocoder mGeocoder = new Geocoder(activity, Locale.getDefault());
        List<Address> addresses = mGeocoder.getFromLocation(lat, log, 1);
        if (addresses.get(0).getLocality() != null) {
            locationAddresh = addresses.get(0).getAddressLine(0);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationAddresh;
    }
    public static boolean gpsPermission(Activity activity, String callTime) {
        final boolean[] isGPS = new boolean[1];
        try {
            new GpsUtils(activity).turnGPSOn(isGPSEnable -> {
                isGPS[0] = isGPSEnable;
                if (!isGPS[0] && !callTime.equalsIgnoreCase("runtime")) {
                    new GpsUtils(activity).turnGPSOn(isGPSEnable1 -> isGPS[0] = isGPSEnable1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
        return isGPS[0];
    }
    public static String getCityName(Activity activity, double lat, double log) {
        String locationAddresh = null;
        try {
            Geocoder mGeocoder = new Geocoder(activity, Locale.getDefault());
            List<Address> addresses = mGeocoder.getFromLocation(lat, log, 1);
            if (addresses.get(0).getLocality() != null) {
                locationAddresh = addresses.get(0).getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return locationAddresh;
    }

    public static double distance(double initialLat, double initialLong,
                                  double finalLat, double finalLong) {
        int R = 6371; // km (Earth radius)
        double dLat = deg2rad(finalLat-initialLat);
        double dLon = deg2rad(finalLong-initialLong);
        initialLat = deg2rad(initialLat);
        finalLat = deg2rad(finalLat);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(initialLat) * Math.cos(finalLat);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }



    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static void shareProduct(Context context, String text) {
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, text);
        sendInt.setType("text/plain");
        context.startActivity(Intent.createChooser(sendInt, "Share"));
    }

    public static void showShareWhatsappDialog(Context context, String textMsg, String number) {
        try {
            BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomTheme);
            dialog.setContentView(R.layout.dialog_whatsapp_shared);
            dialog.setCanceledOnTouchOutside(true);
            LinearLayout llWhatsapp = dialog.findViewById(R.id.llWhatsapp);
            LinearLayout llWhatsappBusiness = dialog.findViewById(R.id.llWhatsappBusiness);
            TextView tvSharewith = dialog.findViewById(R.id.tvSharewith);
            TextView tvWhasapp = dialog.findViewById(R.id.tvWhasapp);
            TextView tvWhasappBusiness = dialog.findViewById(R.id.tvWhasappBusiness);
            tvSharewith.setText("Share with");
            tvWhasapp.setText("Whatsapp");
            tvWhasappBusiness.setText("Whatsapp Business");
          /*  tvSharewith.setText("DirectSDK.getInstance().dbHelper.getString(R.string.share_with)");
            tvWhasapp.setText(DirectSDK.getInstance().dbHelper.getString(R.string.whatsapp));
            tvWhasappBusiness.setText(DirectSDK.getInstance().dbHelper.getString(R.string.whatsapp_business));*/
            if (appInstalledOrNot(context,"com.whatsapp") && appInstalledOrNot(context,"com.whatsapp.w4b")) {
                dialog.show();
            } else shareOnWhatsapp(context, textMsg, number, !appInstalledOrNot(context,"com.whatsapp"));
            llWhatsapp.setOnClickListener(view -> {
                shareOnWhatsapp(context, textMsg, number, false);
                dialog.dismiss();
            });
            llWhatsappBusiness.setOnClickListener(view -> {
                shareOnWhatsapp(context, textMsg, number, true);
                dialog.dismiss();
            });
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void shareOnWhatsapp(Context context, String textMsg, String number, boolean isWB) {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        if (isWB) {
            whatsappIntent.setPackage("com.whatsapp.w4b");
        } else {
            whatsappIntent.setPackage("com.whatsapp");
        }
        if (number != null && !number.equals("")) {
            whatsappIntent.putExtra("jid", PhoneNumberUtils.stripSeparators("91" + number) + "@s.whatsapp.net");
        }
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, textMsg);
        try {
            context.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Utils.setToast(context, "Whatsapp not installed.");
        }
    }

    public static boolean appInstalledOrNot(Context context, String packageManager) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageManager, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getTokenData(Context activity, TokenModel model){
        SharePrefs.getInstance(activity).putString(SharePrefs.TOKEN, model.getAccess_token());
        SharePrefs.getInstance(activity).putString(SharePrefs.USER_NAME, model.getUserName());
        SharePrefs.setSharedPreference(activity, SharePrefs.IS_REGISTRATIONCOMPLETE, model.getIsRegistrationComplete());
        SharePrefs.setStringSharedPreference(activity,SharePrefs.LAT, String.valueOf(model.getLatitiute()));
        SharePrefs.setStringSharedPreference(activity,SharePrefs.LON, String.valueOf(model.getLongitude()));
        SharePrefs.getInstance(activity).putString(SharePrefs.BUSINESS_TYPE, model.getBusinessType());
        SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_CONTACTREAD, model.getIscontactRead());
        SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_SUPER_ADMIN, model.getIsSuperAdmin());
        SharePrefs.getInstance(activity).putString(SharePrefs.ASP_NET_USER_ID, model.getAspNetuserId());
        try {
            JSONObject jsonObject = new JSONObject(model.getUserDetail());
            SharePrefs.getInstance(activity).putString(SharePrefs.FIRST_NAME, jsonObject.getString("FirstName"));
            SharePrefs.getInstance(activity).putInt(SharePrefs.ID, jsonObject.getInt("Id"));
            SharePrefs.getInstance(activity).putString(SharePrefs.MOBILE_NUMBER, jsonObject.getString("MobileNo"));
            SharePrefs.getInstance(activity).putString(SharePrefs.EMAIL_ID, jsonObject.getString("Email"));
            SharePrefs.getInstance(activity).putString(SharePrefs.STATE, jsonObject.getString("State"));
            SharePrefs.getInstance(activity).putString(SharePrefs.CITYNAME, jsonObject.getString("City"));
            SharePrefs.getInstance(activity).putString(SharePrefs.PIN_CODE, jsonObject.getString("Pincode"));
            SharePrefs.getInstance(activity).putInt(SharePrefs.PIN_CODE_master, jsonObject.getInt("PinCodeMasterId"));
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_ACTIVE, jsonObject.getBoolean("IsActive"));
            SharePrefs.getInstance(activity).putBoolean(SharePrefs.IS_DELETE, jsonObject.getBoolean("IsDelete"));
        /*    JSONArray jsonArray = jsonObject.getJSONArray("UserDeliveryDC");
            if (jsonArray != null && jsonArray.length() > 0) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    SharePrefs.getInstance(activity).putBoolean(SharePrefs.USER_IS_DELETE, object.getBoolean("IsDelete"));
                    SharePrefs.getInstance(activity).putBoolean(SharePrefs.USER_IS_ACTIVE, object.getBoolean("IsActive"));
                    SharePrefs.getInstance(activity).putInt(SharePrefs.USER_DC_ID, object.getInt("Id"));
                    SharePrefs.getInstance(activity).putInt(SharePrefs.USER_DC_USER_ID, object.getInt("UserId"));
                    SharePrefs.getInstance(activity).putString(SharePrefs.DELIVERY, object.getString("Delivery"));

                }
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}