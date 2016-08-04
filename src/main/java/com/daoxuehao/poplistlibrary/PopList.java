package com.daoxuehao.poplistlibrary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;

import java.util.List;

/**
 * Created by Yale on 2016/8/3.
 */
public abstract class PopList {
    protected PopupWindow mPopupWindow;
    protected Context mContext;
    protected List<String> mData;
    protected String mFocusData="";
    protected OnChoseListener mOnChoseListener;
    protected int mFocusColor = Color.BLACK;
    protected View mContentView;
    protected AdapterView.OnItemClickListener mOnItemClickListener;

    protected PopAdapter mPopAdapter;

    public PopList(Context context,int layout){
        mContext = context;

        mContentView = LayoutInflater.from(context).inflate(layout,null);
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        mOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mData!=null){
                    mFocusData = mData.get(position);
                }
                if (mOnChoseListener!=null){
                    mOnChoseListener.chose(position,mFocusData);
                }
                mPopupWindow.dismiss();
            }
        };
        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.update();

        initView();


    }
    protected abstract void initView();
    protected abstract void bindAdapter();
    public   PopList setData(List<String> data){
        mData = data;
        bindAdapter();
        return this;
    }

    public PopList show(View parent){
        mPopupWindow.showAsDropDown(parent);
        return this;
    }

    public PopList setOnChoseListener(OnChoseListener choseListener){
        mOnChoseListener = choseListener;
        return this;
    }
    public PopList setFocusColor(int color){
        mFocusColor = color;
        return this;
    }
    public interface OnChoseListener{
        public void chose(int index,String data);
    }
    public PopList setFocusData(String focus){
        mFocusData = focus;
        mPopAdapter.notifyDataSetChanged();
        return this;
    }
    class PopAdapter extends BaseAdapter {
        //List<String> mData = null;
        //private Context mContext;
        private  BaseAdapterBindView mBaseAdapterBindView;
        public PopAdapter(BaseAdapterBindView baseAdapterBindView){
          //  mContext  = context;
         //   mData = data;

            mBaseAdapterBindView = baseAdapterBindView;
        }
        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            return mBaseAdapterBindView.getView(position,convertView,parent);
        }

    }

    interface  BaseAdapterBindView{
        public View getView(int position, View convertView, ViewGroup parent);
    }
}
