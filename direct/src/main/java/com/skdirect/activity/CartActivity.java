package com.skdirect.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.skdirect.R;
import com.skdirect.adapter.CartListAdapter;
import com.skdirect.api.CommonClassForAPI;
import com.skdirect.databinding.ActivityCartdBinding;
import com.skdirect.interfacee.CartItemInterface;
import com.skdirect.model.CartItemModel;
import com.skdirect.model.CartMainModel;
import com.skdirect.model.CartModel;
import com.skdirect.model.ItemAddModel;
import com.skdirect.utils.DBHelper;
import com.skdirect.utils.DirectSDK;
import com.skdirect.utils.SharePrefs;
import com.skdirect.utils.Utils;
import com.skdirect.viewmodel.CartItemViewMode;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import io.reactivex.observers.DisposableObserver;

public class CartActivity extends AppCompatActivity implements View.OnClickListener, CartItemInterface {
    private ActivityCartdBinding mBinding;
    private CartItemViewMode cartItemViewMode;
    private final ArrayList<CartModel> cartItemList = new ArrayList<>();
    private CartListAdapter cartListAdapter;
    private CartItemModel cartItemDataModel;
    private int skipCount = 0;
    private int pastVisiblesItems = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;
    private boolean loading = true;
    private double totalAmount;
    private DBHelper dbHelper;
    private CommonClassForAPI commonClassForAPI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_cartd);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setBackgroundDrawable( new ColorDrawable( getResources().getColor(R.color.colorAccentDir )));

       // setTitle(DirectSDK.getInstance().dbHelper.getString(R.string.shopping_bag));

        cartItemViewMode = ViewModelProviders.of(this).get(CartItemViewMode.class);
        dbHelper = DirectSDK.getInstance().dbHelper;
        initView();
        mBinding.tvTittle.setText(dbHelper.getString(R.string.shopping_bag));
        mBinding.tvTittleClear.setText(dbHelper.getString(R.string.clear_all));
        mBinding.ivBackPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
        mBinding.tvTittleClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cartItemList.size() > 0) {
                    showClearCartDialog();
                } else {
                    Utils.setToast(getApplicationContext(), dbHelper.getString(R.string.no_items_available));
                }
            }
        });
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        commonClassForAPI = CommonClassForAPI.getInstance(this);
        DirectSDK.getInstance().cartRepository.getCartValue().observe(this, aDouble -> {
            if (aDouble != null) {
                totalAmount = aDouble;
                mBinding.tvTotalAmount.setText("₹ " + totalAmount);
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("ComeTo").equalsIgnoreCase("Login")) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        } else {
            finish();
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.iv_back_press) {
            onBackPressed();
        } else if (id == R.id.btnCheckout) {
            SharePrefs.setSharedPreference(getApplicationContext(), SharePrefs.CAME_FROM_CART, false);
            if (SharePrefs.getSharedPreferences(getApplicationContext(), SharePrefs.IS_REGISTRATIONCOMPLETE) && SharePrefs.getInstance(getApplicationContext()).getBoolean(SharePrefs.IS_LOGIN)) {
                startActivity(new Intent(getApplicationContext(), PaymentActivity.class)
                        .putExtra("cartItemSize", cartItemDataModel)
                        .putExtra("totalAmount", totalAmount));
            } else {
                SharePrefs.setSharedPreference(getApplicationContext(), SharePrefs.CAME_FROM_CART, true);
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        } else if (id == R.id.tv_keep_shopping) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }

    @Override
    public void plusButtonOnClick(CartModel cartModel) {
        int qty = cartModel.getQuantity();
        totalAmount = totalAmount + cartModel.getPrice();
        mBinding.tvTotalAmount.setText("₹ " + totalAmount);
        DirectSDK.getInstance().cartRepository.updateCartItem(cartModel);
        addItemInCart(qty, cartModel);
    }

    @Override
    public void minusButtonOnClick(CartModel cartModel, int position) {
        int qty = cartModel.getQuantity();
        if (qty >= 1) {
            totalAmount = totalAmount - cartModel.getPrice();
            mBinding.tvTotalAmount.setText("₹ " + totalAmount);
            DirectSDK.getInstance().cartRepository.updateCartItem(cartModel);
            addItemInCart(qty, cartModel);
        } else {
            totalAmount = totalAmount - cartModel.getPrice();
            mBinding.tvTotalAmount.setText("₹ " + totalAmount);
            cartListAdapter.notifyDataSetChanged();
            removeItemFromCart(cartModel, position);
            DirectSDK.getInstance().cartRepository.deleteCartItem(cartModel);
            if (cartItemList.size() == 0) {
                mBinding.rlCheckOut.setVisibility(View.GONE);
                mBinding.blankBasket.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void removeButtonOnClick(CartModel cartModel, int position) {
        removeItemAlertDialog(cartModel, position);
    }


    private void initView() {
        mBinding.tvNotingInBasket.setText(dbHelper.getString(R.string.nothing_in_basket));
        mBinding.tvKeepShopping.setText(dbHelper.getString(R.string.keep_shopping));
        mBinding.btnCheckout.setText(dbHelper.getString(R.string.checkout));

        mBinding.tvKeepShopping.setOnClickListener(this);
        mBinding.btnCheckout.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mBinding.rvCartItem.setLayoutManager(layoutManager);

        cartListAdapter = new CartListAdapter(getApplicationContext(), cartItemList, this);
        mBinding.rvCartItem.setAdapter(cartListAdapter);

        mBinding.rvCartItem.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();
                    if (loading) {
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false;
                            skipCount = skipCount + 10;
                            // mBinding.progressBar.setVisibility(View.VISIBLE);
                            //callCartList();
                        }
                    }
                }
            }
        });
        cartItemList.clear();

        callCartList();
    }

    private void callCartList() {
        if (Utils.isNetworkAvailable(getApplicationContext())) {
            Utils.showProgressDialog(this);
            cartItemsAPI();
        } else {
            Utils.setToast(getApplicationContext(), "No Internet Connection Please connect.");
        }
    }

    private void cartItemsAPI() {
        cartItemViewMode.getCartItemModelVMRequest(mBinding.rvCartItem, mBinding.blankBasket);
        cartItemViewMode.getCartItemModelVM().observe(this, (CartMainModel cartMainModel) -> {
            Utils.hideProgressDialog();
            if (cartMainModel.isSuccess()) {
                if (cartMainModel.getResultItem() != null) {
                    cartItemDataModel = cartMainModel.getResultItem();
                    if (cartMainModel.getResultItem().getCart().size() > 0) {
                        mBinding.rlCheckOut.setVisibility(View.VISIBLE);
                        mBinding.rvCartItem.post(() -> {
                            for (int i = 0; i < cartMainModel.getResultItem().getCart().size(); i++) {
                                totalAmount += totalAmount = cartMainModel.getResultItem().getCart().get(i).getQuantity() * cartMainModel.getResultItem().getCart().get(i).getPrice();
                                mBinding.tvTotalAmount.setText("₹ " + totalAmount);
                            }
                            cartItemList.addAll(cartMainModel.getResultItem().getCart());
                            cartListAdapter.notifyDataSetChanged();

                            loading = true;
                        });
                        for (CartModel itemModel : cartMainModel.getResultItem().getCart()) {
                            itemModel.setCarId(cartMainModel.getResultItem().getId());
                        }
                        DirectSDK.getInstance().cartRepository.addToCart(cartMainModel.getResultItem().getCart());
                    } else {
                        loading = false;
                    }
                }
            } else {
                Utils.setToast(this, cartMainModel.getErrorMessage());
                mBinding.rvCartItem.setVisibility(View.GONE);
                mBinding.blankBasket.setVisibility(View.VISIBLE);
                mBinding.rlCheckOut.setVisibility(View.GONE);
                DirectSDK.getInstance().cartRepository.truncateCart();
            }
        });
    }

    private void showClearCartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        builder.setTitle(DirectSDK.getInstance().dbHelper.getString(R.string.clear_cart));
        builder.setMessage(DirectSDK.getInstance().dbHelper.getString(R.string.are_you_sure_clear_cart));

        builder.setPositiveButton("Yes", (dialog, which) -> {
            clearCart();
        });
        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    private void addItemInCart(int QTY, CartModel sellerProductModel) {
        DirectSDK.getInstance().cartRepository.updateCartItem(sellerProductModel);
        ItemAddModel paginationModel = new ItemAddModel(QTY, "123", sellerProductModel.getId(), 0, 0, SharePrefs.getInstance(this).getString(SharePrefs.MALL_ID));
        cartItemViewMode.getAddItemsInCardVMRequest(paginationModel);
        cartItemViewMode.getAddItemsInCardVM().observe(this, sellerProdList -> {
            Utils.hideProgressDialog();
            if (sellerProdList != null) {

            }
        });
    }

    public void removeItemAlertDialog(CartModel cartModel, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dbHelper.getString(R.string.alert));
        builder.setMessage(dbHelper.getString(R.string.are_you_sure_you_want_to_delete));
        builder.setPositiveButton(dbHelper.getString(R.string.yes), (dialog, which) -> removeItemFromCart(cartModel, position));
        builder.setNegativeButton(dbHelper.getString(R.string.no), (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }

    private void removeItemFromCart(CartModel cartModel, int position) {
        commonClassForAPI.deleteCartItems(new DisposableObserver<CartMainModel>() {
            @Override
            public void onNext(@NotNull CartMainModel cartMainModel) {
                Utils.hideProgressDialog();
                try {
                    if (cartMainModel != null && cartMainModel.isSuccess()) {
                        DirectSDK.getInstance().cartRepository.deleteCartItem(cartModel);
                        cartItemList.remove(position);
                        cartListAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (cartItemList != null && cartItemList.isEmpty()) {
                    mBinding.rlCheckOut.setVisibility(View.GONE);
                    mBinding.blankBasket.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onError(@NotNull Throwable e) {
                e.printStackTrace();
                Utils.hideProgressDialog();
            }

            @Override
            public void onComplete() {
                Utils.hideProgressDialog();
            }
        }, cartModel.getId());
    }

    private void clearCart() {
        Utils.showProgressDialog(this);
        String cartId = DirectSDK.getInstance().cartRepository.getCartId();
        cartItemViewMode.clearCartItemVMRequest(cartId);
        cartItemViewMode.getClearCartItemVM().observe(this, object -> {
            DirectSDK.getInstance().cartRepository.truncateCart();
            Utils.hideProgressDialog();
            cartItemList.clear();
            cartListAdapter.notifyDataSetChanged();
            mBinding.rlCheckOut.setVisibility(View.GONE);
            mBinding.blankBasket.setVisibility(View.VISIBLE);
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        });
    }
}