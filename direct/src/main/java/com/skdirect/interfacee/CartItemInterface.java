package com.skdirect.interfacee;

import com.skdirect.model.CartModel;

public interface CartItemInterface {
    void plusButtonOnClick(CartModel sellerProductModel);

    void minusButtonOnClick(CartModel sellerProductModel, int position);

    void removeButtonOnClick(CartModel cartModel, int position);
}