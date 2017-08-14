package com.tensun.additiveanimationsdemo.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.bartoszlipinski.viewpropertyobjectanimator.ViewPropertyObjectAnimator;
import com.tensun.additiveanimationsdemo.AdditiveAnimationsShowcaseActivity;
import com.tensun.additiveanimationsdemo.R;

import java.util.ArrayList;
import java.util.List;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;
import at.wirecube.additiveanimations.helper.EaseInOutPathInterpolator;

public class MultipleViewsAnimationDemoFragment extends Fragment {
    FrameLayout rootView;
    View orangeView;
    View blueView;
    View greenView;
    View pinkView;
    int rotation = 0;

    List<View> views = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(                                                   // 以fragment_multiple_views_demo 作為畫面的顯示布局
                R.layout.fragment_multiple_views_demo,
                container,
                false
        );
        orangeView = rootView.findViewById(R.id.animated_view);
        blueView = rootView.findViewById(R.id.animated_view2);
        greenView = rootView.findViewById(R.id.animated_view3);
        pinkView = rootView.findViewById(R.id.animated_view4);

        pinkView.setVisibility(View.GONE);
        blueView.setVisibility(View.GONE);
        greenView.setVisibility(View.GONE);
        orangeView.setVisibility(View.GONE);

        for(int i = 5; i < 25; i++) {                                                               // 建立一組透明化的圖片, 把xml裡面的 20張view, 全部放入views, 並為每一張view 設置透明度, 為了呈現更好的視覺效果
            View v = rootView.findViewById(R.id.animated_view4 -4 +i);
            views.add(v);
            v.setAlpha(0.3f);
        }

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN                                 // 只要觸碰到螢幕, 就會觸發
                        || event.getAction() == MotionEvent.ACTION_MOVE
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    float x = event.getX();                                                         // 將點擊的x座標位置, 賦予x
                    float y = event.getY();                                                         // 將點擊的y座標位置, 賦予x

                    if(event.getAction() == MotionEvent.ACTION_UP
                            && AdditiveAnimationsShowcaseActivity.ADDITIVE_ANIMATIONS_ENABLED) {
                        rotation = 0;                                                               // snap to 360°
                    } else if(x < rootView.getWidth()/2.0) {                                        // 如果點擊的位置 是在左半邊
                        rotation -= 10;
                    } else {                                                                        // 如果點擊的位置 是在右半邊
                        rotation += 10;
                    }

                    float width = rootView.getWidth();
                    float height = rootView.getHeight();

                    if(AdditiveAnimationsShowcaseActivity.ADDITIVE_ANIMATIONS_ENABLED) {        // 如果有開啟 Additive animations enabled
                        AdditiveAnimator animator = new AdditiveAnimator().withLayer();
                        for(View view : views) {                                                    // 把views的所有圖片, 分別賦予view, 並分別對其動畫化
                            animator = animator.target(view).x(x).y(y)
                                    .rotation(rotation).thenWithDelay(50);                          // thenWithDelay: 每張view的間隔時間
                        }
                        animator.start();                                                           // 播放動畫
                    } else {                                                                        // 如果沒有開啟 Additive animations enabled
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.setInterpolator(EaseInOutPathInterpolator.create());
                        animatorSet.setDuration(1000);                                              // 完成這個動畫的總耗時
                        List<Animator> animators = new ArrayList<>();                               // 定義一組動畫集合
                        animators.add(ViewPropertyObjectAnimator.animate(orangeView).x(x).y(y).rotation(rotation).get());
                        animators.add(ViewPropertyObjectAnimator.animate(blueView).x(width - x - blueView.getWidth()).y(height - y).rotation(-rotation).get());
                        animators.add(ViewPropertyObjectAnimator.animate(greenView).x(x).y(height - y).rotation(-rotation).get());
                        animators.add(ViewPropertyObjectAnimator.animate(pinkView).x(width - x - pinkView.getWidth()).y(y).rotation(rotation).get());
                        for(View view :views) {                                                     // 把20張圖片 分別做成動畫, 並放入animators
                            animators.add(
                                    ViewPropertyObjectAnimator
                                            .animate(view).x(x).y(y).rotation(-rotation).get()
                            );
                        }
                        animatorSet.playTogether(animators);                                        // 同時執行這20個動畫
                        animatorSet.start();                                                        // 播放動畫
                    }
                }
                return true;
            }
        });
        return rootView;
    }
}
