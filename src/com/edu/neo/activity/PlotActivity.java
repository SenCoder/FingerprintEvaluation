package com.edu.neo.activity;

import com.edu.neo.adapter.LineChartListAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class PlotActivity extends Activity {

	public String mUser;
	
	private ListView lv_linechart;
	private LineChartListAdapter adapter;
//	private ArrayList<LineData> dataList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_plot);
		
		lv_linechart = (ListView) findViewById(R.id.lv_line_chart);
		mUser = getIntent().getStringExtra("username");
		adapter = new LineChartListAdapter(this);
		lv_linechart.setAdapter(adapter);
	}

}
