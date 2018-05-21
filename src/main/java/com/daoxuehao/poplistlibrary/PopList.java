package com.daoxuehao.poplistlibrary;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
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
public abstract class PopList<T> {
    protected PopupWindow mPopupWindow;
    protected Context mContext;
    protected List<T> mData;
    protected T mFocusData=null;
    protected OnChoseListener mOnChoseListener;
    protected int mFocusColor = Color.BLACK;
    protected View mContentView;
    protected AdapterView.OnItemClickListener mOnItemClickListener;

    protected PopAdapter mPopAdapter;
    private PopupWindow.OnDismissListener mOnDismissListener;

    public PopList(Context context,int layout){
        mContext = context;

        mFocusColor = context.getResources().getColor(R.color.focus_color);

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
        mPopupWindow = new PopupWindow(mContentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

                if (mOnDismissListener!=null){
                    mOnDismissListener.onDismiss();
                }
            }
        });
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.update();

        initView();

    }
    public PopupWindow getPopupWindow(){
        return mPopupWindow;
    }
    protected abstract void initView();
    protected abstract void bindAdapter();
    public  PopList setData(List<T> data){
        mData = data;
        bindAdapter();
        return this;
    }
    public void showAsDropDown(final View anchor, final int xoff, final int yoff) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect visibleFrame = new Rect();
            anchor.getGlobalVisibleRect(visibleFrame);
            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
            mPopupWindow.setHeight(height);
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
        } else {
            mPopupWindow.showAsDropDown(anchor, xoff, yoff);
        }
    }
    public PopList show(View parent){

        showAsDropDown(parent,0,0);
        return this;
    }
    public PopList show(View parent, int xoff,  int yoff){

        showAsDropDown(parent,xoff,yoff);
        return this;
    }
    public PopList addDismissListener(PopupWindow.OnDismissListener onDismissListener){
        mOnDismissListener = onDismissListener;
        return  this;
    }
    public PopList setOnChoseListener(OnChoseListener choseListener){
        mOnChoseListener = choseListener;
        return this;
    }
    public PopList setFocusColor(int color){
        mFocusColor = color;
        return this;
    }
    public interface OnChoseListener<T>{
        void chose(int index,T data);
    }
    public PopList setFocusData(T focus){
        mFocusData = focus;
        mPopAdapter.notifyDataSetChanged();
        return this;
    }
    public class PopAdapter extends BaseAdapter {
        private  BaseAdapterBindView mBaseAdapterBindView;
        public PopAdapter(BaseAdapterBindView baseAdapterBindView){
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

    public interface  BaseAdapterBindView{
         View getView(int position, View convertView, ViewGroup parent);
    }
}
