package com.louisk.wgstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.louisk.wgstore.R;
import com.louisk.wgstore.entity.CouponBean;

import java.util.ArrayList;
import java.util.List;

public class CartCouponAdapter extends RecyclerView.Adapter<CartCouponAdapter.MyViewHolder> {

    private List<CouponBean> couponBeanList = new ArrayList<>();
    private Context mContext;
    private RecyclerView recyclerView;


    public CartCouponAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCouponBeanList(List list) {
        if (!couponBeanList.isEmpty())
            this.couponBeanList.clear();
        this.couponBeanList.addAll(list);
        this.mNotify();
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    private void mNotify() {
        if (recyclerView != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart_coupon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CouponBean couponBean = couponBeanList.get(position);
        holder.tv_cart_goods_coupon_extent.setText("满"+couponBean.getTotalMoney()+"减"+couponBean.getFreeMoney());
        holder.tv_cart_good_coupon_deadline.setText(couponBean.getCouponBeanDeadLine());
    }

    @Override
    public int getItemCount() {
        return couponBeanList == null ? 0 : couponBeanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cart_goods_coupon_extent, tv_cart_good_coupon_deadline;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_cart_goods_coupon_extent = (TextView) itemView.findViewById(R.id.tv_cart_goods_coupon_extent);
            tv_cart_good_coupon_deadline = (TextView) itemView.findViewById(R.id.tv_cart_good_coupon_deadline);
        }
    }

}
