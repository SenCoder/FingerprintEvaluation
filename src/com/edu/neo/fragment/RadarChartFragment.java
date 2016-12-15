package com.edu.neo.fragment;

import java.util.ArrayList;

import com.edu.neo.activity.R;
import com.edu.neo.adapter.RadarListAdapter;
import com.edu.neo.data.EerData;
import com.github.mikephil.charting.data.RadarData;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RadarChartFragment extends Fragment {

	protected String mDescribe;
	
	private ListView lv_radar_chart;
	private RadarListAdapter adapter;
	private ArrayList<RadarData> dataList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fm_radar_chart, container, false);
		lv_radar_chart = (ListView) v.findViewById(R.id.lv_radar_chart);
		
		setRadarDataList();
		/**/
		return v;
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		adapter = new RadarListAdapter(getActivity(), dataList);
		lv_radar_chart.setAdapter(adapter);
	}

	public void setRadarDataList() {
		if(dataList == null)
			dataList = new ArrayList<>();
//		for (int i = 0; i < 6; i++) {
		dataList.add(new EerData("01478").makeRadarData());
		dataList.add(new EerData("014367").makeRadarData());
//		}
    }

}
