package com.daoxuehao.poplistlibrary;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


/**
 * Created by Yale on 2016/7/26.
 */
public class SimplePopList extends PopList {


    private ListView mListView;

    public SimplePopList(Context context){
        super(context,R.layout.simple_poplist);
    }

    @Override
    protected void initView() {
        mListView = (ListView) mContentView.findViewById(R.id.list_poplist);
        mListView.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    protected void bindAdapter() {
        mPopAdapter = new PopAdapter(new BindView());
        mListView.setAdapter(mPopAdapter);
    }

    class BindView implements BaseAdapterBindView{

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_simple_poplist,null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_title_item_poplist);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(mData.get(position));

            if (mData.get(position).equals(mFocusData)){
                viewHolder.title.setTextColor(mFocusColor);
            }else{
                viewHolder.title.setTextColor(Color.BLACK);
            }
            return convertView;
        }

        class ViewHolder{
            TextView title;
        }
    }
}
