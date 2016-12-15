package com.edu.neo.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.renderer.LineChartRenderer;

public class MyLineChart extends LineChart {

	public MyLineChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void init() {
		// TODO Auto-generated method stub
		super.init();
		setRenderer(new LineChartRenderer(this, mAnimator, mViewPortHandler));
	}

	public void showChart(int position, LineData lineData) {
		
		setDrawBorders(false);	//是否在折线图上添加边框
//		setDescription("");// no description text
//		setDescriptionPosition(getWidth() - mViewPortHandler.offsetRight() - 10
//				, getHeight() - mViewPortHandler.offsetBottom() - 10);
		setNoDataTextDescription("You need to provide data for the chart.");
		
		// enable/disable grid background
		setDrawGridBackground(true); // 是否显示表格颜色
		setGridBackgroundColor(Color.WHITE);
		
		setGridBackgroundColor(Color.WHITE & 0x80FFFFFF); // 表格的的颜色，在这里是是给颜色设置一个透明度
		
		setTouchEnabled(true);
		setDragEnabled(false);
		setScaleEnabled(false);
		// if disabled, scaling can be done on x- and y-axis separately
		setPinchZoom(false);
		
		setData(lineData);
		// get the legend (only possible after setting data)
		Legend mLegend = getLegend();
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
		mLegend.setForm(LegendForm.CIRCLE);// 样式
		mLegend.setFormSize(6f);	//字体
		mLegend.setPosition(LegendPosition.BELOW_CHART_LEFT);
		mLegend.setTextColor(Color.BLACK);	//颜色
//		mLegend.setTypeface(mTf);//字体
		if (position%4 == 1) {
			setDescription("FAR");
		}
		else if (position%4 == 2) {
			setDescription("FRR");
		}
		else {
			setDescription("ROC");
		}
	}
}
