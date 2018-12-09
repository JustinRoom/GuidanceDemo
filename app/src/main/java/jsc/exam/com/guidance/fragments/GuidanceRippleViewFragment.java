package jsc.exam.com.guidance.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jsc.exam.com.guidance.R;
import jsc.kit.guidance.GuidanceRippleView;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class GuidanceRippleViewFragment extends BaseFragment {

    GuidanceRippleView rippleView1;
    GuidanceRippleView rippleView2;
    GuidanceRippleView rippleView3;
    GuidanceRippleView rippleView4;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_guidance_ripple_view, container, false);
        rippleView1 = root.findViewById(R.id.ripple_view_1);
        rippleView2 = root.findViewById(R.id.ripple_view_2);
        rippleView3 = root.findViewById(R.id.ripple_view_3);
        rippleView4 = root.findViewById(R.id.ripple_view_4);

        root.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleView1.start();
                rippleView2.start();
                rippleView3.start();
                rippleView4.start();
                getView().findViewById(R.id.btn_start).setEnabled(false);
                getView().findViewById(R.id.btn_pause).setEnabled(true);
                getView().findViewById(R.id.btn_resume).setEnabled(false);
                getView().findViewById(R.id.btn_stop).setEnabled(true);
            }
        });
        root.findViewById(R.id.btn_pause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleView1.pause();
                rippleView2.pause();
                rippleView3.pause();
                rippleView4.pause();
                getView().findViewById(R.id.btn_start).setEnabled(false);
                getView().findViewById(R.id.btn_pause).setEnabled(false);
                getView().findViewById(R.id.btn_resume).setEnabled(true);
                getView().findViewById(R.id.btn_stop).setEnabled(true);
            }
        });
        root.findViewById(R.id.btn_resume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleView1.resume();
                rippleView2.resume();
                rippleView3.resume();
                rippleView4.resume();
                getView().findViewById(R.id.btn_start).setEnabled(false);
                getView().findViewById(R.id.btn_pause).setEnabled(true);
                getView().findViewById(R.id.btn_resume).setEnabled(false);
                getView().findViewById(R.id.btn_stop).setEnabled(true);
            }
        });
        root.findViewById(R.id.btn_stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleView1.stop();
                rippleView2.stop();
                rippleView3.stop();
                rippleView4.stop();
                getView().findViewById(R.id.btn_start).setEnabled(true);
                getView().findViewById(R.id.btn_pause).setEnabled(false);
                getView().findViewById(R.id.btn_resume).setEnabled(false);
                getView().findViewById(R.id.btn_stop).setEnabled(false);
            }
        });
        return root;
    }

    @Override
    void onLoadData(Context context) {

    }
}
