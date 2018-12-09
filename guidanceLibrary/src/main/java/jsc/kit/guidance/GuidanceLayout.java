package jsc.kit.guidance;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class GuidanceLayout extends FrameLayout {

    private static final String TAG = "GuidanceLayout";
    private ImageView targetView;
    private GuidanceRippleView rippleViewView;
    private Rect targetRect = new Rect();
    private int curStepIndex = -1;

    public GuidanceLayout(@NonNull Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public GuidanceLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public GuidanceLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    public void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        setEnabled(false);
        targetView = new ImageView(context);
        rippleViewView = new GuidanceRippleView(context);
        rippleViewView.initAttr(context, attrs, defStyleAttr);
        addView(targetView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(rippleViewView, new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @NonNull
    public Rect getTargetRect() {
        return targetRect;
    }

    public int getCurStepIndex() {
        return curStepIndex;
    }

    public void resetStepIndex() {
        curStepIndex = -1;
    }

    /**
     * Set the click listener for {@link #targetView}.
     *
     * @param l target click listener
     */
    public void setTargetClickListener(View.OnClickListener l) {
        targetView.setOnClickListener(l);
    }

    /**
     * Remove all children except {@link #targetView} and {@link #rippleViewView}.
     */
    public void removeAllCustomViews() {
        int count = getChildCount();
        if (count > 2) {
            View[] children = new View[count - 2];
            int index = -1;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child == targetView
                        || child == rippleViewView)
                    continue;
                index++;
                children[index] = child;
            }
            for (View child : children) {
                removeView(child);
            }
        }
    }

    /**
     * Add custom view into layout.
     *
     * @param customView custom view
     * @param l1         listener for creating layout params
     * @param l2         click listener
     */
    public <V extends View> void addCustomView(@NonNull V customView, OnCustomViewAddListener<V> l1, View.OnClickListener l2) {
        if (customView.getParent() != null)
            throw new IllegalStateException("This custom view already had a parent.");
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(customView, params);
        if (l1 != null) {
            l1.onViewInit(customView, params, targetRect);
            customView.setLayoutParams(params);
            l1.onViewAdded(customView, targetRect);
        }
        customView.setOnClickListener(l2);
    }

    /**
     * @param layoutId layout id
     * @param l1       listener for creating layout params
     * @param l2       click listener
     * @param <V>      the type of custom view
     */
    public <V extends View> void addCustomView(@LayoutRes int layoutId, OnCustomViewAddListener<V> l1, View.OnClickListener l2) {
        V customView = (V) inflate(getContext(), layoutId, this);
        LayoutParams params = (LayoutParams) customView.getLayoutParams();
        if (l1 != null) {
            l1.onViewInit(customView, params, targetRect);
            customView.setLayoutParams(params);
            l1.onViewAdded(customView, targetRect);
        }
        customView.setOnClickListener(l2);
    }

    /**
     * Update the target view's location.
     *
     * @param targetView         target
     * @param l                  the left margin
     * @param t                  the top margin
     * @param rippleViewSize     the size of {@link #rippleViewView}
     * @param rippleClipToTarget true, clip {@link #rippleViewView} to {@link #targetRect} area.
     */
    public void updateTargetViewLocation(@NonNull View targetView, int l, int t, int rippleViewSize, boolean rippleClipToTarget, OnRippleViewLocationUpdatedCallback callback) {
        Bitmap bitmap = ViewDrawingCacheUtils.getDrawingCache(targetView);
        updateTargetViewLocation(bitmap, l, t, rippleViewSize, rippleClipToTarget, callback);
    }

    /**
     * Update the target view's location.
     *
     * @param targetView         target
     * @param l                  the left margin
     * @param t                  the top margin
     * @param listener           listener for initializing {@link #rippleViewView}'s size
     * @param rippleClipToTarget true, clip {@link #rippleViewView} to {@link #targetRect} area.
     */
    public void updateTargetViewLocation(@NonNull View targetView, int l, int t, OnInitRippleViewSizeListener listener, boolean rippleClipToTarget, OnRippleViewLocationUpdatedCallback callback) {
        Bitmap bitmap = ViewDrawingCacheUtils.getDrawingCache(targetView);
        int size = listener == null ? getResources().getDimensionPixelSize(R.dimen.guidance_default_ripple_size) : listener.onInitializeRippleViewSize(bitmap);
        updateTargetViewLocation(bitmap, l, t, size, rippleClipToTarget, callback);
    }

    public void updateTargetViewLocation(Bitmap bitmap, int l, int t, int rippleViewSize, boolean rippleClipToTarget, OnRippleViewLocationUpdatedCallback callback) {
        curStepIndex++;
        if (bitmap == null)
            return;
        targetRect.set(l, t, l + bitmap.getWidth(), t + bitmap.getHeight());
        ViewGroup.LayoutParams params = targetView.getLayoutParams();
        params.width = targetRect.width();
        params.height = targetRect.height();
        if (params instanceof MarginLayoutParams) {
            ((MarginLayoutParams) params).leftMargin = targetRect.left;
            ((MarginLayoutParams) params).topMargin = targetRect.top;
        }
        targetView.setLayoutParams(params);
        targetView.setImageBitmap(bitmap);
        updateRippleViewLocation(rippleViewSize, callback);
        if (rippleClipToTarget)
            rippleViewView.setClip(targetRect.width(), targetRect.height());
        else
            rippleViewView.setClip(-1, -1);
    }

    /**
     * Update ripple view's location.
     *
     * @param size     size
     * @param callback call back when the ripple view's location was updated.
     */
    private void updateRippleViewLocation(int size, OnRippleViewLocationUpdatedCallback callback) {
        if (size < 0)
            throw new IllegalArgumentException("Bad params:size is less than zero.");
        ViewGroup.LayoutParams params = rippleViewView.getLayoutParams();
        params.width = size;
        params.height = size;
        if (params instanceof MarginLayoutParams) {
            ((MarginLayoutParams) params).leftMargin = (targetRect.left + targetRect.right - size) / 2;
            ((MarginLayoutParams) params).topMargin = (targetRect.top + targetRect.bottom - size) / 2;
        }
        rippleViewView.setLayoutParams(params);
        if (callback != null)
            callback.onRippleViewLocationUpdated(rippleViewView, targetRect);
    }

    public interface OnInitRippleViewSizeListener {
        /**
         * Initialize {@link #rippleViewView}'s size.
         *
         * @param bitmap bitmap for {@link #targetView}'s drawing cache
         * @return {@link #rippleViewView}'s size
         */
        int onInitializeRippleViewSize(@NonNull Bitmap bitmap);
    }

    /**
     * listener for creating layout params
     */
    public interface OnCustomViewAddListener<V> {

        void onViewInit(@NonNull V customView, @NonNull LayoutParams params, @NonNull Rect targetRect);

        void onViewAdded(@NonNull V customView, @NonNull Rect targetRect);
    }

    /**
     * call back when the ripple view's location was updated
     */
    public interface OnRippleViewLocationUpdatedCallback {
        void onRippleViewLocationUpdated(@NonNull GuidanceRippleView rippleView, @NonNull Rect rect);
    }
}
