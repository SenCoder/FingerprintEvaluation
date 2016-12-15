package com.edu.neo.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;

public class MyRadarChart extends RadarChart {

	private RadarData mRadarData;
	protected Typeface tf;
	private Context mContext;
	
	public MyRadarChart(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public void setChartView(int position, RadarData radarData) {
		
		tf = Typeface.createFromAsset(mContext.getAssets(), "OpenSans-Regular.ttf");
		if (position == 0) {
			setDescription("EER for password 01478");
		}
		else {
			setDescription("EER for password 014367");
		}
		setDescriptionTextSize(12.0f);
		setDescriptionTypeface(tf);
		setWebLineWidth(1.5f);
		setWebLineWidthInner(0.75f);
		setWebAlpha(100);
		setRotationEnabled(false);
        
		setRadarData(radarData);
        setData(mRadarData);
        invalidate();
        
        XAxis xAxis = getXAxis();
        xAxis.setTypeface(tf);
        xAxis.setTextSize(9f);
        YAxis yAxis = getYAxis();
        yAxis.setTypeface(tf);
        yAxis.setLabelCount(5, false);
        yAxis.setTextSize(9f);
        yAxis.setStartAtZero(true);

        Legend lg = getLegend();
        lg.setPosition(LegendPosition.RIGHT_OF_CHART);
        lg.setTypeface(tf);
        lg.setXEntrySpace(7f);
        lg.setYEntrySpace(5f);
		
	}

	public void setRadarData(RadarData radarData) {
		
		mRadarData = radarData;
    }
}
