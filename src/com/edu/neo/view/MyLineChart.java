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
		
		setDrawBorders(false);	//�Ƿ�������ͼ����ӱ߿�
//		setDescription("");// no description text
//		setDescriptionPosition(getWidth() - mViewPortHandler.offsetRight() - 10
//				, getHeight() - mViewPortHandler.offsetBottom() - 10);
		setNoDataTextDescription("You need to provide data for the chart.");
		
		// enable/disable grid background
		setDrawGridBackground(true); // �Ƿ���ʾ�����ɫ
		setGridBackgroundColor(Color.WHITE);
		
		setGridBackgroundColor(Color.WHITE & 0x80FFFFFF); // ���ĵ���ɫ�����������Ǹ���ɫ����һ��͸����
		
		setTouchEnabled(true);
		setDragEnabled(false);
		setScaleEnabled(false);
		// if disabled, scaling can be done on x- and y-axis separately
		setPinchZoom(false);
		
		setData(lineData);
		// get the legend (only possible after setting data)
		Legend mLegend = getLegend();
		// mLegend.setPosition(LegendPosition.LEFT_OF_CHART);
		mLegend.setForm(LegendForm.CIRCLE);// ��ʽ
		mLegend.setFormSize(6f);	//����
		mLegend.setPosition(LegendPosition.BELOW_CHART_LEFT);
		mLegend.setTextColor(Color.BLACK);	//��ɫ
//		mLegend.setTypeface(mTf);//����
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
