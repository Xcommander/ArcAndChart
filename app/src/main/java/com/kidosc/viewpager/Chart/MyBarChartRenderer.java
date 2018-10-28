package com.kidosc.viewpager.Chart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.buffer.BarBuffer;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @author JasonXu
 * @date 2018/10/28
 */
public class MyBarChartRenderer extends BarChartRenderer {
    public MyBarChartRenderer(BarDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);

    }

    private RectF mBarShadowRectBuffer = new RectF();

    @Override
    protected void drawDataSet(Canvas c, IBarDataSet dataSet, int index) {

        Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());

        mBarBorderPaint.setColor(dataSet.getBarBorderColor());
        mBarBorderPaint.setStrokeWidth(Utils.convertDpToPixel(dataSet.getBarBorderWidth()));

        final boolean drawBorder = dataSet.getBarBorderWidth() > 0.f;

        float phaseX = mAnimator.getPhaseX();
        float phaseY = mAnimator.getPhaseY();

        // draw the bar shadow before the values
        if (mChart.isDrawBarShadowEnabled()) {
            mShadowPaint.setColor(dataSet.getBarShadowColor());

            BarData barData = mChart.getBarData();

            final float barWidth = barData.getBarWidth();
            final float barWidthHalf = barWidth / 2.0f;
            float x;

            for (int i = 0, count = Math.min((int) (Math.ceil((float) (dataSet.getEntryCount()) * phaseX)), dataSet.getEntryCount());
                 i < count;
                 i++) {
                BarEntry e = dataSet.getEntryForIndex(i);
                x = e.getX();
                mBarShadowRectBuffer.left = x - barWidthHalf;
                mBarShadowRectBuffer.right = x + barWidthHalf;
                trans.rectValueToPixel(mBarShadowRectBuffer);
                if (!mViewPortHandler.isInBoundsLeft(mBarShadowRectBuffer.right)) {
                    continue;
                }
                if (!mViewPortHandler.isInBoundsRight(mBarShadowRectBuffer.left)) {
                    break;
                }
                mBarShadowRectBuffer.top = mViewPortHandler.contentTop();
                mBarShadowRectBuffer.bottom = mViewPortHandler.contentBottom();
                c.drawRect(mBarShadowRectBuffer, mShadowPaint);
            }
        }
        // initialize the buffer
        BarBuffer buffer = mBarBuffers[index];
        buffer.setPhases(phaseX, phaseY);
        buffer.setDataSet(index);
        buffer.setInverted(mChart.isInverted(dataSet.getAxisDependency()));
        buffer.setBarWidth(mChart.getBarData().getBarWidth());
        buffer.feed(dataSet);
        trans.pointValuesToPixel(buffer.buffer);
        final boolean isSingleColor = dataSet.getColors().size() == 1;
        if (isSingleColor) {
            mRenderPaint.setColor(dataSet.getColor());
        }
        for (int j = 0; j < buffer.size(); j += 4) {
            if (!mViewPortHandler.isInBoundsLeft(buffer.buffer[j + 2])) {
                continue;
            }
            if (!mViewPortHandler.isInBoundsRight(buffer.buffer[j])) {
                break;
            }
            //isSingleColor是不是只有一个颜色  防止不需要渐变色的柱状图
            //下面是向画笔添加渐变的的不同的级别颜色不同,这边的额渐变色还需要变化
            if (!isSingleColor) {
                if (dataSet.getEntryForIndex(j / 4).getY() > 140) {
                    Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#F37779"),
                            Color.parseColor("#DA1738"), Shader.TileMode.CLAMP);
                    mRenderPaint.setShader(shader);
                } else if (dataSet.getEntryForIndex(j / 4).getY() < 30) {
                    Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#70AAFD"),
                            Color.parseColor("#0D70FC"), Shader.TileMode.CLAMP);
                    mRenderPaint.setShader(shader);
                } else {
                    Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#71A4A7"),
                            Color.parseColor("#0E9355"), Shader.TileMode.CLAMP);
                    mRenderPaint.setShader(shader);
                }
            } else {
                Shader shader = new LinearGradient(0, buffer.buffer[j + 1], 0, buffer.buffer[j + 3], Color.parseColor("#70AAFD"),
                        Color.parseColor("#0D70FC"), Shader.TileMode.CLAMP);
                mRenderPaint.setShader(shader);
            }
            //修改画笔的属性 是否圆头
            mRenderPaint.setStrokeCap(Paint.Cap.ROUND);
            float wtch = buffer.buffer[j + 2] - buffer.buffer[j];
            mRenderPaint.setStrokeWidth(wtch);
            c.drawLine(buffer.buffer[j] + wtch / 2, buffer.buffer[j + 1], buffer.buffer[j + 2] - wtch / 2,
                    buffer.buffer[j + 3], mRenderPaint);
            if (drawBorder) {
                c.drawLine(buffer.buffer[j] + wtch / 2, buffer.buffer[j + 1], buffer.buffer[j + 2] - wtch / 2,
                        buffer.buffer[j + 3], mRenderPaint);
            }
        }
    }
}
