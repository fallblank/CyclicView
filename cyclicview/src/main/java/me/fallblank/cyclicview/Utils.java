package me.fallblank.cyclicview;

import android.content.Context;

/**
 * Created by fallb on 2016/8/13.
 */
public class Utils {
    static float dpToPx(Context context, int dp) {
        final float density = context.getResources().getDisplayMetrics().density;
        return density * dp / 160;
    }
}
