package com.kidosc.viewpager.Chart;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;

/**
 * @author JasonXu
 * @date 2018/10/28
 */
public class MyBarChart extends BarChart {
    public MyBarChart(Context context) {
        super(context);
    }

    public MyBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBarChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void init() {
        super.init();
        mRenderer = new MyBarChartRenderer(this, mAnimator, mViewPortHandler);
    }
}
