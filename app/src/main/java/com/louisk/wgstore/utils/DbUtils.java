package com.louisk.wgstore.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import com.louisk.wgstore.entity.CouponBean;
import com.louisk.wgstore.entity.DiscountBean;
import com.louisk.wgstore.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite数据库操作工具类
 */

public class DbUtils {

    private static final String DATEBASE_NAME = "wgStore_db";
    private static final int DATABASE_VERSION = 1;

    public static String GoodsInfoTable = "GoodsInfoTable";//商品详情表
    public static String DiscountTable = "DiscountTable";//折扣单表
    public static String CouponTable = "CouponTable";//优惠券表

    private static DbUtils dbUtils;
    private  SQLiteDatabase mDatabase;

    private DbUtils(){}

    public static DbUtils getInstance(){
        if(dbUtils == null){
            dbUtils = new DbUtils();
        }
        return dbUtils;
    }

    //初始化数据库
    public synchronized void openDb(Context context) throws Exception {
        DataBaseOpenHelper openHelper = new DataBaseOpenHelper(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            openHelper.setWriteAheadLoggingEnabled(true);
        }

        this.mDatabase = openHelper.getWritableDatabase();
    }

    public SQLiteDatabase getSQLiteDatabase(){
        return mDatabase;
    }

    public boolean isOpen() {
        if (mDatabase == null)
            return false;
        return mDatabase.isOpen();
    }

    public synchronized void close() {
        if (mDatabase != null && mDatabase.isOpen()) {
            this.mDatabase.close();
            this.mDatabase = null;
        }
    }

    private static class DataBaseOpenHelper  extends SQLiteOpenHelper{

