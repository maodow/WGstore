package com.louisk.wgstore.listener;

import com.louisk.wgstore.entity.DiscountBean;

import java.util.List;

/**
 * Created by admin on 2017/11/4.
 */

public interface RefreshDiscountListListener {
    void refreshDiscountList(List<DiscountBean> discountBeans);
}
