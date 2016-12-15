package com.edu.neo.adapter;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.edu.neo.activity.R;

public class UsListAdapter extends BaseAdapter {

	private ArrayList<String> puList;
	private LayoutInflater mInflater;
	
	private final class ViewHolder {
		public TextView tv_username;
	}
	
	public UsListAdapter(ArrayList<String> puList, Context context) {
		super();
		this.puList = puList;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return puList.size();
	}
	
	public void notifyDataSetChanged(ArrayList<String> dataList) {
		this.puList = dataList;
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
			convertView = mInflater.inflate(R.layout.item_lv_users, null);
			viewHolder.tv_username = (TextView) convertView.findViewById(R.id.tv_user_name);
			convertView.setTag(viewHolder);
		}
		else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_username.setText(puList.get(position));
		
		return convertView;
	}

}
