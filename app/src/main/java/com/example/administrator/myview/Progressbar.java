package com.example.administrator.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by Administrator on 2016/1/20.
 */
public class Progressbar extends View implements Runnable {
    private double hasdownload=0;
    private int width;
    private int height;
    private int line_color= Color.BLUE;
    private int circle_color= Color.GREEN;
    private int word_color= Color.BLACK;
    private int radius=50;
    int panleft;
    int panright;
    Paint paint=new Paint(Paint.ANTI_ALIAS_FLAG);


    @Override
    protected void onDetachedFromWindow() {

        super.onDetachedFromWindow();

    }
    public void setHasdownload(double hasdownload){
        this.hasdownload=hasdownload;

    }

    @Override
    public void run() {
        double  hasdownload=0;
        for (; ; ) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            hasdownload+=0.01;
            setHasdownload(hasdownload);
            if (hasdownload > 1) hasdownload = 0;

            this.post(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            });
        }
    }



    public Progressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public Progressbar(Context context) {
        super(context);
       init(context,null);
    }

    public Progressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context,attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
         width=getWidth();
         height=getHeight();
        panleft=getPaddingLeft();
        panright=getPaddingRight();
        int lenght=calculate();
        paint.setColor(line_color);
        paint.setStrokeWidth(10);
        canvas.drawLine(panleft, height / 2, lenght, height / 2, paint);
        paint.setColor(circle_color);
        canvas.drawCircle(calculate() + 50, height / 2, radius, paint);
        paint.setColor(Color.BLUE);
        paint.setTextSize(50);
        int pencent=(int)(hasdownload*100);
        canvas.drawText(String.valueOf(pencent) + "%", lenght + radius / 6, height / 2 + 15, paint);


    }
    private int calculate(){
        double length= hasdownload*(width-panleft-panright-2*radius);

        return (int)length;
    }



    public void init(Context context,AttributeSet attrs){
        if (attrs!=null){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.myview);
        line_color=array.getColor(R.styleable.myview_line_color, Color.BLACK);
        circle_color=array.getColor(R.styleable.myview_circle_color, Color.BLUE);
        word_color=array.getColor(R.styleable.myview_word_color, Color.BLACK);
        array.recycle();
        }
        new Thread(this).start();

    }


}
