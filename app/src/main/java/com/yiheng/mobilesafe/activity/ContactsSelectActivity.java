package com.yiheng.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.bean.ContactsInfo;
import com.yiheng.mobilesafe.utils.ContactsUtils;

import java.util.ArrayList;

public class ContactsSelectActivity extends AppCompatActivity {

    private ListView lv_contacts_select;
    private ArrayList<ContactsInfo> mInfos;
    private LinearLayout ll_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_select);
        lv_contacts_select = (ListView) findViewById(R.id.lv_contacts_select);
        ll_loading = (LinearLayout) findViewById(R.id.ll_loading);

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                mInfos = ContactsUtils.getContactsInfos(getApplicationContext());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        lv_contacts_select.setAdapter(new MyAdaptor());
                        ll_loading.setVisibility(View.INVISIBLE);

                    }
                });
            }
        }.start();
        lv_contacts_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String num = mInfos.get(position).num;
                Intent intent = new Intent();
                intent.putExtra("num",num);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }

    private class MyAdaptor extends BaseAdapter {
        @Override
        public int getCount() {
            return mInfos.size();
        }

        @Override
        public ContactsInfo getItem(int position) {
            ContactsInfo contactsInfo = mInfos.get(position);
            return contactsInfo;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(), R.layout.item_contacts_select, null);
                viewHolder = new ViewHolder();
                viewHolder.ivIcon =
                        (ImageView) convertView.findViewById(R.id.iv_contacts_select_item_icon);
                viewHolder.tvName =
                        (TextView) convertView.findViewById(R.id.tv_contacts_select_item_name);
                viewHolder.tvNum =
                        (TextView) convertView.findViewById(R.id.tv_contacts_select_item_num);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            int id = getItem(position).id;
            Bitmap contactIcon = ContactsUtils.getContactIcon(getApplicationContext(), id);
            if (null!=contactIcon) {
                viewHolder.ivIcon.setImageBitmap(contactIcon);
            } else {
                viewHolder.ivIcon.setImageResource(R.mipmap.ic_launcher);
            }
            String name = getItem(position).name;
            viewHolder.tvName.setText(name);

            String num = getItem(position).num;
            viewHolder.tvNum.setText(num);

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView ivIcon;
        TextView tvName;
        TextView tvNum;

    }
}
