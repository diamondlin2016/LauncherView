package com.rongyi.diamond.animation.launcher;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.rongyi.diamond.animation.R;

/**
 * Author:    Diamond_Lin
 * Version    V1.0
 * Date:      16/10/18 下午3:30
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/10/18      Diamond_Lin            1.0                    1.0
 * Why & What is modified:
 */
public class LauncherView extends RelativeLayout {
    private int mHeight;
    private int mWidth;
    private int dp80 = Utils.dp2px(getContext(), 80);
    private boolean mHasStart;

    public LauncherView(Context context) {
        super(context);
        init();
    }

    public LauncherView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LauncherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    ImageView red, purple, yellow, blue;

    private void init() {
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(CENTER_HORIZONTAL, TRUE);//这里的TRUE 要注意 不是true
        lp.addRule(CENTER_VERTICAL, TRUE);
        lp.setMargins(0, 0, 0, dp80);

        purple = new ImageView(getContext());
        purple.setLayoutParams(lp);
        purple.setImageResource(R.drawable.shape_circle_purple);
        addView(purple);

        yellow = new ImageView(getContext());
        yellow.setLayoutParams(lp);
        yellow.setImageResource(R.drawable.shape_circle_yellow);
        addView(yellow);

        blue = new ImageView(getContext());
        blue.setLayoutParams(lp);
        blue.setImageResource(R.drawable.shape_circle_blue);
        addView(blue);

        red = new ImageView(getContext());
        red.setLayoutParams(lp);
        red.setImageResource(R.drawable.shape_circle_red);
        addView(red);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus&&!mHasStart){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    start();
                    mHasStart = true;
                }
            },500);
        }
    }

    public void start() {
        ViewPath redPath1 = new ViewPath(); //偏移坐标
        redPath1.moveTo(0, 0);
        redPath1.lineTo(mWidth / 5 - mWidth / 2, 0);
        ViewPath redPath2 = new ViewPath();
        redPath2.moveTo(mWidth / 5 - mWidth / 2, 0);
        redPath2.curveTo(-700, -mHeight / 2, mWidth / 3 * 2, -mHeight / 3 * 2, 0, -dp80);
        setAnimation(red, redPath1, redPath2);


        ViewPath purplePath1 = new ViewPath(); //偏移坐标
        purplePath1.moveTo(0, 0);
        purplePath1.lineTo(mWidth / 5 * 2 - mWidth / 2, 0);
        ViewPath purplePath2 = new ViewPath(); //偏移坐标
        purplePath2.moveTo(mWidth / 5 * 2 - mWidth / 2, 0);
        purplePath2.curveTo(-300, -mHeight / 2, mWidth, -mHeight / 9 * 5, 0, -dp80);
        setAnimation(purple, purplePath1, purplePath2);


        ViewPath yellowPath1 = new ViewPath(); //偏移坐标
        yellowPath1.moveTo(0, 0);
        yellowPath1.lineTo(mWidth / 5 * 3 - mWidth / 2, 0);
        ViewPath yellowPath2 = new ViewPath(); //偏移坐标
        yellowPath2.moveTo(mWidth / 5 * 3 - mWidth / 2, 0);
        yellowPath2.curveTo(300, mHeight, -mWidth, -mHeight / 9 * 5, 0, -dp80);
        setAnimation(yellow, yellowPath1, yellowPath2);


        ViewPath bluePath1 = new ViewPath(); //偏移坐标
        bluePath1.moveTo(0, 0);
        bluePath1.lineTo(mWidth / 5 * 4 - mWidth / 2, 0);
        ViewPath bluePath2 = new ViewPath(); //偏移坐标
        bluePath2.moveTo(mWidth / 5 * 4 - mWidth / 2, 0);
        bluePath2.curveTo(700, mHeight / 3 * 2, -mWidth / 2, mHeight / 2, 0, -dp80);
        setAnimation(blue, bluePath1, bluePath2);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLogo();
            }
        }, 2400);
    }

    private void setAnimation(final ImageView target, ViewPath path1, ViewPath path2) {
        //左右平移
        ObjectAnimator redAnim1 = ObjectAnimator.ofObject(new ViewObj(target), "fabLoc", new ViewPathEvaluator(), path1.getPoints().toArray());
        redAnim1.setInterpolator(new AccelerateDecelerateInterpolator());
        redAnim1.setDuration(800);
        //贝塞尔曲线
        ObjectAnimator redAnim2 = ObjectAnimator.ofObject(new ViewObj(target), "fabLoc", new ViewPathEvaluator(), path2.getPoints().toArray());
        redAnim2.setInterpolator(new AccelerateDecelerateInterpolator());

        //组合动画
        addAnimation(redAnim1, redAnim2, target);
    }

    private void addAnimation(ObjectAnimator animator1, ObjectAnimator animator2, ImageView target) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 1f, 0.5f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 1, getScale(target), 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 1, getScale(target), 1.0f);

        AnimatorSet all2 = new AnimatorSet();
        all2.setDuration(1800);
        all2.playTogether(alpha, scaleX, scaleY, animator2);
        all2.addListener(new AnimEndListener(target));

        AnimatorSet all = new AnimatorSet();
        all.playSequentially(animator1, all2);
        all.start();
    }

    private float getScale(ImageView target) {
        if (target == red)
            return 3.0f;
        if (target == purple)
            return 2.0f;
        if (target == yellow)
            return 4.5f;
        if (target == blue)
            return 3.5f;
        return 2f;

    }


    private void showLogo() {
        View view = View.inflate(getContext(), R.layout.widget_load_view, this);
        View logo = view.findViewById(R.id.iv_logo);
        final View slogo = view.findViewById(R.id.iv_slogo);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(logo, View.ALPHA, 0f, 1f);
        alpha.setDuration(800);

        alpha.start();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator alpha = ObjectAnimator.ofFloat(slogo, View.ALPHA, 0f, 1f);
                alpha.setDuration(200);
                alpha.start();
            }
        },400);

    }

    private class AnimEndListener extends AnimatorListenerAdapter {
        private View target;

        public AnimEndListener(View target) {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation) {
            super.onAnimationEnd(animation);
            removeView((target));
        }
    }


    public class ViewObj {
        private final ImageView red;

        public ViewObj(ImageView red){
            this.red = red;
        }

        public void setFabLoc(ViewPoint newLoc){
            red.setTranslationX(newLoc.x);
            red.setTranslationY(newLoc.y);
        }
    }


}


