package com.louisk.wgstore.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.louisk.wgstore.R;
import com.louisk.wgstore.entity.GoodsBean;

import java.util.ArrayList;
import java.util.List;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {

    private List<GoodsBean> goodsBeanList = new ArrayList<>();
    private Context mContext;
    private RecyclerView recyclerView;

    private ItemClickListener itemClickListener;

    public GoodsListAdapter(Context mContext, ItemClickListener listener) {
        this.mContext = mContext;
        this.itemClickListener = listener;
    }

    public void setGoodsBeanList(List list) {
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        GoodsBean info = goodsBeanList.get(position);
        holder.tv_goods_name.setText(info.getGoodsName());
        holder.tv_goods_price.setText(String.valueOf(info.getGoodsPrice()));

        //自定义控件点击
        holder.iv_add_goods.setOnClickListener(v ->{itemClickListener.onItemClick(holder.itemView, position);});

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
        TextView tv_goods_name, tv_goods_price;
        ImageView iv_add_goods;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_good_name);
            tv_goods_price = (TextView) itemView.findViewById(R.id.tv_good_price);
            iv_add_goods = (ImageView) itemView.findViewById(R.id.iv_add);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View v, Object object);
    }
}
