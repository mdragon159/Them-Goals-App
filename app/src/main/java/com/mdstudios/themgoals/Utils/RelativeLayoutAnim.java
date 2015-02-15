package com.mdstudios.themgoals.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

/**
 * Created by jawad on 15/02/15.
 *
 * Purpose: Support fragment translate transitions
 */
public class RelativeLayoutAnim extends RelativeLayout {
    private float yFraction = 0;
    private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

    public RelativeLayoutAnim(Context context) {
        super(context);
    }

    public RelativeLayoutAnim(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RelativeLayoutAnim(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public float getXFraction() {
        return getX() / getWidth(); // TODO: guard divide-by-zero
    }

    public void setXFraction(float xFraction) {
        // TODO: cache width
        final int width = getWidth();
        setX((width > 0) ? (xFraction * width) : -9999);
    }

    public void setYFraction(float fraction) {

        this.yFraction = fraction;

        if (getHeight() == 0) {
            if (preDrawListener == null) {
                preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                        setYFraction(yFraction);
                        return true;
                    }
                };
                getViewTreeObserver().addOnPreDrawListener(preDrawListener);
            }
            return;
        }

        float translationY = getHeight() * fraction;
        setTranslationY(translationY);
    }

    public float getYFraction() {
        if (getHeight() == 0) {
            return 0;
        }
        return getTranslationY() / getHeight();
    }
}
