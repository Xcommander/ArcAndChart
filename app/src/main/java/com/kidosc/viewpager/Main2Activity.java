package com.kidosc.viewpager;

import android.app.Activity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.kidosc.viewpager.Chart.MyBarChart;
import com.kidosc.viewpager.model.Data;

import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends Activity {
    private MyBarChart mBarChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mBarChart = findViewById(R.id.bar_chart);
        initData();


    }

    private void initData() {
        Data[] datas = {new Data(1, 2), new Data(2, 3), new Data(3, 4)};

        List<BarEntry> barEntries = new ArrayList<>();
        for (Data d :
                datas) {
            barEntries.add(new BarEntry(d.getX(), d.getY()));

        }

        BarDataSet barDataSet = new BarDataSet(barEntries, "test");
        barDataSet.setColors(new int[]{getColor(R.color.colorPrimaryDark)
                , getColor(R.color.colorAccent)
                , getColor(R.color.current_step_color)});
        BarData barData = new BarData(barDataSet);
        mBarChart.setData(barData);



        //每个状图的值，不显示
        barData.setDrawValues(false);
        //设置柱形图的宽度
        barData.setBarWidth(0.3f);




        //禁止触摸
        mBarChart.setTouchEnabled(false);
        mBarChart.setDragEnabled(false);
        //去掉描述
        Description description=new Description();
        description.setEnabled(false);
        mBarChart.setDescription(description);






        //关于X轴的设置
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setEnabled(false);


        //关于left-Y轴的设置
        YAxis leftYAxis = mBarChart.getAxisLeft();
        leftYAxis.setEnabled(false);
        leftYAxis.setAxisMaximum(5);
        leftYAxis.setAxisMinimum(1);

        //关于right-Y轴的设置

        YAxis rightYAxis = mBarChart.getAxisRight();
        rightYAxis.setEnabled(false);


        //去掉图例
        mBarChart.getLegend().setEnabled(false);
        mBarChart.invalidate();
    }
}
