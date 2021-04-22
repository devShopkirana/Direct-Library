package com.skdirect.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.skdirect.R;
import com.skdirect.databinding.ActivityMainBinding;
import com.skdirect.firebase.FirebaseLanguageFetch;
import com.skdirect.fragment.HomeFragment;
import com.skdirect.model.CartModel;
import com.skdirect.utils.AppSignatureHelper;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.GpsUtils;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.TextUtils;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.MainActivityViewMode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ActivityMainBinding mBinding;
    private boolean doubleBackToExitPressedOnce = false;
    private SharedPreferences sharedPreferences;
    public boolean positionChanged = false;
    public Toolbar appBarLayout;
    public TextView userNameTV, mobileNumberTV, setLocationTV;
    private MainActivityViewMode mainActivityViewMode;
    public DBHelper dbHelper;
    Boolean isGPS = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainActivityViewMode = ViewModelProviders.of(this).get(MainActivityViewMode.class);
        openFragment(new HomeFragment());
        Log.e("key: ", new AppSignatureHelper(getApplicationContext()).getAppSignatures() + "");
        initView();
        setVarible();
        openFragment(new HomeFragment());


        clickListener();
        setupBadge();
        ///callRunTimePermissions();
    }

    private void setVarible() {
        setLocationTV.setText(SharePrefs.getInstance(MainActivity.this).getString(SharePrefs.CITYNAME) + " " + SharePrefs.getInstance(MainActivity.this).getString(SharePrefs.PIN_CODE));
        if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE)) {
            userNameTV.setText(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.FIRST_NAME));
            if (SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_LOGIN)) {
                mBinding.llLogout.setVisibility(View.VISIBLE);
                mBinding.llSignIn.setVisibility(View.GONE);
                mobileNumberTV.setText(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.MOBILE_NUMBER));
            } else {
                mBinding.llSignIn.setVisibility(View.VISIBLE);
                mBinding.llLogout.setVisibility(View.GONE);
                mBinding.tvSigninTitle.setText(dbHelper.getString(R.string.log_in));
                mobileNumberTV.setText("");
            }
        } else {
            userNameTV.setText(R.string.guest_user);
            mBinding.llSignIn.setVisibility(View.VISIBLE);
            mBinding.tvSigninTitle.setText(dbHelper.getString(R.string.sign_in));
            mBinding.llLogout.setVisibility(View.GONE);
            mobileNumberTV.setText("");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupBadge();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_FETCH_LANGUAGE)) {
            new FirebaseLanguageFetch(getApplicationContext()).fetchLanguage();
        }
    }

    private void clickListener() {
        mBinding.llProfile.setOnClickListener(this);
        mBinding.llChnagePassword.setOnClickListener(this);
        mBinding.llChet.setOnClickListener(this);
        mBinding.llLogout.setOnClickListener(this);
        mBinding.llSignIn.setOnClickListener(this);
        mBinding.llHowtoUse.setOnClickListener(this);
        mBinding.llChangeLanguage.setOnClickListener(this);
        mBinding.llRateThisApp.setOnClickListener(this);
        mBinding.llPrivatePolcy.setOnClickListener(this);
        mBinding.llAboutApp.setOnClickListener(this);
        mBinding.llTermsAndCondition.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if (requestCode == 2) {
            if (requestCode == 2 && resultCode == RESULT_OK) {
                String LOCATION = data.getStringExtra("LOCATION");
                if (!TextUtils.isNullOrEmpty(LOCATION)) {
                    setLocationTV.setText(LOCATION);
                }
            }
        }
        openFragment(new HomeFragment());
    }

    private void initView() {
        dbHelper = DirectSDK.getInstance().dbHelper;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        appBarLayout = mBinding.toolbarId.toolbar;
        userNameTV = mBinding.navTop.tvUserName;
        mobileNumberTV = mBinding.navTop.tvMobileName;
        setLocationTV = mBinding.toolbarId.tvLoction;
        if (!TextUtils.isNullOrEmpty(SharePrefs.getInstance(this).getString(SharePrefs.FIRST_NAME))) {
            userNameTV.setText(SharePrefs.getInstance(this).getString(SharePrefs.FIRST_NAME));
        }

        mBinding.toolbarId.ivMenu.setOnClickListener(view -> mBinding.drawer.openDrawer(GravityCompat.START));

        Menu menu = mBinding.toolbarId.bottomNavigation.getMenu();
        MenuItem home = menu.findItem(R.id.nav_home);
        MenuItem profile = menu.findItem(R.id.nav_profile);
        MenuItem myOrder = menu.findItem(R.id.nav_my_order);
        MenuItem chat = menu.findItem(R.id.nav_chat);
        home.setTitle(dbHelper.getString(R.string.home));
        profile.setTitle(dbHelper.getString(R.string.profile));
        myOrder.setTitle(dbHelper.getString(R.string.my_order));
        chat.setTitle(dbHelper.getString(R.string.chat));

        mBinding.toolbarId.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                openFragment(new HomeFragment());
            } else if (itemId == R.id.nav_profile) {
                positionChanged = true;
                if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE) && SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_LOGIN)) {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            } else if (itemId == R.id.nav_my_order) {
                positionChanged = true;
                if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE) && SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_LOGIN)) {
                    startActivity(new Intent(MainActivity.this, MyOrderActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            } else if (itemId == R.id.nav_chat) {
                positionChanged = true;
                startActivity(new Intent(MainActivity.this, ChatActivity.class));
            }
            return true;
        });

        mBinding.toolbarId.notifictionCount.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), CartActivity.class)));

        setLocationTV.setOnClickListener(view -> {
            if (Utils.ISGPSON(MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, MapsExtendedActivity.class);
                intent.putExtra("Lat", Double.parseDouble(SharePrefs.getStringSharedPreferences(MainActivity.this, SharePrefs.LAT)));
                intent.putExtra("Lon", Double.parseDouble(SharePrefs.getStringSharedPreferences(MainActivity.this, SharePrefs.LON)));
                startActivityForResult(intent, 2);
            } else {
                new GpsUtils(MainActivity.this).turnGPSOn(isGPSEnable -> {
                    // turn on GPS
                    isGPS = isGPSEnable;
                });
            }
        });

        if (!TextUtils.isNullOrEmpty(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.USER_IMAGE))) {
            Glide.with(this)
                    .load(SharePrefs.getInstance(getApplicationContext()).getString(SharePrefs.USER_IMAGE))
                    .centerCrop()
                    .into(mBinding.navTop.profileImageNav);
        } else {
            mBinding.navTop.profileImageNav.setImageDrawable(getResources().getDrawable(R.drawable.profile_round));
        }

        getCartItemApi();
        mBinding.tvProfileHead.setText(dbHelper.getString(R.string.profile));
        mBinding.tvProfileTitle.setText(dbHelper.getString(R.string.profile));
        mBinding.tvChangePassTitle.setText(dbHelper.getString(R.string.change_pass));
        mBinding.tvChatTitle.setText(dbHelper.getString(R.string.chat));
        mBinding.tvOtherSettingsHead.setText(dbHelper.getString(R.string.other_settings));
        mBinding.tvChangeLangTitle.setText(dbHelper.getString(R.string.change_language));
        mBinding.tvRateAppTitle.setText(dbHelper.getString(R.string.rate_app));
        mBinding.tvPrivacyTitle.setText(dbHelper.getString(R.string.privacy_policy));
        mBinding.tvAboutTitle.setText(dbHelper.getString(R.string.about_direct));
        mBinding.tvTermsAndCondition.setText(dbHelper.getString(R.string.terms_and_condition));
        mBinding.tvHelpTitle.setText(dbHelper.getString(R.string.help));
        mBinding.tvHowToTitle.setText(dbHelper.getString(R.string.how_to_use));
        mBinding.tvLogoutTitle.setText(dbHelper.getString(R.string.logout));
        mBinding.tvSigninTitle.setText(dbHelper.getString(R.string.sign_in));
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawer.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() <= 1) {
                if (doubleBackToExitPressedOnce) {
                    super.onBackPressed();
                    //finishAffinity();
                    return;
                }
                doubleBackToExitPressedOnce = true;
                Snackbar.make(mBinding.drawer, dbHelper.getString(R.string.back_again),
                        Snackbar.LENGTH_SHORT).show();
                new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
            } else {
                if (positionChanged) {
                    positionChanged = false;
                    mBinding.toolbarId.bottomNavigation.getMenu().getItem(0).setChecked(true);
                    super.onBackPressed();
                } else {
                    super.onBackPressed();
                }
            }
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_frame, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.ll_profile) {
            if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE) && SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_LOGIN)) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_chnage_password) {
            if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE) && SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_LOGIN)) {
                startActivity(new Intent(getApplicationContext(), ChangePasswordActivity.class));
            } else {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_chet) {
            mBinding.drawer.closeDrawers();
            startActivity(new Intent(getApplicationContext(), ChatActivity.class));
        } else if (id == R.id.ll_rate_this_app) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_logout) {
            mBinding.drawer.closeDrawers();
            clearSharePrefs();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        } else if (id == R.id.ll_sign_in) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_howto_use) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UChGqYYqXeuGdNVqQ9MQS2Fw?app=desktop"));
            startActivity(intent);
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.llChangeLanguage) {
            startActivity(new Intent(getApplicationContext(), ChangeLanguageActivity.class));
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_private_polcy) {
            startActivity(new Intent(getApplicationContext(), WebviewActivity.class).putExtra("FunctionName", "PrivacyPolicy"));
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_about_app) {
            startActivity(new Intent(getApplicationContext(), WebviewActivity.class).putExtra("FunctionName", "AboutApp"));
            mBinding.drawer.closeDrawers();
        } else if (id == R.id.ll_terms_and_condition) {
            startActivity(new Intent(getApplicationContext(), WebviewActivity.class).putExtra("FunctionName", "TermsCondition"));
            mBinding.drawer.closeDrawers();
        }
    }

    public void clearSharePrefs() {
        DirectSDK.getInstance().cartRepository.truncateCart();
        sharedPreferences.edit().clear().apply();
        SharePrefs.getInstance(getApplicationContext()).putBoolean(SharePrefs.IS_FETCH_LANGUAGE, true);
        DirectSDK.getInstance().dbHelper.deleteDataFromTable();
    }

    private void setupBadge() {
        int count = DirectSDK.getInstance().cartRepository.getCartCount1();
        if (count == 0) {
            mBinding.toolbarId.cartBadge.setVisibility(View.GONE);
        } else {
            mBinding.toolbarId.cartBadge.setVisibility(View.VISIBLE);
            mBinding.toolbarId.cartBadge.setText(String.valueOf(Math.min(count, 99)));
        }
    }


    private void getCartItemApi() {
        mainActivityViewMode.getCartItemsRequest();
        mainActivityViewMode.getCartItemsVM().observe(this, model -> {
            Utils.hideProgressDialog();
            if (model.isSuccess() && model.getResultItem().getCart() != null) {
                System.out.println("HomeSellerId " + model.getResultItem().getSellerId());
                for (CartModel itemModel : model.getResultItem().getCart()) {
                    itemModel.setCarId(model.getResultItem().getId());
                }
                DirectSDK.getInstance().cartRepository.addToCart(model.getResultItem().getCart());
            } else {
                DirectSDK.getInstance().cartRepository.truncateCart();
            }
            setupBadge();
        });
    }

   /* private void callLocationAPI(double latitude, double longitude) {
        mainActivityViewMode.getMapViewModelRequest(latitude, longitude);
        mainActivityViewMode.getMapViewModel().observe(this, new Observer<JsonObject>() {
            @Override
            public void onChanged(JsonObject data) {
                Utils.hideProgressDialog();
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(data.toString());
                    JSONObject components = jsonResponse.getJSONObject("geometry");
                    JSONObject location = components.getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String cityName = addresses.get(0).getLocality();
                    Log.e("cityName", "cityName  " + cityName);
                    setLocationTV.setText(cityName);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void callRunTimePermissions() {
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
        Permissions.check(this*//*context*//*, permissions, null*//*rationale*//*, null*//*options*//*, new PermissionHandler() {
            @Override
            public void onGranted() {
                Log.e("onDenied", "onGranted");
                GPSTracker gpsTracker = new GPSTracker(getApplicationContext());
                if (gpsTracker != null) {
                    callLocationAPI(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                }
            }
            @Override
            public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                super.onDenied(context, deniedPermissions);
                Log.e("onDenied", "onDenied" + deniedPermissions);

            }
        });
    }*/
}