package com.louisk.wgstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.louisk.wgstore.R;
import com.louisk.wgstore.entity.DiscountBean;

import java.util.ArrayList;
import java.util.List;


public class DiscountListAdapter extends RecyclerView.Adapter<DiscountListAdapter.MyViewHolder> {
    private List<DiscountBean> discountBeanList = new ArrayList<>();
    private Context mContext;
    private RecyclerView recyclerView;


    public DiscountListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setDiscountBeanList(List list){
        if(!discountBeanList.isEmpty())
        this.discountBeanList.clear();
        this.discountBeanList.addAll(list);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_discount, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        DiscountBean discountBean = discountBeanList.get(position);
        holder.tv_discount_type.setText(discountBean.getDiscountType());
        holder.tv_discount_deadline.setText(discountBean.getDiscountDeadLine());
        holder.iv_discount_count.setText(discountBean.getDiscountExtent()+"折");
    }

    @Override
    public int getItemCount() {
        return discountBeanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv_discount_type, tv_discount_deadline, iv_discount_count;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_discount_type = (TextView) itemView.findViewById(R.id.tv_discount_type);
            tv_discount_deadline = (TextView) itemView.findViewById(R.id.tv_discount_deadline);
            iv_discount_count = (TextView) itemView.findViewById(R.id.iv_discount_count);
        }
    }
}
