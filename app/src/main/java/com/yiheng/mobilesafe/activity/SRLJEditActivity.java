package com.yiheng.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.bean.ForbiddenInfo;
import com.yiheng.mobilesafe.db.ForbiddenDAO;
import com.yiheng.mobilesafe.utils.ConstantUtils;

public class SRLJEditActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_srlj_edit_title;
    private EditText et_srlj_edit_number;
    private RadioGroup rg_srlj_type;
    private RadioButton rbt_srlj_edit_number;
    private RadioButton rbt_srlj_edit_sms;
    private RadioButton rbt_srlj_edit_all;
    private Button btn_srlj_edit_confirm;
    private Button btn_srlj_edit_concel;


    private boolean isEdit;
    private Intent intent;
    private int position;
    private ForbiddenDAO forbiddenDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srlj_edit);

        btn_srlj_edit_confirm = (Button) findViewById(R.id.btn_srlj_edit_confirm);
        btn_srlj_edit_confirm.setOnClickListener(this);
        btn_srlj_edit_concel = (Button) findViewById(R.id.btn_srlj_edit_concel);
        btn_srlj_edit_concel.setOnClickListener(this);

        tv_srlj_edit_title = (TextView) findViewById(R.id.tv_srlj_edit_title);
        et_srlj_edit_number = (EditText) findViewById(R.id.et_srlj_edit_number);
        rg_srlj_type = (RadioGroup) findViewById(R.id.rg_srlj_type);
        rbt_srlj_edit_number = (RadioButton) findViewById(R.id.rbt_srlj_edit_number);
        rbt_srlj_edit_sms = (RadioButton) findViewById(R.id.rbt_srlj_edit_sms);
        rbt_srlj_edit_all = (RadioButton) findViewById(R.id.rbt_srlj_edit_all);

        forbiddenDAO = new ForbiddenDAO(getApplicationContext());
        intent = getIntent();
        position = intent.getIntExtra(ConstantUtils.EXTR_POSITION, 0);
        String action = intent.getAction();

        if (TextUtils.equals(action, "add")) {
            isEdit = false;

            tv_srlj_edit_title.setText("添加黑名单");
            btn_srlj_edit_confirm.setText("保存");
            et_srlj_edit_number.setEnabled(true);

        } else if (TextUtils.equals(action, "edit")) {
            isEdit = true;

            tv_srlj_edit_title.setText("更新黑名单");
            btn_srlj_edit_confirm.setText("更新");
            et_srlj_edit_number.setEnabled(false);

            String number = intent.getStringExtra(ConstantUtils.EXTRA_NUMBER);
            et_srlj_edit_number.setText(number);

            int type = intent.getIntExtra(ConstantUtils.EXTRA_TYPE, 0);
            switch (type) {
                case ForbiddenInfo.TYPE_CALL:
                    rbt_srlj_edit_number.setChecked(true);
                    break;
                case ForbiddenInfo.TYPE_SMS:
                    rbt_srlj_edit_sms.setChecked(true);
                    break;
                case ForbiddenInfo.TYPE_ALL:
                    rbt_srlj_edit_all.setChecked(true);
                    break;
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_srlj_edit_confirm:

                String number = et_srlj_edit_number.getText().toString().trim();
                if (TextUtils.equals(number, "")) {
                    Toast.makeText(getApplicationContext(), "请填写要屏蔽的号码", Toast.LENGTH_LONG).show();
                    return;
                }

                int type = -1;
                int checkedRadioButtonId = rg_srlj_type.getCheckedRadioButtonId();
                if (-1 == checkedRadioButtonId) {
                    Toast.makeText(getApplicationContext(), "请选择屏蔽类型", Toast.LENGTH_LONG).show();
                    return;
                }
                switch (checkedRadioButtonId) {
                    case R.id.rbt_srlj_edit_number:
                        type = ForbiddenInfo.TYPE_CALL;
                        break;
                    case R.id.rbt_srlj_edit_sms:
                        type = ForbiddenInfo.TYPE_SMS;
                        break;
                    case R.id.rbt_srlj_edit_all:
                        type = ForbiddenInfo.TYPE_ALL;
                        break;
                }

                if (isEdit) {
                    boolean updateSuccess = forbiddenDAO.update(number, type);

                    if (updateSuccess) {
                        Intent data = new Intent();
                        data.putExtra(ConstantUtils.EXTRA_NUMBER, number);
                        data.putExtra(ConstantUtils.EXTRA_TYPE, type);
                        data.putExtra(ConstantUtils.EXTR_POSITION, position);
                        setResult(Activity.RESULT_OK, data);
                    } else {
                        Toast.makeText(getApplicationContext(), "更新失败", Toast.LENGTH_LONG).show();
                    }
                } else {
                    boolean insertSuccess = forbiddenDAO.insert(number, type);
                    if (insertSuccess) {
                        Intent data = new Intent();
                        data.putExtra(ConstantUtils.EXTRA_NUMBER, number);
                        data.putExtra(ConstantUtils.EXTRA_TYPE, type);
                        setResult(Activity.RESULT_OK, data);
                    } else {
                        Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_LONG).show();
                    }
                }
                finish();
                break;
            case R.id.btn_srlj_edit_concel:
                finish();
                break;
        }
    }
}
