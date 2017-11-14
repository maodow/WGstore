package com.louisk.wgstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.louisk.wgstore.R;
import com.louisk.wgstore.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class CartGoodsListAdapter extends RecyclerView.Adapter<CartGoodsListAdapter.MyViewHolder> {

    private List<GoodsBean> goodsBeanList = new ArrayList<>();
    private Context mContext;
    private RecyclerView recyclerView;


    public CartGoodsListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setGoodsList(List list) {
        if (!goodsBeanList.isEmpty())
            this.goodsBeanList.clear();
        this.goodsBeanList.addAll(list);
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_cart_goods, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GoodsBean goodsBean = goodsBeanList.get(position);
        holder.tv_cart_goods_name.setText(goodsBean.getGoodsName());
        holder.tv_cart_goods_price.setText(String.valueOf(goodsBean.getGoodsPrice()));
        holder.edit_goods_count.setText(goodsBean.getGoodsNum()+"");
    }

    @Override
    public int getItemCount() {
        return goodsBeanList == null ? 0 : goodsBeanList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cart_goods_name, tv_cart_goods_price;
        EditText edit_goods_count;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_cart_goods_name = (TextView) itemView.findViewById(R.id.tv_cart_good_name);
            tv_cart_goods_price = (TextView) itemView.findViewById(R.id.tv_cart_good_price);
            edit_goods_count = (EditText) itemView.findViewById(R.id.edit_goods_count);
        }
    }

}
