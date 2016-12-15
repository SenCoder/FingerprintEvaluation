package com.edu.neo.thread;

import static com.edu.neo.utils.FileUtil.saveBitmap;
import android.graphics.Bitmap;
import android.view.View;

import com.edu.neo.utils.FileUtil;

public class MyThread extends Thread {

	private View v;
	private int position;
	private String chartType;
	
	public MyThread(View v, int posi, String chartType) {
		// TODO Auto-generated constructor stub
		this.v = v;
		this.position = posi;
		this.chartType = chartType;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		v.setDrawingCacheEnabled(true);
		Bitmap bmp = FileUtil.convertViewToBitmap(v);
		if (bmp != null) {
			saveBitmap(bmp, chartType + "_" + position);
		}
		v.setDrawingCacheEnabled(false);
	}
}
