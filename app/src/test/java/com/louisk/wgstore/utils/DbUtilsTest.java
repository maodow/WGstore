package com.louisk.wgstore.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 2017/11/8.
 *
 */
public class DbUtilsTest {

    private DbUtils dbUtils;

    @Before
    public void setUp() throws Exception {
        dbUtils = DbUtils.getInstance();
//        dbUtils.openDb();
    }

    // 测试完成后执行
    @After
    public void tearDown() throws Exception {
    }

    // 测试方法
    @Test
    public void addGoodsToDb() throws Exception {
        long resultCode = dbUtils.addGoodsToDb("日用品类", "雨伞", 23);
        assertEquals(-1, resultCode);
    }

    // 测试方法
    @Test
    public void addDiscountToDb() throws Exception {
        long resultCode = dbUtils.addDiscountToDb("酒类", 7,"2019/10/13", "0000");
        assertEquals(-1, resultCode);
    }

}