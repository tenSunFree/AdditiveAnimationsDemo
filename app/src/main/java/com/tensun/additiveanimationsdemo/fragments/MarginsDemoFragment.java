package com.tensun.additiveanimationsdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.tensun.additiveanimationsdemo.R;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.helper.EaseInOutPathInterpolator;

import static com.tensun.additiveanimationsdemo.AdditiveAnimationsShowcaseActivity.ADDITIVE_ANIMATIONS_ENABLED;

public class MarginsDemoFragment extends Fragment {

    ViewGroup rootView;
    View animatedView;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_margins_demo, container, false);    // 畫面的綁定, 取得findViewById的功能
        animatedView = rootView.findViewById(R.id.animated_view_with_margins);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE
                        || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(ADDITIVE_ANIMATIONS_ENABLED) {                                           // 如果有開啟 Additive animations enabled
                        AdditiveAnimator.animate(animatedView)                                       // 當手指滑到哪, animatedView物件 會跟著移動到該位置
                                .leftMargin((int) event.getX() - 100)                               // animatedView物件 左上角的x座標位置, 這邊為了讓animatedView物件 置中在手指點擊的位置
                                .topMargin((int) event.getY() - 150)                                // animatedView物件 左上角的y座標位置, 這邊為了讓animatedView物件 置中在手指點擊的位置
                                .start();
                    } else {                                                                        // 如果沒有開啟 Additive animations enabled
                        ViewPropertyObjectAnimator.animate(animatedView)
                                .leftMargin((int) event.getX() - 100)                               // animatedView物件 左上角的x座標位置, 這邊為了讓animatedView物件 置中在手指點擊的位置
                                .topMargin((int) event.getY() - 150)                                // animatedView物件 左上角的y座標位置, 這邊為了讓animatedView物件 置中在手指點擊的位置
                                .setInterpolator(EaseInOutPathInterpolator.create())
                                .setDuration(1500)                                                  // 想要花多久時間完成該動畫
                                .start();
                    }
                }
                return ADDITIVE_ANIMATIONS_ENABLED;                                             // 1. 如果有開啟Additive animations enabled, 結束手勢前(手指離開) 不斷發送最新位置給它, 讓它即時更新 2. 如果沒有開啟Additive animations enabled, 結束手勢前(手指離開) 不要再發送事件給它, 不然會產生頓頓的不好效果
            }
        });
        return rootView;
    }
}