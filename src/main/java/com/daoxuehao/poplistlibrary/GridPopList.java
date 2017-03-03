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
    private boolean mIsRound = false;
    public GridPopList(Context context){
        super(context, R.layout.poplist_grid_poplist);
    }

    public boolean isRound() {
        return mIsRound;
    }

    public void setRound(boolean round) {
        mIsRound = round;
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
                convertView = LayoutInflater.from(mContext).inflate(R.layout.poplist_item_grid_poplist,null);
                viewHolder.title = (TextView) convertView.findViewById(R.id.tv_item_grid_poplist);
                viewHolder.imageViewArrow = (ImageView) convertView.findViewById(R.id.iv_grid_arrow);
                if (mIsRound){
                    viewHolder.imageViewArrow.setVisibility(View.GONE);
                }
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.title.setText(mData.get(position));

            if (mData.get(position).equals(mFocusData)){
                viewHolder.title.setTextColor(mFocusColor);

                int res = R.drawable.poplist_shape_grid_item_focus;
                if (mIsRound){
                    res = R.drawable.poplist_shape_grid_item_focus_r;
                }else{
                    viewHolder.imageViewArrow.setVisibility(View.VISIBLE);
                }
                viewHolder.title.setBackgroundResource(res);

            }else{
                viewHolder.title.setTextColor(Color.BLACK);
                int res = R.drawable.poplist_shape_grid_item;
                if (mIsRound){
                    res = R.drawable.poplist_shape_grid_item_r;
                }
                viewHolder.title.setBackgroundResource(res);
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
