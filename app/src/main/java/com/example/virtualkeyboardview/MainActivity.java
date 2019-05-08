package com.example.virtualkeyboardview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String strPass="";//保存密码
    private GridView mGridView;
    private PasswordEditText mPasswordEditText;
    private List<Integer> listNumber;//1,2,3---0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = findViewById(R.id.grid_view);
        mPasswordEditText = findViewById(R.id.edit_pass);
        mPasswordEditText.clearFocus();
initView();
    }

    private void initView() {
        //初始化数据
        listNumber = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            listNumber.add(i);
        }
        listNumber.add(10);
        listNumber.add(0);
        listNumber.add(R.mipmap.ic_pay_del0);

        mGridView.setAdapter(adapter);

    }
    /**
     *   GridView的适配器
     */

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return listNumber.size();
        }
        @Override
        public Object getItem(int position) {
            return listNumber.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item_keyboard, null);
                holder = new ViewHolder();
                holder.btnNumber = (TextView) convertView.findViewById(R.id.btNumber);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            //-------------设置数据----------------
            holder.btnNumber.setText(listNumber.get(position)+"");
            if (position == 9) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundColor(MainActivity.this.getResources().getColor(R.color.graye3));
            }
            if (position == 11) {
                holder.btnNumber.setText("");
                holder.btnNumber.setBackgroundResource(listNumber.get(position));
            }
            //监听事件----------------------------
            if(position==11) {
                holder.btnNumber.setOnTouchListener(new View.OnTouchListener() {

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case MotionEvent.ACTION_DOWN:
                                holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del1);
                                break;
                            case MotionEvent.ACTION_MOVE:
                                holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del1);
                                break;
                            case MotionEvent.ACTION_UP:
                                holder.btnNumber.setBackgroundResource(R.mipmap.ic_pay_del0);
                                break;
                        }
                        return false;
                    }
                });
            }
            holder.btnNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position < 11 &&position!=9) {//0-9按钮
                        if(mPasswordEditText.getText().toString().length()==6){
                            return;
                        } else {
                            mPasswordEditText.addPassword(String.valueOf(listNumber.get(position)));
                            //输入完成
                            if(mPasswordEditText.getText().toString().length()==6){
                                Toast.makeText(MainActivity.this,"密码："+mPasswordEditText.getText().toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if(position == 11) {//删除
                        if(mPasswordEditText.getText().toString().length()>0){
                            mPasswordEditText.deleteLastPassword();
                        }
                    }
                    if(position==9){//空按钮
                    }
                }
            });

            return convertView;
        }
    };
    static class ViewHolder {
        public TextView btnNumber;
    }

}
