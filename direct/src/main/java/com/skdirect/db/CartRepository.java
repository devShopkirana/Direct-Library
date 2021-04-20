package com.skdirect.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Room;

import com.skdirect.model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartRepository {
    private final String DB_NAME = "sk_central";

    private final CartDatabase cartDatabase;


    public CartRepository(Context context) {
        cartDatabase = Room.databaseBuilder(context, CartDatabase.class, DB_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }


    /*Cart operations*/

    public void addToCart(CartModel model) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.cartDaoAccess().insertTask(model);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void addToCart(final ArrayList<CartModel> list) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.cartDaoAccess().truncateCart();
                cartDatabase.cartDaoAccess().insertTask(list);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void updateCartItem(final CartModel model) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.cartDaoAccess().updateTask(model);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void deleteCartItem(final int id) {
        final LiveData<CartModel> task = getCartItem(id);
        if (task != null) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    cartDatabase.cartDaoAccess().deleteTask(task.getValue());
                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    public void deleteCartItem(final CartModel model) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.cartDaoAccess().deleteTask(model);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void deleteCartItem1(final int id) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                cartDatabase.cartDaoAccess().deleteItem(id);
                return null;
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public CartModel getItem(int id) {
        return cartDatabase.cartDaoAccess().getItem(id);
    }

    public LiveData<CartModel> getCartItem(int id) {
        return cartDatabase.cartDaoAccess().getCartItem(id);
    }

    public LiveData<List<CartModel>> getCart() {
        return cartDatabase.cartDaoAccess().fetchAllTasks();
    }

    public boolean isItemInCart(int id) {
        return cartDatabase.cartDaoAccess().isItemExist(id);
    }

    public Integer getItemQty(int id) {
        return cartDatabase.cartDaoAccess().getItemQty(id);
    }

    public Integer getCartSellerId() {
        return cartDatabase.cartDaoAccess().getCartSellerId();
    }

    public LiveData<Double> getCartValue() {
        return cartDatabase.cartDaoAccess().getCartValue();
    }

    public Double getCartValue1() {
        return cartDatabase.cartDaoAccess().getCartValue1();
    }

    public int getCartQtyCount() {
        return cartDatabase.cartDaoAccess().getCartQtyCount();
    }

    public LiveData<Integer> getCartCount() {
        return cartDatabase.cartDaoAccess().getCartCount();
    }

    public int getCartCount1() {
        return cartDatabase.cartDaoAccess().getCartCount1();
    }

    public void truncateCart() {
        cartDatabase.cartDaoAccess().truncateCart();
    }

    public String getCartId() {
        return cartDatabase.cartDaoAccess().getCartId();
    }

    public void updateCartId(String cartId) {
        cartDatabase.cartDaoAccess().updateCartId(cartId);
    }
}