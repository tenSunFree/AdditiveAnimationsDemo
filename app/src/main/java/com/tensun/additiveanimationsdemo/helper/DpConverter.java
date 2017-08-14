package com.tensun.additiveanimationsdemo.helper;

import android.util.TypedValue;

import com.tensun.additiveanimationsdemo.AAApplication;

public class DpConverter {

    public static int converDpToPx(float dp) {
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,                                                      // 轉換前的單位是什麼?
                dp,                                                                                 // 轉換前的大小?
                AAApplication.getContext().getResources().getDisplayMetrics()                         // 封裝了顯示區域的各種屬性值
        );
        return Math.round(px);                                                                       // 對px 進行四捨五入取整數
    }
}
