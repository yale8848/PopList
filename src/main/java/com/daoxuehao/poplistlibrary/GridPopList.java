package com.daoxuehao.poplistlibrary;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by Yale on 2016/8/3.
 */
public class GridPopList extends PopList {

    private GridView mGridView;
    public GridPopList(Context context){
        super(context, R.layout.grid_poplist);
    }

    @Override
    protected void initView() {
        mGridView = (GridView) mContentView.findViewById(R.id.grid_poplist);
        mGridView.setOnItemClickListener(mOnItemClickListener);
    }

    @Override
    protected void bindAdapter() {
        mPopAdapter = new PopAdapter(new BindView());
        mGridView.setAdapter(mPopAdapter);
    }

    class BindView implements BaseAdapterBindView{

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid_poplist,null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_item_grid_poplist);
                viewHolder.imageViewArrow = (ImageView) convertView.findViewById(R.id.iv_grid_arrow);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(mData.get(position));

            if (mData.get(position).equals(mFocusData)){
                viewHolder.title.setTextColor(mFocusColor);
                viewHolder.title.setBackgroundResource(R.drawable.shape_grid_item_focus);
                viewHolder.imageViewArrow.setVisibility(View.VISIBLE);
            }else{
                viewHolder.title.setTextColor(Color.BLACK);
                viewHolder.title.setBackgroundResource(R.drawable.shape_grid_item);
                viewHolder.imageViewArrow.setVisibility(View.GONE);

            }
            return convertView;
        }

        class ViewHolder{
            TextView title;
            ImageView imageViewArrow;
        }
    }
}
