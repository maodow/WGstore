package com.louisk.wgstore.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.KeyEvent;
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

import java.util.Calendar;

import butterknife.BindView;

/**
 * 添加优惠券
 */

public class AddCouponActivity extends PresenterActivity<PromotionPresenter> implements PromotionView {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_coupon_total)
    RelativeLayout rlCouponTotal;
    @BindView(R.id.rl_coupon_extent)
    RelativeLayout rlCouponExtent;
    @BindView(R.id.rl_coupon_deadline)
    RelativeLayout rlCouponDeadline;
    @BindView(R.id.tv_coupon_total)
    TextView tvCouponTotal;
    @BindView(R.id.tv_coupon_extent)
    TextView tvCouponExtent;
    @BindView(R.id.tv_coupon_deadline)
    TextView tvCouponDeadline;

    private DatePickerDialog datePickerDialog;
    private int mYear, mMonth, mDay;
    private static final int REQUEST_CODE_TOTAL = 0;
    private static final int REQUEST_CODE_EXTENT = 1;

    private PromotionPresenter presenter;
    private int totalMoney ,extentMoney;
    private String content;


    @Override
    public int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected PromotionPresenter createPresenter() {
        presenter = new PromotionPresenter(this, this);
        return presenter;
    }

    @Override
    public void initView() {
        tvTitle.setText("添加优惠券");
        initDateDialog();
        setOnViewClick(btnBack);
        setOnViewClick(rlCouponTotal);
        setOnViewClick(rlCouponExtent);
        setOnViewClick(rlCouponDeadline);
    }

    @Override
    protected void onViewClick(View v) {
        super.onViewClick(v);
        Intent intent_couponTotal = new Intent(AddCouponActivity.this, UserWriteActivity.class);
        switch (v.getId()) {
            case R.id.rl_coupon_total:
                startActivityForResult(intent_couponTotal, REQUEST_CODE_TOTAL);
                break;
            case R.id.rl_coupon_extent:
                startActivityForResult(intent_couponTotal, REQUEST_CODE_EXTENT);
                break;
            case R.id.rl_coupon_deadline:
                datePickerDialog.show();
                break;
            case R.id.btn_back:
                addCouponToDb();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        content = data.getStringExtra("content");
        if(TextUtils.isEmpty(content)){
            content = "";
        }
        switch (requestCode) {
            case REQUEST_CODE_TOTAL:
                tvCouponTotal.setVisibility(View.VISIBLE);
                tvCouponTotal.setText(content);
                break;
            case REQUEST_CODE_EXTENT:
                tvCouponExtent.setVisibility(View.VISIBLE);
                tvCouponExtent.setText(content);
                break;
            default:
                break;
        }
    }

    private void addCouponToDb(){
        if (isNotEmpty()) {
            if(!checkInput()){
                return;
            }
            getPresenter().addCouponToDB(totalMoney, extentMoney
                    , tvCouponDeadline.getText().toString().trim()
                    , String.valueOf(System.currentTimeMillis()));

        } else {//用户不添加，直接退出
            finish();
        }
    }

    private boolean checkInput(){
        totalMoney = Integer.valueOf(tvCouponTotal.getText().toString().trim());
        extentMoney = Integer.valueOf(tvCouponExtent.getText().toString().trim());

        if(extentMoney > totalMoney){
            showToast("减免金额不能大于商品总价~");
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
    public void addPromotCallback(long resultCode) {
        if (resultCode != -1) {//数据库添加成功
            finish();
        } else {
            showToast("添加失败！");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            addCouponToDb();
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
        if(!TextUtils.isEmpty(tvCouponTotal.getText().toString().trim())
                && !TextUtils.isEmpty(tvCouponDeadline.getText().toString().trim())
                && !TextUtils.isEmpty(tvCouponExtent.getText().toString().trim())){
            return true;
        }

        return false;
    }

    private void initDateDialog() {
        final Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
    }

    /**
     * 设置截止日期 利用StringBuffer追加
     */
    public void display() {
        tvCouponDeadline.setVisibility(View.VISIBLE);
        tvCouponDeadline.setText(new StringBuffer().append(mYear).append("/").append(mMonth+1).append("/").append(mDay));
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
}
