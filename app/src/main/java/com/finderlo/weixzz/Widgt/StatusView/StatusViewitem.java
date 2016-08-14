package com.finderlo.weixzz.Widgt.StatusView;

import android.content.Context;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.finderlo.weixzz.R;
import com.finderlo.weixzz.SinaAPI.openapi.models.Status;
import com.finderlo.weixzz.Util.Util;
import com.finderlo.weixzz.Widgt.AutoLinkTextView;

import java.util.ArrayList;

/**
 * Created by Finderlo on 2016/8/8.
 */
public class StatusViewitem extends LinearLayout{

    public static final int TYPE_STATUS = 1;
    public static final int TYPE_RETWEETED_STATUS = 2;

    private int mType = 1;
    private Status mStatus;


    public StatusViewitem(Context context) {
        super(context);
        setOrientation(VERTICAL);
        setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        setPadding(Util.dip2px(context,5),Util.dip2px(context,5),Util.dip2px(context,5),Util.dip2px(context,5));
    }

    public StatusViewitem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    StatusHeadView headView = null;
    private void initView(Context context) {
        if (mType==TYPE_STATUS){
            headView = new StatusHeadView(context);
            headView.setStatus(mStatus);
            addView(headView);
        }

        initStatusContent(context,mStatus.text);
        initStatusPics(context,mStatus);

        if (mStatus.retweeted_status!=null){
            StatusViewitem statusViewitem = new StatusViewitem(context);
            statusViewitem.setStatus(context,mStatus.retweeted_status,TYPE_RETWEETED_STATUS);
            statusViewitem.setIsRetweetedStatus(true);
            addView(statusViewitem);
        }

    }

    private void initStatusPics(Context context, Status status) {
        if (null == mStatus.pic_urls||mStatus.pic_urls.size()==0)  return;
        ImageViews imageViews = new ImageViews(context);
        imageViews.setImageSrc(status);
        addView(imageViews);
    }

    private void initStatusContent(Context context,String text) {
        AutoLinkTextView statusContent = new AutoLinkTextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        statusContent.setLayoutParams(params);
        statusContent.setPadding(15,15,0,0);
        statusContent.setAutoLinkMask(Linkify.WEB_URLS);
        statusContent.setTextSize(16);

        statusContent.setText(text);
        addView(statusContent);
    }


    public void setStatus(Context context,Status status){
        mStatus = status;
        initView(context);
    }

    public void setStatus(Context context,Status status,int type){
        mStatus = status;
        if (TYPE_RETWEETED_STATUS == type){
            mType = TYPE_RETWEETED_STATUS;
            setBackground(getResources().getDrawable(R.drawable.backgroundcolor));
        }
        initView(context);
    }

    public void setHeadViewVisible(boolean flag){
        if (headView==null) return;
        if (flag){
            headView.setVisibility(View.VISIBLE);
        }else headView.setVisibility(View.GONE);
    }

    public void setIsRetweetedStatus(boolean type){
        if (type){
            setBackground(getResources().getDrawable(R.drawable.backgroundcolor));
        }
    }
}
