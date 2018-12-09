package jsc.kit.guidance;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class GuidancePopupWindow {

    public static final int SHOW_IN_CONTENT = 0x10;
    public static final int SHOW_IN_WINDOW = 0x11;

    @IntDef({SHOW_IN_CONTENT, SHOW_IN_WINDOW})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ShowType {
    }

    private Activity activity;
    private GuidanceLayout guidanceLayout;
    private int curShowType = -1;
    private OnTargetClickListener listener = null;

    public GuidancePopupWindow(@NonNull Activity activity) {
        this(activity, 0x99000000);
    }

    public GuidancePopupWindow(@NonNull Activity activity, @ColorInt int backgroundColor) {
        this.activity = activity;
        guidanceLayout = new GuidanceLayout(activity);
        guidanceLayout.setId(R.id.guidance_default_layout_id);
        guidanceLayout.setBackgroundColor(backgroundColor);
        guidanceLayout.setTargetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null || !listener.onTargetClick(guidanceLayout))
                    dismiss();
            }
        });
    }

    public void show() {
        show(SHOW_IN_CONTENT);
    }

    public void show(@ShowType int showType) {
        this.curShowType = showType;
        switch (curShowType) {
            case SHOW_IN_CONTENT:
                FrameLayout contentLayout = activity.findViewById(android.R.id.content);
                if (!isGuidanceLayoutAdded(guidanceLayout)) {
                    contentLayout.addView(guidanceLayout);
                }
                break;
            case SHOW_IN_WINDOW:
                WindowManager.LayoutParams params = new WindowManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                }
                activity.getWindow().getWindowManager().addView(guidanceLayout, params);
                break;
        }
    }

    public void dismiss() {
        switch (curShowType) {
            case SHOW_IN_CONTENT:
                FrameLayout contentLayout = activity.findViewById(android.R.id.content);
                contentLayout.removeView(guidanceLayout);
                break;
            case SHOW_IN_WINDOW:
                activity.getWindow().getWindowManager().removeView(guidanceLayout);
                break;
        }
    }

    @NonNull
    public GuidanceLayout getGuidanceLayout() {
        return guidanceLayout;
    }

    public void setTargetClickListener(OnTargetClickListener listener) {
        this.listener = listener;
    }

    private boolean isGuidanceLayoutAdded(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View child = viewGroup.getChildAt(i);
            if (child == guidanceLayout)
                return true;
        }
        return false;
    }
}
