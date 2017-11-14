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


public class CouponListAdapter extends RecyclerView.Adapter<CouponListAdapter.MyViewHolder> {
    private ArrayList<CouponBean> couponBeans = new ArrayList<>();
    private Context mContext;
    private RecyclerView recyclerView;

    public CouponListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setCouponBeanList(List list){
        if(!couponBeans.isEmpty())
        this.couponBeans.clear();
        this.couponBeans.addAll(list);
        this.mNotify();
    }

    public void setRecyclerView(RecyclerView recyclerView){
        this.recyclerView = recyclerView;
    }


    private void mNotify(){
        if(recyclerView != null){
           recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_coupon, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        CouponBean info = couponBeans.get(position);
        holder.tv_coupon.setText("满"+info.getTotalMoney()+"减"+info.getFreeMoney()+"优惠券");
    }

    @Override
    public int getItemCount() {
        return couponBeans == null ? 0 : couponBeans.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_coupon;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_coupon = (TextView) itemView.findViewById(R.id.tv_coupon);
        }
    }

}
