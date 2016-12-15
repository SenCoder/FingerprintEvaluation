package com.edu.neo.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.edu.neo.activity.R;
import com.edu.neo.view.MyRadarChart;
import com.github.mikephil.charting.data.RadarData;

public class RadarListAdapter extends BaseAdapter {

	private ArrayList<RadarData> dataList;
	private LayoutInflater mInflater;
	
	private class ViewHolder {
		MyRadarChart rdChart;
	}
	
	public RadarListAdapter(Context context, ArrayList<RadarData> dataList) {
		// TODO Auto-generated constructor stub
		this.mInflater = LayoutInflater.from(context);
		this.dataList = dataList;
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (arg1 == null) {
			viewHolder = new ViewHolder();
			arg1 = mInflater.inflate(R.layout.item_lv_rader, null);
			viewHolder.rdChart = (MyRadarChart) arg1.findViewById(R.id.rd_chart);
			arg1.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		viewHolder.rdChart.setChartView(arg0, dataList.get(arg0));

//		new MyThread(arg1, arg0, "radar").start();
		
		return arg1;
	}

}
