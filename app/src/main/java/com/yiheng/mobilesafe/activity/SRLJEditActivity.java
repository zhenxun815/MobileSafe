package com.yiheng.mobilesafe.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yiheng.mobilesafe.R;

public class SRLJEditActivity extends AppCompatActivity {
    private TextView tv_srlj_edit_title;
    private EditText et_srlj_edit_number;
    private RadioButton rbt_srlj_edit_number;
    private RadioButton rbt_srlj_edit_sms;
    private RadioButton rbt_srlj_edit_all;
    private Button btn_srlj_edit_confirm;
    private Button btn_srlj_edit_concel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srlj_edit);

        btn_srlj_edit_confirm = (Button) findViewById(R.id.btn_srlj_edit_confirm);
        btn_srlj_edit_concel = (Button) findViewById(R.id.btn_srlj_edit_concel);

        tv_srlj_edit_title = (TextView) findViewById(R.id.tv_srlj_edit_title);
        et_srlj_edit_number = (EditText) findViewById(R.id.et_srlj_edit_number);
        rbt_srlj_edit_number = (RadioButton) findViewById(R.id.rbt_srlj_edit_number);
        rbt_srlj_edit_sms = (RadioButton) findViewById(R.id.rbt_srlj_edit_sms);
        rbt_srlj_edit_all = (RadioButton) findViewById(R.id.rbt_srlj_edit_all);
    }
}
