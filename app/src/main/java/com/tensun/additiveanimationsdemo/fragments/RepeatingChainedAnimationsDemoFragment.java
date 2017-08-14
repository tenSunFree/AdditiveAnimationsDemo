package com.tensun.additiveanimationsdemo.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.tensun.additiveanimationsdemo.R;
import com.tensun.additiveanimationsdemo.helper.DpConverter;
import com.tensun.additiveanimationsdemo.subclass.AdditiveAnimatorSubclassDemo;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;


public class RepeatingChainedAnimationsDemoFragment extends Fragment {

    ViewGroup rootView;
    View animatedView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_tap_to_move_demo, container, false);
        animatedView = rootView.findViewById(R.id.animated_view);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    AdditiveAnimator.cancelAnimation(animatedView, View.ROTATION);                  // 似乎沒效果? 有待考察
                }
                return true;
            }
        });

        animate();
        return rootView;
    }

    private void animate() {
        int colors[] = new int[] {
                getResources().getColor(R.color.niceOrange),
                getResources().getColor(R.color.niceBlue),
                getResources().getColor(R.color.niceGreen),
                getResources().getColor(R.color.nicePink)
        };
        // TODO: don't use hardcoded px values
        AdditiveAnimatorSubclassDemo.animate(animatedView)
                .x(px(50)).y(px(100)).backgroundColor(colors[1]).rotation(0)                       // 移動, 藍色
                .thenBounceBeforeEnd(800, 400)                                                      // 藍色停頓
                .thenBeforeEnd(400).x(px(200)).backgroundColor(                                     // 移動, 綠色
                        colors[2]).rotationBy(45).setDuration(1000)
                .thenBounceBeforeEnd(800, 400)                                                      // 綠色停頓
                .thenBeforeEnd(400).y(px(300)).backgroundColor(                                     // 移動, 紅色
                        colors[3]).rotationBy(45).setDuration(1000)
                .thenBounceBeforeEnd(800, 400)                                                      // 紅色停頓
                .thenBeforeEnd(400).x(px(50)).backgroundColor(                                      // 移動, 黃色
                        colors[0]).rotationBy(90).setDuration(1000)
                .thenBounceBeforeEnd(800, 300)                                                      //  黃色停頓
                .addEndAction(new AdditiveAnimator.AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(boolean wasCancelled) {
                        if (getActivity() != null) {
                            animate();
                        }
                    }
                })
                .start();
    }

    private int px(int dp) {
        return DpConverter.converDpToPx(dp);                                                        // 返回轉換成px的值
    }

}
