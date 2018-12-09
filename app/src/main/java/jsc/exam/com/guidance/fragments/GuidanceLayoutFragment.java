package jsc.exam.com.guidance.fragments;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import jsc.exam.com.guidance.R;
import jsc.kit.guidance.GuidanceLayout;
import jsc.kit.guidance.GuidanceRippleView;
import jsc.kit.guidance.ViewDrawingCacheUtils;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class GuidanceLayoutFragment extends BaseFragment {

    Button btnShowAgain;
    GuidanceLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guidance_layout, container, false);
        btnShowAgain = root.findViewById(R.id.btn_show_again);
        layout = root.findViewById(R.id.guidance_layout);
        layout.setTargetClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(layout.getContext(), "clicked me", Toast.LENGTH_SHORT).show();
                switch (layout.getCurStepIndex()) {
                    case 0:
                        showStep(layout, R.id.item_layout_1);
                        break;
                    case 1:
                        showStep(layout, R.id.item_layout_2);
                        break;
                    case 2:
                        showStep(layout, R.id.item_layout_3);
                        break;
                    default:
                        layout.setVisibility(View.GONE);
                        break;
                }
            }
        });
        btnShowAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.setVisibility(View.VISIBLE);
                layout.resetStepIndex();
                showStep(layout, R.id.item_layout_0);
            }
        });
        return root;
    }

    @Override
    void onLoadData(Context context) {
        layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                showStep(layout, R.id.item_layout_0);
            }
        }, 100);
    }

    private void showStep(GuidanceLayout layout, int targetViewId) {
        layout.removeAllCustomViews();
        showStep(layout, getView().findViewById(targetViewId));
    }

    private void showStep(GuidanceLayout guidanceLayout, View target) {
        Context context = guidanceLayout.getContext();
        int statusBarHeight = ViewDrawingCacheUtils.getStatusBarHeight(context);
        int actionBarHeight = ViewDrawingCacheUtils.getActionBarSize(context);
        int[] location = ViewDrawingCacheUtils.getWindowLocation(target);
        guidanceLayout.updateTargetViewLocation(
                target, location[0],
                location[1] - statusBarHeight - actionBarHeight,
                new GuidanceLayout.OnInitRippleViewSizeListener() {
                    @Override
                    public int onInitializeRippleViewSize(@NonNull Bitmap bitmap) {
                        return bitmap.getHeight();
                    }
                },
                true,
                new GuidanceLayout.OnRippleViewLocationUpdatedCallback() {
                    @Override
                    public void onRippleViewLocationUpdated(@NonNull GuidanceRippleView rippleView, @NonNull Rect targetRect) {

                    }
                });

        ImageView imageView = new ImageView(guidanceLayout.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageResource(R.drawable.hand_o_up);
        guidanceLayout.addCustomView(imageView, new GuidanceLayout.OnCustomViewAddListener<ImageView>() {

            @Override
            public void onViewInit(@NonNull ImageView customView, @NonNull FrameLayout.LayoutParams params, @NonNull Rect targetRect) {
                customView.measure(0, 0);
                params.topMargin = targetRect.bottom + 12;
                params.leftMargin = targetRect.left - (customView.getMeasuredWidth() - targetRect.width()) / 2;
            }

            @Override
            public void onViewAdded(@NonNull ImageView customView, @NonNull Rect targetRect) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(customView, View.TRANSLATION_Y, 0, 32, 0)
                        .setDuration(1200);
                animator.setRepeatCount(-1);
                animator.start();
            }
        }, null);
    }
}
