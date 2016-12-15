package com.edu.neo.adapter;

import java.util.ArrayList;

import com.edu.neo.activity.PlotActivity;
import com.edu.neo.activity.R;
import com.edu.neo.data.LineChartData;
import com.edu.neo.view.MyLineChart;
import com.github.mikephil.charting.data.LineData;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LineChartListAdapter extends BaseAdapter {

	private Context mContext;
	@SuppressWarnings("rawtypes")
	private ArrayList dataList;
	protected Typeface tf;
	private TextView textView;
	private MyLineChart lineChart;
	
	public LineChartListAdapter(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
		dataList = new LineChartData(((PlotActivity)mContext).mUser).dataForAdapter();
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return dataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isEnabled(int position) {
		// TODO Auto-generated method stub
		if(position % 4 == 0) { 
            return false;
        }
        return super.isEnabled(position);
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if(arg0 % 4 != 0) {
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.item_lv_linechart, null);
			lineChart = (MyLineChart) arg1.findViewById(R.id.line_chart);
			lineChart.showChart(arg0, (LineData) dataList.get(arg0));
//			new MyThread(arg1, arg0, "-linechart").start();
		}
		else {
			arg1 = LayoutInflater.from(mContext).inflate(R.layout.item_lv_linechart_grouptitle, null);
			textView = (TextView) arg1.findViewById(R.id.linechart_title_group);
			textView.setText((String) dataList.get(arg0));
		}
		return arg1;
	}

}
