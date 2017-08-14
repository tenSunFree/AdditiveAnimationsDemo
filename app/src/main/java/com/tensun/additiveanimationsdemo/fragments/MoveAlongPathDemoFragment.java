package com.tensun.additiveanimationsdemo.fragments;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import com.tensun.additiveanimationsdemo.AdditiveAnimationsShowcaseActivity;
import com.tensun.additiveanimationsdemo.R;
import com.tensun.additiveanimationsdemo.helper.DpConverter;

import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator;

public class MoveAlongPathDemoFragment extends Fragment {
    FrameLayout rootView;
    View animatedView;

    int circleRadius = DpConverter.converDpToPx(40);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (FrameLayout) inflater.inflate(
                R.layout.fragment_move_along_path_demo, container, false
        );
        animatedView = rootView.findViewById(R.id.animated_view);

        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE
                        || event.getAction() == MotionEvent.ACTION_DOWN) {
                    if(AdditiveAnimationsShowcaseActivity.ADDITIVE_ANIMATIONS_ENABLED) {
                        AdditiveAnimator.animate(animatedView)                                       // 移動到手指觸碰的位置
                                .x(event.getX())
                                .y(event.getY())
                                .start();
                    }
                }
                return true;                                                                        // 結束手勢前(手指離開) 不斷發送最新位置給它, 讓它即時更新
            }
        });

        // wait for rootView to layout itself so we can get its center
        rootView.post(new Runnable() {
            @Override
            public void run() {
                if (AdditiveAnimationsShowcaseActivity.ADDITIVE_ANIMATIONS_ENABLED) {

                    /** small circle */
                    final Path path1 = new Path();
                    path1.addCircle(
                            rootView.getWidth() / 2,
                            rootView.getHeight() / 2,
                            circleRadius,                                                           // 圓的半徑
                            Path.Direction.CW
                    );
                    AdditiveAnimator.animate(animatedView)
                            .setInterpolator(new LinearInterpolator())
                            .xyAlongPath(path1)
                            .setRepeatCount(ValueAnimator.INFINITE)                                 // 重複次數設為無限循環
                            .start();

                    /** another circle which also updates rotation to better show where on the path we are */
                    final Path path2 = new Path();
                    path2.addCircle(
                            rootView.getWidth() / 2,
                            rootView.getHeight() / 2,
                            rootView.getWidth() / 4,                                                // 圓的半徑
                            Path.Direction.CW
                    );
                    AdditiveAnimator.animate(animatedView).setDuration(3200)
                            .setInterpolator(new LinearInterpolator())
                            .xyRotationAlongPath(path2)
                            .setRepeatCount(ValueAnimator.INFINITE)                                 // 重複次數設為無限循環
                            .start();
                } else {

                }
            }
        });
        return rootView;
    }
}