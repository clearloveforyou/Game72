package com.greenhand.game73.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;


import com.greenhand.game73.R;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * project: Game73
 * package: com.greenhand.game73.custom
 * author: HouShengLi
 * time: 2017/3/25 00:30
 * e-mail:13967189624@163.com
 * description: 自定义计数器
 * 自定义view步骤：
 *        1.自定义View首先要实现一个继承自View的类
 *        2.一般来说给一个两个参数的构造器（下面有说明）
 *        3.重写onDraw，（onMeasure）等（onDraw主要是绘制，onMeasure:主要是我们这view为：wrapContent时要测量）
 *        4.如果自定义的View有自己的属性，需要在values下建立attrs.xml文件，在其中定义属性
 *        5.在布局文件中用的时候要加命名空间：xmlns:my="http://schemas.android.com/apk/res/demo.view.my"
 *          其中xmlns后的“my”是自定义的属性的前缀，res后的是我们自定义View所在的包
 *
 *
 */
public class CounterView extends View {

    private String text;
    private int color;
    private int size;
    private Paint mPaint;
    private Rect f;
    private RadialGradient mRadialGradient;

    /**
     * 第一属于程序内实例化时采用，之传入Context即可
     * @param context
     */
    public CounterView(Context context) {
        this(context,null);
    }

    /**
     * 第二个用于layout文件实例化，会把XML内的参数通过AttributeSet带入到View内。
     * @param context
     * @param attrs
     */
    public CounterView(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    /**
     * 第三个主题的style信息，也会从XML里带入
     * 现在还有第四个参数了…以后可能还会有更多
     *一般继承View类，知晓熟悉使用前两个方法即可，后面的都很少用到
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CounterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取我们自定义的属性
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CounterView, defStyleAttr, 0);
        int n = typedArray.getIndexCount();

        for (int i = 0;i<n;i++){
            int attr = typedArray.getIndex(i);
            switch (attr){

                case R.styleable.CounterView_text:
                    text = typedArray.getString(attr);
                    break;
                case R.styleable.CounterView_color:
                    color = typedArray.getInt(attr, Color.BLACK);//默认返回黑色
                    break;
                case R.styleable.CounterView_textSize:
                    // 默认设置为16sp，TypeValue也可以把sp转化为px
                    size = typedArray.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics()));
                    break;
            }
        }

        //设置文字
        initLabelView();
        typedArray.recycle();//一定要调用，否则这次的设定会对下次的使用造成影响

    }

    private void initLabelView() {

        mPaint = new Paint();
        f = new Rect();
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        //获得文字的宽高
        mPaint.getTextBounds(text,0,text.length(),f);
    }

    /**
     * 测量自定义控件的长宽
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     *
     *
     * 1.一般来说，自定义控件都会去重写View的onMeasure方法，因为该方法指定该控件在屏幕上的大小。
         protected void onMeasure (int widthMeasureSpec, int heightMeasureSpec)

       2.onMeasure传入的两个参数是由上一层控件传入的大小，有多种情况，重写该方法时需要对计算控件的实际大小，
         然后调用setMeasuredDimension(int, int)设置实际大小。
       3.onMeasure传入的widthMeasureSpec和heightMeasureSpec不是一般的尺寸数值，
         而是将模式和尺寸组合在一起的数值。我们需要通过:
           int mode = MeasureSpec.getMode(widthMeasureSpec)得到模式，
           int size = MeasureSpec.getSize(widthMeasureSpec)得到尺寸。
       4.mode共有三种取值情况：MeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY, MeasureSpec.AT_MOST
            MeasureSpec.EXACTLY:是精确尺寸
                                当我们将控件的layout_width或layout_height指定为具体数值时如andorid:layout_width="50dip"，
                                或者为FILL_PARENT是，都是控件大小已经确定的情况，都是精确尺寸。
            MeasureSpec.AT_MOST:是最大尺寸，当控件的layout_width或layout_height指定为WRAP_CONTENT时，
                                控件大小一般随着控件的子空间或内容进行变化，此时控件尺寸只要不超过父控件允许的最大尺寸即可。
                                因此，此时的mode是AT_MOST，size给出了父控件允许的最大尺寸。
            MeasureSpec.UNSPECIFIED：是未指定尺寸，
                                     这种情况不多，一般都是父控件是AdapterView，通过measure方法传入的模式。
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height ;
        if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        } else
        {
            mPaint.setTextSize(size);
            mPaint.getTextBounds(text, 0, text.length(), f);
            float textWidth = f.width();
            //文字两边的pading距离
            int desired = (int) (getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;
        }

        if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        } else
        {
            mPaint.setTextSize(size);
            mPaint.getTextBounds(text, 0, text.length(), f);
            float textHeight = f.height();
            int desired = (int) (getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }


        //设置实际大小
        setMeasuredDimension(width, height);

    }

    /**
     * 绘制：画笔Paint 画布Canvas
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setStyle(Paint.Style.FILL); //设置填充
        mPaint.setAlpha(100);

        mPaint.setColor(Color.YELLOW);
        if (mRadialGradient != null) {
            mPaint.setShader(mRadialGradient);
        }
        canvas.drawCircle(0,0,500,mPaint);
        mPaint.setColor(color);
        canvas.drawText(text, getWidth() / 2 - f.width() / 2, getHeight() / 2 + f.height() / 2, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // @设置alpha通道（透明度）
        //水波效果
        mPaint.setAlpha(400);
        mRadialGradient = new RadialGradient(event.getX(), event.getY(), 48,
                new int[] { Color.WHITE, Color.TRANSPARENT },null, Shader.TileMode.REPEAT);
        //
        text = randomText();
        // @重绘
        postInvalidate();

        return true;
    }

    private  String randomText()
    {
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4)
        {
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set)
        {
            sb.append("" + i);
        }

        return sb.toString();
    }
}
