# CameraMask
**LatestVersion**

[ ![Download](https://api.bintray.com/packages/justinquote/maven/guidance-component/images/download.svg) ](https://bintray.com/justinquote/maven/guidance-component/_latestVersion)

<a href='https://bintray.com/justinquote/maven/guidance-component?source=watch' alt='Get automatic notifications about new "guidance-component" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_greyscale.png'></a>

function guidance library and demo


Scan QRCode to download demo application below:

![](/app/src/main/res/drawable/guidance_demo_qr_code.png)

### 1、implementation
+ 1.1、Gradle
```
implementation 'jsc.kit.guidance:guidance-component:_lastVersion'
```
+ 1.2、Maven
```
<dependency>
  <groupId>jsc.kit.guidance</groupId>
  <artifactId>guidance-component</artifactId>
  <version>_lastVersion</version>
  <type>pom</type>
</dependency>
```

### 2、attrs
+ 2.1、[GuidanceRippleView](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceRippleView.java)

| 名称 | 类型 | 描述 |
|:---|:---|:---|
|`grvCount`|integer|最大波纹圈数|
|`grvSpace`|dimension|两个波纹圈之间距离间隔|
|`grvSpeed`|integer|每秒波纹动画帧数， 默认是48帧。标准的电影动画帧数是24帧|
|`grvColors`|string|每一个波纹圈的颜色，格式如：`{#008577, #D81B60, #00574B}`|
|`grvAutoRun`|boolean|是否开启自动播放|

+ 2.2、[GuidanceLayout](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceLayout.java)

| 子View | 类型 | 属性 |
|:---|:---|:---|
|`rippleViewView`|[GuidanceRippleView](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceRippleView.java)|[GuidanceRippleView](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceRippleView.java)所有属性|

### 3、usage
+ 3.1、[GuidanceRippleView](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceRippleView.java):
```
        <jsc.kit.guidance.GuidanceRippleView
            android:id="@+id/ripple_view_4"
            android:layout_width="@dimen/space_192"
            android:layout_height="wrap_content"
            android:layout_marginTop="@dimen/space_8"
            app:grvColors="{#008577, #D81B60, #00574B}"
            app:grvCount="5"
            app:grvSpeed="36" />
```

+ 3.2、[GuidanceLayout](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceLayout.java):
```
    <jsc.kit.guidance.GuidanceLayout
        android:id="@+id/guidance_layout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#99000000"
        app:grvColors="{#D81B60}"
        app:grvSpace="8dp"
        app:grvSpeed="24" />
```

+ 3.3、[GuidanceDialog](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceDialog.java):
```
    private void showGuidanceDialog() {
        final GuidanceDialog dialog = new GuidanceDialog(getContext());
        dialog.setTargetClickListener(new OnTargetClickListener() {
            @Override
            public boolean onTargetClick(GuidanceLayout layout) {
                Toast.makeText(layout.getContext(), "clicked me", Toast.LENGTH_SHORT).show();
                switch (layout.getCurStepIndex()) {
                    case 0:
                        showStep(layout, R.id.item_layout_1);
                        return true;
                    case 1:
                        showStep(layout, R.id.item_layout_2);
                        return true;
                    case 2:
                        showStep(layout, R.id.item_layout_3);
                        return true;
                    default:
                        return false;
                }
            }
        });
        dialog.show();
        GuidanceLayout guidanceLayout = dialog.getGuidanceLayout();
        if (guidanceLayout == null)
            return;
        showStep(guidanceLayout, R.id.item_layout_0);
    }
```
+ 3.3、[GuidancePopupWindow](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidancePopupWindow.java):
```
    private void showContentGuidance() {
        final GuidancePopupWindow popupWindow = new GuidancePopupWindow(getActivity());
        popupWindow.setTargetClickListener(new OnTargetClickListener() {
            @Override
            public boolean onTargetClick(GuidanceLayout layout) {
                Toast.makeText(layout.getContext(), "clicked me", Toast.LENGTH_SHORT).show();
                switch (layout.getCurStepIndex()) {
                    case 0:
                        layout.removeAllCustomViews();
                        showStep(layout, R.id.item_layout_1);
                        return true;
                    case 1:
                        layout.removeAllCustomViews();
                        showStep(layout, R.id.item_layout_2);
                        return true;
                    case 2:
                        layout.removeAllCustomViews();
                        showStep(layout, R.id.item_layout_3);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popupWindow.show();
        GuidanceLayout guidanceLayout = popupWindow.getGuidanceLayout();
        showStep(guidanceLayout, R.id.item_layout_0);
    }
```
```
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
                location[1] - statusBarHeight,
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
```

| 组件 | 使用示例 |
|:---|:---|
|[GuidanceRippleView](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceRippleView.java)|[GuidanceRippleViewFragment](/app/src/main/java/jsc/exam/com/guidance/fragments/GuidanceRippleViewFragment.java)|
|[GuidanceLayout](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceLayout.java)|[GuidanceLayoutFragment](/app/src/main/java/jsc/exam/com/guidance/fragments/GuidanceLayoutFragment.java)|
|[GuidanceDialog](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceDialog.java)|[GuidanceDialogFragment](/app/src/main/java/jsc/exam/com/guidance/fragments/GuidanceDialogFragment.java)|
|[GuidancePopupWindow](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidancePopupWindow.java)|[GuidancePopupWindowFragment](/app/src/main/java/jsc/exam/com/guidance/fragments/GuidancePopupWindowFragment.java)|

### 4、Screenshots
+ 4.1、[GuidanceRippleView](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceRippleView.java)

![GuidanceRippleView](/output/shots/guidance_ripple_view_s.png)

+ 4.2、[GuidanceLayout](/guidanceLibrary/src/main/java/jsc/kit/guidance/GuidanceLayout.java)

![GuidanceLayout](/output/shots/guidance_layout_s.png)

### 5、release log

##### version:0.1.0
+ 1、create project

### LICENSE
```
   Copyright 2018 JustinRoom

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
