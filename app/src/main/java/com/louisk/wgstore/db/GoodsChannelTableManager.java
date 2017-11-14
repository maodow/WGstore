/*
 * Copyright (c) 2016 咖枯 <kaku201313@163.com | 3772304@qq.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.louisk.wgstore.db;

import com.louisk.wgstore.Constant;
import com.louisk.wgstore.WG_Application;
import com.louisk.wgstore.R;
import com.louisk.wgstore.entity.GoodsChannelBean;
import com.louisk.wgstore.entity.GoodsBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GoodsChannelTableManager {

    /**
     * 加载商品类型
     * @return
     */
    public static List<GoodsChannelBean> loadGoodsChannelsMine() {
        List<String> channelName = Arrays.asList(WG_Application.getAppContext().getResources().getStringArray(R.array.goods_channel_name));
        List<String> channelId = Arrays.asList(WG_Application.getAppContext().getResources().getStringArray(R.array.goods_channel_id));
        ArrayList<GoodsChannelBean> ChannelBeanList = new ArrayList<>();
        for (int i = 0; i < channelName.size(); i++) {
            GoodsChannelBean channelBean = new GoodsChannelBean(channelId.get(i), channelName.get(i));
            ChannelBeanList.add(channelBean);
        }
        return ChannelBeanList;
    }


    /**
     * 商品详情
     * @return
     */
    public static List<GoodsBean> loadGoodsInfo(String goodsType) {
        int res_name = 0;
        int res_price = 1;
        if(goodsType.equals(Constant.GOODS_TYPE_ELEC)){
            res_name = R.array.goods_etc_name;
            res_price = R.array.goods_etc_price;
        }else if(goodsType.equals(Constant.GOODS_TYPE_FOOD)){
            res_name = R.array.goods_food_name;
            res_price = R.array.goods_food_price;
        }else if(goodsType.equals(Constant.GOODS_TYPE_COMMODITY)){
            res_name = R.array.goods_commodity_name;
            res_price = R.array.goods_commodity_price;
        }else if(goodsType.equals(Constant.GOODS_TYPE_DRINK)){
            res_name = R.array.goods_drink_name;
            res_price = R.array.goods_drink_price;
        }

        List<String> goodsName = Arrays.asList(WG_Application.getAppContext().getResources().getStringArray(res_name));
        List<String> goodsPrice = Arrays.asList(WG_Application.getAppContext().getResources().getStringArray(res_price));
        ArrayList <GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < goodsName.size(); i++) {
            GoodsBean goodsBean = new GoodsBean(goodsType, goodsName.get(i), Double.valueOf(goodsPrice.get(i)));
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }

}
