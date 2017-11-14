package com.louisk.wgstore.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.louisk.wgstore.R;
import com.louisk.wgstore.base.PresenterActivity;
import com.louisk.wgstore.presenter.Presenter;

import butterknife.BindView;

/**
 * 用户输入界面（减免金额等）
 */

public class UserWriteActivity extends PresenterActivity {

    @BindView(R.id.btn_back)
    ImageView btnBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_content)
    EditText editContent;

    private Intent mIntent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_write;
    }

    @Override
    protected Presenter createPresenter() {
        return null;
    }

    @Override
    public void initView() {
        tvTitle.setText("用户输入");
        setOnViewClick(btnBack);
    }

    @Override
    protected void onViewClick(View v) {
        super.onViewClick(v);
        switch (v.getId()) {
            case R.id.btn_back:
                deliverContentAndFinish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            deliverContentAndFinish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void deliverContentAndFinish(){
        mIntent = new Intent();
        mIntent.putExtra("content", editContent.getText().toString().trim());
        setResult(RESULT_OK, mIntent);
        finish();
    }
}
