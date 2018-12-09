package jsc.kit.guidance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.View;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class ViewDrawingCacheUtils {

    public static Bitmap getDrawingCache(@NonNull View view) {
        int width = view.getWidth();
        int height = view.getHeight();
        if (width + height == 0) {
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            width = view.getMeasuredWidth();
            height = view.getMeasuredHeight();
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    /**
     * Get status bar height.
     *
     * @param context context
     * @return the height of status bar
     */
    public static int getStatusBarHeight(@NonNull Context context) {
        int statusBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * Get action bar height.
     *
     * @param context context
     * @return the height of action bar
     */
    public static int getActionBarSize(@NonNull Context context) {
        TypedValue typedValue = new TypedValue();
        int[] attribute = new int[]{android.R.attr.actionBarSize};
        TypedArray array = context.obtainStyledAttributes(typedValue.resourceId, attribute);
        int actionBarSize = array.getDimensionPixelSize(0, 0);
        array.recycle();
        return actionBarSize;
    }

    public static int[] getScreenLocation(@NonNull View view){
        int[] outLocation = new int[2];
        view.getLocationOnScreen(outLocation);
        return outLocation;
    }

    public static int[] getWindowLocation(@NonNull View view){
        int[] outLocation = new int[2];
        view.getLocationInWindow(outLocation);
        return outLocation;
    }
}
