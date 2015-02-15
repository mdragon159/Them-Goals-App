package com.mdstudios.themgoals.Utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by jawad on 15/02/15.
 *
 * Purpose: Support fragment transitions
 */
public class RelativeLayoutAnim extends RelativeLayout {
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
}
