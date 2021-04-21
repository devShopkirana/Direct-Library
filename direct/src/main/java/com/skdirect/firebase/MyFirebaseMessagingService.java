package com.skdirect.firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.skdirect.R;
import com.skdirect.activity.MyOrderActivity;
import com.skdirect.utils.MySingltonApplication;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        System.out.println("From data payload: " + remoteMessage.getData());
        if (remoteMessage.getData().size() > 0) {
            System.out.println("Message data payload: " + remoteMessage.getData());
        }
        JSONObject object = new JSONObject(remoteMessage.getData());
        try {
            String redirectUrl = "", type = null;
            if (object.has("redirecturl")) {
                redirectUrl = object.getString("redirecturl");
            }
            if (object.has("Type")) {
                type = object.getString("Type");
            }

            if (type != null && type.equals("OTP")) {
                MySingltonApplication.getInstance().otp = object.getString("body");
                MySingltonApplication.getInstance().otp.replaceAll("[^0-9]", "");
            } else {
                showNotification(object.getString("icon"),
                        object.getString("body"),
                        object.getString("title"), 0);

                if (object.has("languageUpdateDate")) {
                    String localLastLanguageUpdateDate = SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.LAST_LANGUAGE_UPDATE_DATE);
                    String serverLastLanguageUpdateDate = object.getString("languageUpdateDate");
                    DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    try {
                        if (TextUtils.isNullOrEmpty(localLastLanguageUpdateDate)) {
                            SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.LAST_LANGUAGE_UPDATE_DATE, serverLastLanguageUpdateDate);
                            SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_FETCH_LANGUAGE, true);
                        } else {
                            Date serverdate = originalFormat.parse(serverLastLanguageUpdateDate);
                            Date localdate = originalFormat.parse(localLastLanguageUpdateDate);
                            if (serverdate.after(localdate)) {
                                SharePrefs.getInstance(getApplicationContext()).putString(SharePrefs.LAST_LANGUAGE_UPDATE_DATE, serverLastLanguageUpdateDate);
                                SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_FETCH_LANGUAGE, true);
                            }
                        }

                        if (SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_FETCH_LANGUAGE)) {
                            new FirebaseLanguageFetch(getApplicationContext()).fetchLanguage();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void showNotification(String imageUrl, String messageBody, String title, int actionId) {
        Bitmap myBitmap = null;
        try {
            if (!TextUtils.isNullOrEmpty(imageUrl)) {
                if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {
                    myBitmap = getBitmapFromURL(imageUrl);
                }
            }
            Intent intent = null;
            intent = new Intent(getApplicationContext(), MyOrderActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel mChannel = new NotificationChannel(getResources().getString(R.string.app_name),
                        getResources().getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
                mChannel.enableLights(true);
                mChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder notificationBuilder;
            if (myBitmap != null) {
                notificationBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.app_name))
                        .setSmallIcon(R.mipmap.notification)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(myBitmap))
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setContentInfo(messageBody)
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                        .setChannelId(getResources().getString(R.string.app_name))
                        .setContentIntent(pendingIntent);
            } else {
                notificationBuilder = new NotificationCompat.Builder(this, getResources().getString(R.string.app_name))
                        .setSmallIcon(R.mipmap.notification)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setContentInfo(messageBody)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(messageBody))
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setSound(defaultSoundUri)
                        .setChannelId(getResources().getString(R.string.app_name))
                        .setColorized(true)
                        .setColor(ContextCompat.getColor(this, R.color.colorAccent))
                        .setContentIntent(pendingIntent);
            }

            NotificationManagerCompat notificationManagerCompat =
                    NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.notify((int) System.currentTimeMillis(), notificationBuilder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}