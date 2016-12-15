package com.edu.neo.adapter;

import java.util.ArrayList;
import java.util.Iterator;

import com.edu.neo.data.TableRowData;
import com.edu.neo.utils.DataHandler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.neo.activity.R;

public class EERTableAdapter extends BaseAdapter {
	
	private ArrayList<TableRowData> dataList;
	private LayoutInflater mInflater;
	private int password_tab = 0;	//0:01478	1:014367
	
	// 验收指标
	private final double eer_five_five = 0.12;
	private final double eer_five_ten = 0.11;
	private final double eer_five_twen = 0.10;
	
	private final double eer_six_five = 0.11;
	private final double eer_six_ten = 0.10;
	private final double eer_six_twen = 0.09;
	
	final class ViewHolder {
		
		private TextView tv_user_id;
		private TextView tv_user_name;
		private TextView tv_user_eer_five;
		private TextView tv_user_eer_ten;
		private TextView tv_user_eer_twen;
		
		private void isGood(int pwdTab, double[] eers) {
			// TODO Auto-generated method stub
			int count = 0;
			if (pwdTab == 0) {
				if (eers[0] < eer_five_five) {
					tv_user_eer_five.setTextColor(Color.GREEN);
					count ++;
				}
				else {
					tv_user_eer_five.setTextColor(Color.BLACK);
				}
				if (eers[1] < eer_five_ten) {
					tv_user_eer_ten.setTextColor(Color.GREEN);
					count ++;
				}
				else {
					tv_user_eer_ten.setTextColor(Color.BLACK);
				}
				if (eers[2] < eer_five_twen) {
					tv_user_eer_twen.setTextColor(Color.GREEN);
					count ++;
				}
				else {
					tv_user_eer_twen.setTextColor(Color.BLACK);
				}
			}
			else {
				if (eers[0] < eer_six_five) {
					tv_user_eer_five.setTextColor(Color.GREEN);
					count ++;
				}
				else {
					tv_user_eer_five.setTextColor(Color.BLACK);
				}
				if (eers[1] < eer_six_ten) {
					tv_user_eer_ten.setTextColor(Color.GREEN);
					count ++;
				}
				else {
					tv_user_eer_ten.setTextColor(Color.BLACK);
				}
				if (eers[2] < eer_six_twen) {
					count ++;
					tv_user_eer_twen.setTextColor(Color.GREEN);
				}
				else {
					tv_user_eer_twen.setTextColor(Color.BLACK);
				}
			}
			if (count == 3) {
				tv_user_id.setTextColor(Color.GREEN);
				tv_user_name.setTextColor(Color.GREEN);
			}
			else {
				tv_user_id.setTextColor(Color.BLACK);
				tv_user_name.setTextColor(Color.BLACK);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public EERTableAdapter(ArrayList<TableRowData> dataList, Context context) {
		super();
		this.dataList = (ArrayList<TableRowData>) dataList.clone();
		// add average
		this.dataList.add(new TableRowData("average", averageEER()));
		
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}
	
	@SuppressWarnings("unchecked")
	public void notifyDataSetChanged(ArrayList<TableRowData> dataList, int tabId) {
		this.dataList = (ArrayList<TableRowData>) dataList.clone();
		this.dataList.add(new TableRowData("average", averageEER()));
		this.password_tab = tabId;
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.item_lv_table, null);
			viewHolder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_table_user);
			viewHolder.tv_user_id = (TextView) convertView.findViewById(R.id.tv_table_id);
			viewHolder.tv_user_eer_five = (TextView) convertView.findViewById(R.id.tv_table_eer_five);
			viewHolder.tv_user_eer_ten = (TextView) convertView.findViewById(R.id.tv_table_eer_ten);
			viewHolder.tv_user_eer_twen = (TextView) convertView.findViewById(R.id.tv_table_eer_twen);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_user_name.setText(dataList.get(position).getUsername());
		viewHolder.tv_user_id.setText(position + 1 + "");
		double[] eerVal = dataList.get(position).getEers();
		
		viewHolder.tv_user_eer_five.setText(eerVal[0] + "");
		viewHolder.tv_user_eer_ten.setText(eerVal[1] + "");
		viewHolder.tv_user_eer_twen.setText(eerVal[2] + "");
		viewHolder.isGood(password_tab, eerVal);
		return convertView;
	}
	
	private double[] averageEER() {
		double[] eers = new double[]{0, 0, 0};
		for (Iterator<TableRowData> iterator = dataList.iterator(); iterator.hasNext();) {
			TableRowData tableRowData = (TableRowData) iterator.next();
			eers[0] += tableRowData.getEers()[0];
			eers[1] += tableRowData.getEers()[1];
			eers[2] += tableRowData.getEers()[2];
		}
		for (int i = 0; i < eers.length; i++) {
			eers[i] /= dataList.size();
			eers[i] = DataHandler.format(eers[i]);
		}
		return eers;
	}

}
