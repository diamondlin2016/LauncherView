package com.rongyi.diamond.animation.launcher;

import android.animation.TypeEvaluator;

/**
 * Author:    Diamond_Lin
 * Version    V1.0
 * Date:      16/10/18 下午6:48
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 16/10/18      Diamond_Lin            1.0                    1.0
 * Why & What is modified:
 */
public class ViewPathEvaluator implements TypeEvaluator<ViewPoint> {


    public ViewPathEvaluator() {
    }

    @Override
    public ViewPoint evaluate(float t, ViewPoint startValue, ViewPoint endValue) {

        float x  ,y;

        float startX,startY;

        if(endValue.operation == ViewPath.LINE){

            startX = (startValue.operation==ViewPath.QUAD)?startValue.x1:startValue.x;

            startX = (startValue.operation == ViewPath.CURVE)?startValue.x2:startX;

            startY = (startValue.operation==ViewPath.QUAD)?startValue.y1:startValue.y;

            startY = (startValue.operation == ViewPath.CURVE)?startValue.y2:startY;

            x = startX + t * (endValue.x - startX);
            y = startY+ t * (endValue.y - startY);



        }else if(endValue.operation == ViewPath.CURVE){


            startX = (startValue.operation==ViewPath.QUAD)?startValue.x1:startValue.x;
            startY = (startValue.operation==ViewPath.QUAD)?startValue.y1:startValue.y;

            float oneMinusT = 1 - t;


            x = oneMinusT * oneMinusT * oneMinusT * startX +
                    3 * oneMinusT * oneMinusT * t * endValue.x +
                    3 * oneMinusT * t * t * endValue.x1+
                    t * t * t * endValue.x2;

            y = oneMinusT * oneMinusT * oneMinusT * startY +
                    3 * oneMinusT * oneMinusT * t * endValue.y +
                    3 * oneMinusT * t * t * endValue.y1+
                    t * t * t * endValue.y2;


        }else if(endValue.operation == ViewPath.MOVE){

            x = endValue.x;
            y = endValue.y;


        }else if(endValue.operation == ViewPath.QUAD){


            startX = (startValue.operation==ViewPath.CURVE)?startValue.x2:startValue.x;
            startY = (startValue.operation==ViewPath.CURVE)?startValue.y2:startValue.y;

            float oneMinusT = 1 - t;
            x = oneMinusT * oneMinusT *  startX +
                    2 * oneMinusT *  t * endValue.x +
                    t * t * endValue.x1;

            y = oneMinusT * oneMinusT * startY +
                    2  * oneMinusT * t * endValue.y +
                    t * t * endValue.y1;


        }else {
            x = endValue.x;
            y = endValue.y;
        }


        return new ViewPoint(x,y);
    }
}

