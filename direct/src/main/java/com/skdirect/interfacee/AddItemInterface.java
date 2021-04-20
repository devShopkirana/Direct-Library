package com.skdirect.interfacee;

import android.widget.LinearLayout;
import android.widget.TextView;

import com.skdirect.model.SellerProductList;

public interface AddItemInterface {
    void plusButtonOnClick(SellerProductList sellerProductModel, TextView tvSelectedQty);

    void minusButtonOnClick(SellerProductList sellerProductModel, TextView selectedQty, TextView tvSelectedQty, LinearLayout LLPlusMinus);

    void addButtonOnClick(SellerProductList sellerProductModel, TextView selectedQty, TextView tvSelectedQty, LinearLayout LLPlusMinus);


}
