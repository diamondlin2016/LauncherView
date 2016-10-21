package com.rongyi.diamond.animation.launcher;

import android.content.Context;

/**
 * Author:    Diamond_Lin
 * Version    V1.0
 * Date:      16/10/17 上午11:28
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/10/17      Diamond_Lin            1.0                    1.0
 * Why & What is modified:
 */
public class Utils {
    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
