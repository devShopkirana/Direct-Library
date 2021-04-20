package com.skdirect.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.skdirect.model.CartModel;

@Database(entities = {CartModel.class}, version = 19, exportSchema = false)
public abstract class CartDatabase extends RoomDatabase {

    public abstract CartDaoAccess cartDaoAccess();
}