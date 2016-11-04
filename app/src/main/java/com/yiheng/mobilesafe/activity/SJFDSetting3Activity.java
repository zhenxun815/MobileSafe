package com.yiheng.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.utils.ConstantUtils;
import com.yiheng.mobilesafe.utils.SharedPreferenceUtils;

public class SJFDSetting3Activity extends SJFDBaseActivity {

    private EditText et_sjfd_setting3_safenumber;
    private Button btn_sjfd_setting3_select_safenum;
    private final static int GET_SAFE_NUM = 3001;
    private Button btn_next;
    private Button btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sjfdsetting3);
        et_sjfd_setting3_safenumber = (EditText) findViewById(R.id.et_sjfd_setting3_safenumber);

        String safenum = SharedPreferenceUtils
                .getString(getApplicationContext(), ConstantUtils.SJFD_SAFENUM, "");

        if (!TextUtils.isEmpty(safenum)) {
            et_sjfd_setting3_safenumber.setText(safenum);
            et_sjfd_setting3_safenumber.setSelection(safenum.length());
        }

        btn_sjfd_setting3_select_safenum = (Button) findViewById(R.id.btn_sjfd_setting3_select_safenum);
        btn_sjfd_setting3_select_safenum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ContactsSelectActivity.class);
                startActivityForResult(intent, GET_SAFE_NUM);

            }
        });
        btn_next = (Button) findViewById(R.id.btn_sjfd_setting3_next);
        btn_pre = (Button) findViewById(R.id.btn_sjfd_setting3_pre);

        btn_next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                nextClick(v);
            }
        });
        btn_pre.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                preClick(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (GET_SAFE_NUM==requestCode){
            if (Activity.RESULT_OK == resultCode){
                String safenum = data.getStringExtra("num");
                et_sjfd_setting3_safenumber.setText(safenum);
                et_sjfd_setting3_safenumber.setSelection(safenum.length());
            }
        }

    }

    @Override
    protected boolean toPrePage() {
        startActivity(new Intent(getApplicationContext(), SJFDSetting2Activity.class));
        return true;
    }

    @Override
    protected boolean toNextPage() {
        String safenum = et_sjfd_setting3_safenumber.getText().toString().trim();
        if (TextUtils.isEmpty(safenum)) {
            Toast.makeText(getApplicationContext(), "必须设定安全号码", Toast.LENGTH_LONG).show();
            return false;
        } else {
            SharedPreferenceUtils
                    .putString(getApplicationContext(), ConstantUtils.SJFD_SAFENUM, safenum);
            startActivity(new Intent(getApplicationContext(), SJFDSetting4Activity.class));
            return true;
        }

    }

}
