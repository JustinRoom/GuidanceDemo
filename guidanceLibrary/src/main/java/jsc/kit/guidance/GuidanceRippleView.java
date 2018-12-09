package jsc.kit.guidance;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <br>Email:1006368252@qq.com
 * <br>QQ:1006368252
 * <br><a href="https://github.com/JustinRoom/GuidanceDemo" target="_blank">https://github.com/JustinRoom/GuidanceDemo</a>
 *
 * @author jiangshicheng
 */
public class GuidanceRippleView extends View {

    private Paint paint;
    private int count;
    private int[] colors;
    private float space;
    private float speed;
    private boolean autoRun;

    private int frameCountPerSecond = 0;
    private float radius = 0;
    private boolean isRunning = false;
    private int clipWidth;
    private int clipHeight;

    public GuidanceRippleView(Context context) {
        super(context);
        initAttr(context, null, 0);
    }

    public GuidanceRippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs, 0);
    }

    public GuidanceRippleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs, defStyleAttr);
    }

    public void initAttr(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GuidanceRippleView, defStyleAttr, 0);
        count = a.getInteger(R.styleable.GuidanceRippleView_grvCount, 3);
        space = a.getDimension(R.styleable.GuidanceRippleView_grvSpace, 0);
        frameCountPerSecond = a.getInteger(R.styleable.GuidanceRippleView_grvSpeed, 48);
        autoRun = a.getBoolean(R.styleable.GuidanceRippleView_grvAutoRun, true);
        String colors = a.getString(R.styleable.GuidanceRippleView_grvColors);
        a.recycle();
        if (count < 1)
            throw new IllegalArgumentException("count must be more than one.");
        if (frameCountPerSecond < 1)
            throw new IllegalArgumentException("speed should be one frame per second at least.");
        this.colors = new int[count];
        if (colors != null) {
            colors = colors.trim();
            colors = colors.replace("{", "");
            colors = colors.replace("}", "");
            colors = colors.replace("(", "");
            colors = colors.replace(")", "");
            String[] colorSplit = colors.split(",");
            for (int i = 0; i < count; i++) {
                if (colorSplit.length > 0)
                    this.colors[i] = Color.parseColor(colorSplit[i % colorSplit.length].trim());
                else
                    this.colors[i] = Color.BLUE;
            }
        } else {
            for (int i = 0; i < count; i++) {
                this.colors[i] = Color.BLUE;
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        if (space <= 0) {
            space = getMeasuredWidth() * 1.0f / (count * 2);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        speed = -1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (speed <= 0) {
            speed = space / frameCountPerSecond;
        }

        if (clipWidth > 0 && clipHeight > 0) {
            int clipLeft = (getWidth() - clipWidth) / 2;
            int clipTop = (getHeight() - clipHeight) / 2;
            canvas.clipRect(clipLeft, clipTop, clipLeft + clipWidth, clipTop + clipHeight);
        }

        float maxRadius = getWidth() / 2.0f;
        int alpha = (int) (0xFF * (1 - radius / maxRadius) + .5f);
        for (int i = 0; i < count; i++) {
            paint.setColor(colors[i]);
            paint.setAlpha(alpha);
            float tempRadius = radius - space * i;
            if (tempRadius > 0)
                canvas.drawCircle(maxRadius, maxRadius, tempRadius, paint);
        }
        radius += speed;
        if (radius > maxRadius)
            radius = 0;
        if (isRunning)
            invalidate();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (autoRun)
            start();
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }

    public void start() {
        if (isRunning)
            return;
        radius = 0;
        isRunning = true;
        invalidate();
    }

    public void stop() {
        radius = 0;
        isRunning = false;
        invalidate();
    }

    public void pause() {
        isRunning = false;
    }

    public void resume() {
        isRunning = true;
        invalidate();
    }

    /**
     * Whether run animation automatically when {@link #onAttachedToWindow()}.
     *
     * @param autoRun true, run animation automatically when {@link #onAttachedToWindow()}.
     */
    public void setAutoRun(boolean autoRun) {
        this.autoRun = autoRun;
    }

    /**
     * Set the ripple animation display area.
     *
     * @param clipWidth  clip width
     * @param clipHeight clip height
     */
    public void setClip(int clipWidth, int clipHeight) {
        this.clipWidth = clipWidth;
        this.clipHeight = clipHeight;
    }

    /**
     * @param count               ripple circle count
     * @param colors              colors
     * @param space               the radius offset of two ripple circle
     * @param frameCountPerSecond the speed of ripple animation
     */
    public void updateAnimation(int count, float space, int frameCountPerSecond, int... colors) {
        if (count < 1)
            throw new IllegalArgumentException("count must be more than one.");
        if (frameCountPerSecond < 1)
            throw new IllegalArgumentException("speed should be one frame per second at least.");

        this.colors = new int[count];
        if (colors == null || colors.length == 0)
            colors = new int[]{Color.BLUE};
        for (int i = 0; i < count; i++) {
            this.colors[i] = colors[i % colors.length];
        }
        this.count = count;
        this.space = space;
        this.speed = -1;

    }
}
