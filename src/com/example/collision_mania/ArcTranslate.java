package com.example.collision_mania;

import android.graphics.Point;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class ArcTranslate extends Animation {

private Point start;
private Point end;
private Point middle;
private final float mFromYValue;
private final float mToYValue;
private final float mXValue;
private final int mFromYType;
private final int mToYType;
private final int mXType;
private final int mType;

public float myX;
public float myY;

private long mElapsedAtPause=0;
private boolean mPaused=false;


/**
 * A translation along an arc defined by three points and a Bezier Curve
 *
 * @param duration - the time in ms it will take for the translation to complete
 * @param fromXType - One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
 * @param fromXValue - Change in X coordinate to apply at the start of the animation
 * @param toXType - One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
 * @param toXValue - Change in X coordinate to apply at the end of the animation
 * @param yType - One of Animation.ABSOLUTE, Animation.RELATIVE_TO_SELF, or Animation.RELATIVE_TO_PARENT.
 * @param yValue - Change in Y coordinate to apply at the middle of the animation (the radius of the arc)
 */
public ArcTranslate(long duration, int fromYType, float fromYValue,
        int toYType, float toYValue, int xType, float xValue,int type){
    setDuration(duration);

     mFromYValue = fromYValue;
     mToYValue = toYValue;
     mXValue = xValue;

     mFromYType = fromYType;
     mToYType = toYType;
     mXType = xType;
     mType = type;
     
     myY = fromYValue;
     myX = 0;
}
/** Calculate the position on a quadratic bezier curve given three points
 *  and the percentage of time passed.
 * from http://en.wikipedia.org/wiki/B%C3%A9zier_curve
 * @param interpolatedTime - the fraction of the duration that has passed where 0<=time<=1
 * @param p0 - a single dimension of the starting point
 * @param p1 - a single dimension of the middle point
 * @param p2 - a single dimension of the ending point
 */
private long calcBezier(float interpolatedTime, float p0, float p1, float p2){
    return Math.round((Math.pow((1 - interpolatedTime), 2) * p0)
           + (2 * (1 - interpolatedTime) * interpolatedTime * p1)
           + (Math.pow(interpolatedTime, 2) * p2));
}

@Override
protected void applyTransformation(float interpolatedTime, Transformation t) {
    float dx = calcBezier(interpolatedTime, start.x, middle.x, end.x);
    float dy = calcBezier(interpolatedTime, start.y, middle.y, end.y);
    
    myX =dx;
    myY =dy;

    t.getMatrix().setTranslate(dx, dy);
}

@Override
public void initialize(int width, int height, int parentWidth, int parentHeight) {
    super.initialize(width, height, parentWidth, parentHeight);
    /*float startX = resolveSize(mFromXType, mFromXValue, width, parentWidth);
    float endX = resolveSize(mToXType, mToXValue, width, parentWidth);
    float middleY = resolveSize(mYType, mYValue, width, parentWidth);
    float middleX = startX + ((endX-startX)/2);
    start = new Point((int)startX, 0);
    end = new Point((int)endX, 0);
    middle = new Point((int)middleX, (int)middleY);*/
    
  float startY = resolveSize(mFromYType, mFromYValue, height, parentHeight);
  float endY = resolveSize(mToYType, mToYValue, height, parentHeight);
  float middleX = resolveSize(mXType, mXValue, height, parentHeight);
  float middleY = startY + ((endY-startY)/2);
  start = new Point(0,(int)startY);
  end = new Point(0,(int)endY);
  middle = new Point(mType*(int)middleX, (int)middleY);
}
@Override
public boolean getTransformation(long currentTime, Transformation outTransformation) { 
    if(mPaused && mElapsedAtPause==0) {
        mElapsedAtPause=currentTime-getStartTime();
    }
    if(mPaused)
        setStartTime(currentTime-mElapsedAtPause);
    return super.getTransformation(currentTime, outTransformation);
}

public void pause() {
    mElapsedAtPause=0;
    mPaused=true;
}

public void resume() {
    mPaused=false;
}
}