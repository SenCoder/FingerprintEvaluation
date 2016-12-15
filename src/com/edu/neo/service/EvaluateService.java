package com.edu.neo.service;

import com.edu.neo.evaluation.Evaluation;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class EvaluateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		super.onStartCommand(intent, flags, startId);
		System.out.println("here >>");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				new Evaluation().evaluate();
			}
		}).start();
		return START_STICKY;
	}
}
