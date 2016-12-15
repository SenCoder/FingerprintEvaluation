package com.edu.neo.consts;

import java.util.ArrayList;

import android.os.Environment;

public class MyConst {

	public static final String SD_PATH = Environment.getExternalStorageDirectory() + "/";
	public static final String WORKSPACE = SD_PATH + "Pin_Authentication";
	public static final String RESULT_DIR = "Pin_Result";
	public static int TRAIN_NUM = 10;	//ÑµÁ·´ÎÊý
	
	public static ArrayList<String> userList;
	
}
