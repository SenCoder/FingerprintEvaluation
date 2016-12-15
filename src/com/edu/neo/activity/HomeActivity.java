package com.edu.neo.activity;

import java.util.ArrayList;

import com.edu.neo.adapter.MyFragmentPagerAdapter;
import com.edu.neo.fragment.RadarChartFragment;
import com.edu.neo.fragment.TableFragment;
import com.edu.neo.fragment.UserListFragment;
import com.edu.neo.service.EvaluateService;
import com.github.mikephil.charting.utils.Utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class HomeActivity extends FragmentActivity {

	private ViewPager mViewPager;
	private RadioGroup rg_buttons;
	private RadioButton rb_list_user, rb_radar_chart, rb_eer_table;
	private ArrayList<Fragment> fragments;
	
	private FragmentManager fm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ac_home);
		
		// Initial drawing.
		Utils.init(this);
		
		mViewPager = (ViewPager) findViewById(R.id.m_view_pager);
		rg_buttons = (RadioGroup) findViewById(R.id.rg_bottom_btns);
		rb_list_user = (RadioButton) findViewById(R.id.rbtn_list_all);
		rb_radar_chart = (RadioButton) findViewById(R.id.rbtn_rdchart_eer);
		rb_eer_table = (RadioButton) findViewById(R.id.rbtn_table_eer);
		
		fm = getSupportFragmentManager();
		
		fragments = new ArrayList<>();
		fragments.add(new UserListFragment());
		fragments.add(new RadarChartFragment());
		fragments.add(new TableFragment());
		
		mViewPager.setAdapter(new MyFragmentPagerAdapter(fm, fragments));
		mViewPager.setCurrentItem(0);
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		
		rb_list_user.setOnClickListener(new MyOnClickListener());
		rb_radar_chart.setOnClickListener(new MyOnClickListener());
		rb_eer_table.setOnClickListener(new MyOnClickListener());
		
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("是否进行后台计算服务 ？");
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, EvaluateService.class);
				startService(intent);
				dialog.dismiss();
				Toast.makeText(HomeActivity.this, "Background service running", Toast.LENGTH_SHORT).show();
			}
		});
		
		AlertDialog dialog = builder.create();  
		dialog.show();
		dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			switch (arg0) {
			case 0:
				rg_buttons.check(R.id.rbtn_list_all);
				break;
			case 1:
				rg_buttons.check(R.id.rbtn_rdchart_eer);
				break;
			case 2:
				rg_buttons.check(R.id.rbtn_table_eer);
				break;
			}
		}
	}
	
	private class MyOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.rbtn_list_all:
				mViewPager.setCurrentItem(0);
				break;
			case R.id.rbtn_rdchart_eer:
				mViewPager.setCurrentItem(1);
				break;
			case R.id.rbtn_table_eer:
				mViewPager.setCurrentItem(2);
				break;
			}
		}
	}
	
}

	//使用 OnCheckedChangeListener 会导致循环调用的问题，改用  OnClickLIstener
//	private class OnCheckListener implements RadioGroup.OnCheckedChangeListener {
//
//		@Override
//		public void onCheckedChanged(RadioGroup group, int checkedId) {
//			switch (checkedId) {
//			case R.id.btn_list_all:
//				mViewPager.setCurrentItem(0);
//				System.out.println(">> " + checkedId);
//				break;
//			case R.id.btn_rdchart_eer:
//				mViewPager.setCurrentItem(1);
//				System.out.println(">> " + checkedId);
//				break;
//			case R.id.btn_table_eer:
//				mViewPager.setCurrentItem(2);
//				System.out.println(">> " + checkedId);
//				break;
//			}
//		}
//	}

