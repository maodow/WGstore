package com.louisk.wgstore.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.louisk.wgstore.R;
import com.louisk.wgstore.base.PresenterActivity;
import com.louisk.wgstore.presenter.PromotionPresenter;
import com.louisk.wgstore.view.PromotionView;
import com.louisk.wgstore.view.WheelView;

import java.util.Arrays;
import java.util.Calendar;

import butterknife.BindView;

/**
 * 添加折扣
 */

public class AddDiscountActivity extends PresenterActivity<PromotionPresenter> implements PromotionView {
    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_discount_type)
    TextView tvDiscountType;
    @BindView(R.id.rl_discount_type)
    RelativeLayout rlDiscountType;
    @BindView(R.id.tv_discount_extent)
    TextView tvDiscountExtent;
    @BindView(R.id.tv_discount_deadline)
    TextView tvDiscountDeadline;
    @BindView(R.id.rl_discount_deadline)
    RelativeLayout rlDiscountDeadline;
    @BindView(R.id.rl_discount_extent)
    RelativeLayout rlDiscountExtent;
    private String selectedItem;

    private DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private String content;
    private static final int REQUEST_CODE_DISCOUNT_EXTENT = 2;
    private static final String[] PLANETS = new String[]{"电子类", "食品类", "日用品类", "酒类"};

    private PromotionPresenter presenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_discount;
    }

    @Override
    protected PromotionPresenter createPresenter() {
        presenter = new PromotionPresenter(this, this);
        return presenter;
    }

    @Override
    public void initView() {
        tvTitle.setText("添加折扣");
        initDateDialog();
        setOnViewClick(btnBack);
        setOnViewClick(rlDiscountType);
        setOnViewClick(rlDiscountExtent);
        setOnViewClick(rlDiscountDeadline);
    }

    @Override
    protected void onViewClick(View v) {
        super.onViewClick(v);
        switch (v.getId()) {
            case R.id.btn_back:
                addDiscountToDb();
                break;
            case R.id.rl_discount_type:
                showGoodsTypeDialog();
                break;
            case R.id.rl_discount_extent:
                Intent intent_couponTotal = new Intent(AddDiscountActivity.this, UserWriteActivity.class);
                startActivityForResult(intent_couponTotal, REQUEST_CODE_DISCOUNT_EXTENT);
                break;
            case R.id.rl_discount_deadline:
                datePickerDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        content = data.getStringExtra("content");
        switch (requestCode) {
            case REQUEST_CODE_DISCOUNT_EXTENT:
                tvDiscountExtent.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(content)){
                    tvDiscountExtent.setText("");
                }else{
                    tvDiscountExtent.setText(content+"折");
                }
                break;
            default:
                break;
        }
    }


    private void addDiscountToDb(){
        if(isNotEmpty()){
            if(!checkInput()){
                return;
            }

            getPresenter().addPromotToDB(tvDiscountType.getText().toString().trim()
                    , Double.valueOf(content)
                    , tvDiscountDeadline.getText().toString().trim()
                    , String.valueOf(System.currentTimeMillis()));

        }else{//用户不添加，直接退出
            finish();
        }
    }

    private boolean checkInput(){

        if(Integer.valueOf(content) >= 10){
            showToast("打折额度不合法~");
            return false;
        }

        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        int thisMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        int today = Calendar.getInstance().get(Calendar.DATE);

        if(mYear < thisYear){
            showToast("请选择正确的截止时间~");
            return false;
        }else if(mYear == thisYear){
            if(mMonth + 1 < thisMonth){
                showToast("请选择正确的截止时间~");
                return false;
            }else if(mMonth + 1 == thisMonth){
                if(mDay < today){
                    showToast("请选择正确的截止时间~");
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void addPromotCallback(long resultCode) {//添加购物券的回调
        if(resultCode != -1){//数据库添加成功
            finish();
        }else{
            showToast("添加失败！");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            addDiscountToDb();
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void onNoNetwork() {

    }

    private boolean isNotEmpty(){
        if(!TextUtils.isEmpty(tvDiscountExtent.getText().toString().trim())
                && !TextUtils.isEmpty(tvDiscountType.getText().toString().trim())
                && !TextUtils.isEmpty(tvDiscountDeadline.getText().toString().trim())){
            return true;
        }
        return false;
    }

    private void initDateDialog() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);//滚轮的坑：如果滚轮要显示当前月份，不需要+1（此处跟calendar获取不同）
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
    }

    /**
     * 设置截止日期 利用StringBuffer追加
     */
    public void display() {
        tvDiscountDeadline.setVisibility(View.VISIBLE);
        tvDiscountDeadline.setText(new StringBuffer().append(mYear).append("/").append(mMonth+1).append("/").append(mDay));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };

    private void showGoodsTypeDialog(){
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView wheelView = outerView.findViewById(R.id.wheel_view_wv);
        wheelView.setOffset(2);
        wheelView.setItems(Arrays.asList(PLANETS));
        wheelView.setSeletion(2);
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                selectedItem = item;
            }
        });

        new AlertDialog.Builder(this)
                .setTitle("请选择商品类型")
                .setView(outerView)
                .setPositiveButton("确定", (dialog, which) -> displayGoodsType()).show();
    }

    private void displayGoodsType(){
        tvDiscountType.setVisibility(View.VISIBLE);
        tvDiscountType.setText(selectedItem == null ? "日用品类" : selectedItem);
    }
}
