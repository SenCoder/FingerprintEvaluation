package com.edu.neo.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.edu.neo.activity.R;
import com.edu.neo.adapter.EERTableAdapter;
import com.edu.neo.data.EerData;
import com.edu.neo.data.TableRowData;

public class TableFragment extends Fragment {

	public static RadioGroup rg_top_btns;
	private ListView lv_table;
	private EERTableAdapter adapter;
	private ArrayList<TableRowData> dataListFive;
	private ArrayList<TableRowData> dataListSix;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fm_output_table, container, false);
		
		setTabSelection(0);
		lv_table = (ListView) v.findViewById(R.id.lv_eer_table);
		rg_top_btns = (RadioGroup) v.findViewById(R.id.rg_top_btns);
		rg_top_btns.setOnCheckedChangeListener(new OnCheckList());
		
		adapter = new EERTableAdapter(dataListFive, getActivity());
		lv_table.setAdapter(adapter);
		
		return v;
	}
	
	public class OnCheckList implements RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.btn_show_five:
				setTabSelection(0);
				adapter.notifyDataSetChanged(dataListFive, 0);
				break;
			case R.id.btn_show_six:
				setTabSelection(1);
				adapter.notifyDataSetChanged(dataListSix, 1);
				break;
			}
		}
	}
	
	/**
	 * 
	 * @param index
	 */
	private void setTabSelection(int index) {
		if(index == 0) {
			if(dataListFive == null) {
				dataListFive = new EerData("01478").makeTableData();
//				tableReport(dataListFive, "01478-report.pdf");
			}
			
		}
		else {
			if(dataListSix == null) {
				dataListSix = new EerData("014367").makeTableData();
//				tableReport(dataListSix, "014367-report.pdf");
			}
			
		}
	}
	/*
	private void tableReport(ArrayList<TableRowData> tableList, String filename) {
		PdfUtil.makePdf(tableList, filename);
	}*/
}
