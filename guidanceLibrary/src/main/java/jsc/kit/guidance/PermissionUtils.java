package jsc.kit.guidance;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class PermissionUtils {

    public static boolean checkOverlayPermission(@NonNull Fragment fragment, int requestCode){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if (!Settings.canDrawOverlays(fragment.getContext())) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + fragment.getContext().getPackageName()));
            fragment.startActivityForResult(intent, requestCode);
        }
        return true;
    }

    public static boolean checkOverlayPermission(@NonNull FragmentActivity activity, int requestCode){
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if (!Settings.canDrawOverlays(activity)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, requestCode);
        }
        return true;
    }
}