        public DataBaseOpenHelper (Context context) {
            super(context, DATEBASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + GoodsInfoTable
                    + "(G_type TEXT, G_Name TEXT, G_price REAL, G_num INTEGER, primary key(G_Name))");
            db.execSQL("CREATE TABLE " + DiscountTable
                    + "(D_type TEXT, D_extent REAL, D_deadLine TEXT, D_addTime TEXT, primary key(D_addTime))");
            db.execSQL("CREATE TABLE " + CouponTable
                    + "(C_totalMoney INTEGER, C_freeMoney INTEGER, C_deadLine TEXT, C_addTime TEXT, primary key(C_addTime))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


    /*===========================================================================================================
    *
    * 以下是DB操作，目前无删除操作
    *
    * */

    //添加商品到数据库
    public long addGoodsToDb(String goodsType, String goodsName, double goodsPrice){
//        String sql = "replace into " + GoodsInfoTable
//                + "(G_type, G_Name, G_price, G_num)" + " values (?,?,?,?)";
//        try{
//            getSQLiteDatabase().execSQL(sql, new Object[]{goodsType, goodsName, goodsPrice, 1});
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        ContentValues values = new ContentValues();
        values.put("G_type", goodsType);
        values.put("G_Name", goodsName);
        values.put("G_price", goodsPrice);
        values.put("G_num", 1);

        long ret = getSQLiteDatabase().insert(GoodsInfoTable, null, values);

        return ret;
    }


    //更改同一商品的数量
    public long updateGoodsNum(int goodsNum, String goodsName){
//        String update_sql = "update GoodsInfoTable set G_num = ? where G_Name = ? ";
//        try{
//            getSQLiteDatabase().execSQL(update_sql, new Object[]{goodsNum, goodsName});
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        ContentValues values = new ContentValues();
        values.put("G_num", goodsNum);

        long ret = getSQLiteDatabase().update(GoodsInfoTable, values, "G_Name = ?", new String[]{goodsName});
        return ret;
    }


    //获取购物车商品
    public List<GoodsBean> getAllGoodsInShoppingCart() {
        List<GoodsBean> goodsBeanList = new ArrayList<GoodsBean>();
        String sql = "select * from  " + GoodsInfoTable;
        Cursor cursor = null;
        GoodsBean goodsBean = null;
        try {
            cursor = getSQLiteDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                goodsBean = new GoodsBean("", "", 0.0, 0);
                goodsBean.setGoodsType(cursor.getString(cursor.getColumnIndex("G_type")));
                goodsBean.setGoodsName(cursor.getString(cursor.getColumnIndex("G_Name")));
                goodsBean.setGoodsPrice(cursor.getDouble(cursor.getColumnIndex("G_price")));
                goodsBean.setGoodsNum(cursor.getInt(cursor.getColumnIndex("G_num")));
                goodsBeanList.add(goodsBean);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return goodsBeanList;
    }


    //添加折扣券
    public long addDiscountToDb(String goodsType, double discountExtent, String discountDeadLine, String discountAddTime){
//        String sql = "insert into " + DiscountTable
//                + "(D_type, D_extent, D_deadLine, D_addTime)" + " values (?,?,?,?)";
//
//        try{
//            getSQLiteDatabase().execSQL(sql, new Object[]{goodsType, discountExtent, discountDeadLine, discountAddTime});
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        ContentValues values = new ContentValues();
        values.put("D_type", goodsType);
        values.put("D_extent", discountExtent);
        values.put("D_deadLine", discountDeadLine);
        values.put("D_addTime", discountAddTime);

        long ret = getSQLiteDatabase().insert(DiscountTable, null, values);

        return ret;

    }

    //添加优惠券
    public long addCouponToDb(int totalMoney, int freeMoney, String couponDeadLine, String couponAddTime){
//        String sql = "insert into " + CouponTable
//                + "(C_totalMoney, C_freeMoney, C_deadLine, C_addTime)" + " values (?,?,?,?)";
//        try{
//            getSQLiteDatabase().execSQL(sql, new Object[]{totalMoney, freeMoney, couponDeadLine, couponAddTime});
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        ContentValues values = new ContentValues();
        values.put("C_totalMoney", totalMoney);
        values.put("C_freeMoney", freeMoney);
        values.put("C_deadLine", couponDeadLine);
        values.put("C_addTime", couponAddTime);

        long ret = getSQLiteDatabase().insert(CouponTable, null, values);

        return ret;
    }

    //获取折扣券
    public List<DiscountBean> getDiscountBeanList(){
        List<DiscountBean> discountBeanList = new ArrayList<DiscountBean>();
        String sql = "select * from  " + DiscountTable;
        Cursor cursor = null;
        DiscountBean discountBean = null;
        try {
            cursor = getSQLiteDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                discountBean = new DiscountBean("", 0.0, "");
                discountBean.setDiscountType(cursor.getString(cursor.getColumnIndex("D_type")));
                discountBean.setDiscountExtent(cursor.getDouble(cursor.getColumnIndex("D_extent")));
                discountBean.setDiscountDeadLine(cursor.getString(cursor.getColumnIndex("D_deadLine")));
                discountBean.setDiscountAddTime(cursor.getString(cursor.getColumnIndex("D_addTime")));
                discountBeanList.add(discountBean);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return discountBeanList;
    }

    //获取优惠券
    public List<CouponBean> getCouponBeanList(){
        List<CouponBean> couponBeanList = new ArrayList<CouponBean>();
        String sql = "select * from  " + CouponTable;
        Cursor cursor = null;
        CouponBean couponBean = null;
        try {
            cursor = getSQLiteDatabase().rawQuery(sql, null);
            while (cursor.moveToNext()) {
                couponBean = new CouponBean(0, 0, "");
                couponBean.setTotalMoney(cursor.getInt(cursor.getColumnIndex("C_totalMoney")));
                couponBean.setFreeMoney(cursor.getInt(cursor.getColumnIndex("C_freeMoney")));
                couponBean.setCouponBeanDeadLine(cursor.getString(cursor.getColumnIndex("C_deadLine")));
                couponBean.setCouponAddTime(cursor.getString(cursor.getColumnIndex("C_addTime")));
                couponBeanList.add(couponBean);
            }
        } catch (Exception e) {
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        return couponBeanList;
    }
}


