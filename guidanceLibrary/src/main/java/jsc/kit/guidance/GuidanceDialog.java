package jsc.kit.guidance;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class GuidanceDialog extends AppCompatDialog {

    private GuidanceLayout guidanceLayout;
    private int width = ViewGroup.LayoutParams.MATCH_PARENT;
    private int height = ViewGroup.LayoutParams.MATCH_PARENT;
    private OnTargetClickListener listener = null;

    public GuidanceDialog(@NonNull Context context) {
        super(context);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    public GuidanceDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        guidanceLayout = new GuidanceLayout(getContext());
        guidanceLayout.setTargetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener == null || !listener.onTargetClick(guidanceLayout))
                    dismiss();
            }
        });
        setContentView(guidanceLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (getWindow() != null) {
            //设置window背景，默认的背景会有Padding值，不能全屏。当然不一定要是透明，你可以设置其他背景，替换默认的背景即可。
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //一定要在setContentView之后调用，否则无效
            getWindow().setLayout(width, height);
        }
    }

    public void setTargetClickListener(OnTargetClickListener listener) {
        this.listener = listener;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Nullable
    public GuidanceLayout getGuidanceLayout() {
        return guidanceLayout;
    }
}
