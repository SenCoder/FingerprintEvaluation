package com.edu.neo.data;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

import com.edu.neo.consts.MyConst;
import com.edu.neo.model.User;
import com.edu.neo.utils.DataHandler;
import com.edu.neo.utils.FileUtil;

/*
 * This class stands for a file to be stored.
 * 
 */

public class EvaluationUnit {

	String userName;
	String password;
	int train_times;
	public double eer;
	double[] thresholds;
	public ArrayList<Double> fars;
	public ArrayList<Double> frrs;
	
	public EvaluationUnit(User user, double[] thresholds, int train_times) {
		super();
		this.userName = user.getUsername();
		this.password = user.getPassword();
		this.thresholds = thresholds;
		this.train_times = train_times;
		fars = new ArrayList<>();
		frrs = new ArrayList<>();
	}
	
	public void saveToTxt() {
		setEer();
		File file = FileUtil.creatSDFile(MyConst.RESULT_DIR + "/" + userName, password + "_" + train_times + ".txt");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(DataHandler.format(eer) + "\n");
			for (int i = 0; i < thresholds.length; i++) {
				bw.write(DataHandler.format(thresholds[i]) + " ");	
			}
			bw.newLine();
			for (int i = 0; i < thresholds.length; i++) {
				bw.write(DataHandler.format(fars.get(i)) + " ");				
			}
			bw.newLine();
			for (int i = 0; i < thresholds.length; i++) {
				bw.write(DataHandler.format(frrs.get(i)) + " ");				
			}
			bw.close();
			Log.d("progress", userName + " >>> " + password + "_" + train_times + ".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setEer() {
		for (int i = 0; i + 1 < thresholds.length; i++) {
			if (fars.get(i) < frrs.get(i) && fars.get(i+1) > frrs.get(i+1)) {
				if (Math.sqrt(fars.get(i) - frrs.get(i)) < Math.sqrt(fars.get(i+1) - frrs.get(i+1))) {
					eer = (fars.get(i) + frrs.get(i)) /2;
				}
				else {
					eer = (fars.get(i+1) + frrs.get(i+1)) /2;
				}
				break;
			}
		}
	}
}
