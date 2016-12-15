package com.edu.neo.fragment;

import java.util.ArrayList;
import java.util.Locale;

import com.edu.neo.activity.PlotActivity;
import com.edu.neo.activity.R;
import com.edu.neo.adapter.UsListAdapter;
import com.edu.neo.consts.MyConst;
import com.edu.neo.utils.FileUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class UserListFragment extends Fragment {

	EditText et_name_input;
	ListView lv_user_list;
	private ArrayList<String> puList;
	private UsListAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fm_user_list, container, false);
		et_name_input = (EditText) v.findViewById(R.id.et_search_user);
		et_name_input.addTextChangedListener(watcher);
		MyConst.userList = puList = FileUtil.searchUsers(MyConst.RESULT_DIR);
		lv_user_list = (ListView) v.findViewById(R.id.lv_list_user);
		adapter = new UsListAdapter(puList, getActivity());
		lv_user_list.setAdapter(adapter);
		lv_user_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), PlotActivity.class);
				intent.putExtra("username", puList.get(arg2));
				startActivity(intent);
			}
		});
		return v;
	}
	
	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			adapter.notifyDataSetChanged(subListByHead(arg0.toString()));
		}
	    
	};
	
	private ArrayList<String> subListByHead(String header) {
		Locale defloc = Locale.getDefault();
		String temp = header.toLowerCase(defloc);
		String TEMP = header.toLowerCase(defloc);
		ArrayList<String> mList = new ArrayList<>();
		for (int i = 0; i < puList.size(); i++) {
			String name = puList.get(i);
			if (name.startsWith(temp) || name.startsWith(TEMP)) {
				mList.add(name);
			}
		}
		return mList;
	}
	
//	private void searchUsers(String path) {
//		File file = new File(MyConst.SD_PATH + path);
//		if (!file.exists()) {
//			puList = new ArrayList<String>();
//			Log.d("xxx", "no data");
//			return ;
//		}
//		puList = new ArrayList<String>(Arrays.asList(file.list()));
//	}
	
}
