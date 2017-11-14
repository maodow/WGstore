package com.louisk.wgstore.view;

import com.louisk.wgstore.entity.GoodsBean;

import java.util.List;

/**
 * Created by admin on 2017/11/6.
 *
 */

public interface HomeView extends IView {
    void onGetAllGoodsInDB(List<GoodsBean> goodsBeanList);
    void onAddGoodsToDB(long resultCode);
    void onUpdateGoodsNum(long resultCode);
}
