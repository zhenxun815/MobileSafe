package com.yiheng.mobilesafe.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yiheng.mobilesafe.R;
import com.yiheng.mobilesafe.bean.ForbiddenInfo;
import com.yiheng.mobilesafe.db.ForbiddenDAO;
import com.yiheng.mobilesafe.utils.ConstantUtils;

import java.util.ArrayList;

public class SRLJActivity extends AppCompatActivity {
    private ImageView iv_srlj_add_forbidden;
    private ListView lv_srlj_forbiddons;
    private ImageView iv_srlj_empty;
    private ProgressBar pbr_srlj;
    private ForbiddenDAO forbiddenDAO;
    private MyAdapter adapter;
    private ArrayList<ForbiddenInfo> infos;
    private static final int SIZE = 10;
    private static final int REQUESTCODE_EDIT = 20001;
    private static final int REQUESTCODE_ADD = 20002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_srlj);
        iv_srlj_add_forbidden = (ImageView) findViewById(R.id.iv_srlj_add_forbidden);
        lv_srlj_forbiddons = (ListView) findViewById(R.id.lv_srlj_forbiddons);
        iv_srlj_empty = (ImageView) findViewById(R.id.iv_srlj_empty);
        pbr_srlj = (ProgressBar) findViewById(R.id.pbr_srlj);

        forbiddenDAO = new ForbiddenDAO(getApplicationContext());

        new Thread() {
            @Override
            public void run() {
                SystemClock.sleep(1600);
                infos = forbiddenDAO.queryPart(SIZE, 0);
                adapter = new MyAdapter();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pbr_srlj.setVisibility(View.INVISIBLE);
                        lv_srlj_forbiddons.setAdapter(adapter);
                        lv_srlj_forbiddons.setEmptyView(iv_srlj_empty);
                    }
                });

            }
        }.start();

        //点击添加图片进入添加黑名单页面
        iv_srlj_add_forbidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SRLJEditActivity.class);
                intent.setAction("add");
                startActivityForResult(intent, REQUESTCODE_ADD);
            }
        });
        //点击item进入编辑页面
        lv_srlj_forbiddons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ForbiddenInfo info = (ForbiddenInfo) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), SRLJEditActivity.class);
                intent.setAction("edit");
                intent.putExtra(ConstantUtils.EXTR_POSITION, position);
                intent.putExtra(ConstantUtils.EXTRA_NUMBER, info.forbiddenNumber);
                intent.putExtra(ConstantUtils.EXTRA_TYPE, info.forbiddenType);
                startActivityForResult(intent, REQUESTCODE_EDIT);
            }
        });

        lv_srlj_forbiddons.setOnScrollListener(new AbsListView.OnScrollListener() {
            //            Parameters:
            //            view - The view whose scroll state is being reported
            //            scrollState - The current scroll state. One of SCROLL_STATE_TOUCH_SCROLL or SCROLL_STATE_IDLE.
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = view.getLastVisiblePosition();
                    if (lastVisiblePosition == infos.size() - 1) {
                        pbr_srlj.setVisibility(View.VISIBLE);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                SystemClock.sleep(1600);
                                ArrayList<ForbiddenInfo> partInfos = forbiddenDAO.queryPart(SIZE, infos.size());
                                if (partInfos.isEmpty()) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                } else {
                                    infos.addAll(partInfos);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            adapter.notifyDataSetChanged();
                                            pbr_srlj.setVisibility(View.INVISIBLE);
                                        }
                                    });
                                }
                            }
                        }).start();
                    }
                }
            }

            //            Parameters:
            //            view - The view whose scroll state is being reported
            //            firstVisibleItem - the index of the first visible cell (ignore if visibleItemCount == 0)
            //            visibleItemCount - the number of visible cells
            //            totalItemCount - the number of items in the list adaptor
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public ForbiddenInfo getItem(int position) {

            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getApplicationContext(), R.layout.item_srlj_forbidden, null);
                viewHolder.tv_srlj_forbidden_number =
                        (TextView) convertView.findViewById(R.id.tv_srlj_forbidden_number);
                viewHolder.tv_srlj_forbidden_type =
                        (TextView) convertView.findViewById(R.id.tv_srlj_forbidden_type);
                viewHolder.iv_delet_forbidden =
                        (ImageView) convertView
                                .findViewById(R.id.iv_srlj_delet_forbidden);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ForbiddenInfo info = getItem(position);
            viewHolder.tv_srlj_forbidden_number.setText(info.forbiddenNumber);

            switch (info.forbiddenType) {
                case ForbiddenInfo.TYPE_ALL:
                    viewHolder.tv_srlj_forbidden_type.setText("电话+短信");
                    break;
                case ForbiddenInfo.TYPE_CALL:
                    viewHolder.tv_srlj_forbidden_type.setText("电话");
                    break;
                case ForbiddenInfo.TYPE_SMS:
                    viewHolder.tv_srlj_forbidden_type.setText("短信");
                    break;
            }
            //点击删除图片删除数据库中黑名单并更新列表显示
            viewHolder.iv_delet_forbidden.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pbr_srlj.setVisibility(View.VISIBLE);
                    new Thread() {

                        @Override
                        public void run() {
                            SystemClock.sleep(1000);
                            boolean delete = forbiddenDAO.delete(info.forbiddenNumber);
                            if (delete) {
                                infos.remove(info);
                                //删除一条后再补充显示一条
                                ArrayList<ForbiddenInfo> partInfo = forbiddenDAO.queryPart(1, infos.size());
                                infos.addAll(partInfo);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                        pbr_srlj.setVisibility(View.INVISIBLE);
                                    }
                                });
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "删除失败", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }.start();

                }
            });

            return convertView;
        }

    }

    static class ViewHolder {
        TextView tv_srlj_forbidden_number;
        TextView tv_srlj_forbidden_type;
        ImageView iv_delet_forbidden;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUESTCODE_EDIT == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                int type = data.getIntExtra(ConstantUtils.EXTRA_TYPE, 0);
                int position = data.getIntExtra(ConstantUtils.EXTR_POSITION, 0);
                infos.get(position).forbiddenType = type;
                adapter.notifyDataSetChanged();
            }

        } else if (REQUESTCODE_ADD == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                String number = data.getStringExtra(ConstantUtils.EXTRA_NUMBER);
                int type = data.getIntExtra(ConstantUtils.EXTRA_TYPE, 0);
                ForbiddenInfo info = new ForbiddenInfo(number, type);
                infos.add(info);
                adapter.notifyDataSetChanged();
            }
        }
    }
}
