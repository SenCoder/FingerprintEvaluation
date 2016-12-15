package com.edu.neo.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.edu.neo.consts.MyConst;
import com.edu.neo.model.AuthEvent;
import com.edu.neo.model.TouchEvent;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class FileUtil {
	
	private static final String TAG = "FileHelper";
    public static final String SD_PATH = Environment.getExternalStorageDirectory() + "/";
    public static final String APPLICATION = "Pin_Authentication";
    public static final String APP_PATH = SD_PATH + APPLICATION + "/";
//    public static  final  String APP_PATH = "/Users/Yunpeng/Pin_Authentication/";
    public static final String TRAINING_DATA_Dir = "training";
    public static final String PREDICT_DATA_Dir = "predict";
    public static final String PREDICT_TRUE_DATA_Dir = "predict/true";
    public static final String PREDICT_FALSE_DATA_Dir = "predict/false";
    public static final String FEATURE_Dir = "feature";
    public static final String MODEL_Dir = "model";
    
    public static boolean checkUserDir(String username){
        return creatDir(username.trim());
    }
	
    /*
     * Sheng Yuan 
     * */
	public static File creatSDDir(String dirName) {
		File dir = new File(SD_PATH + dirName);
		dir.mkdirs();	//File.mkdirs()会判断自动判断文件夹是否已存在
		return dir;
	}
	
	/*
     * Sheng Yuan 
     * */
	public static File creatSDFile(String dirName, String fileName) {
		
		File file = new File(creatSDDir(dirName) + "/"+ fileName);
		try {
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	/*
     * Sheng Yuan 
     * */
	@SuppressLint("SimpleDateFormat")
	public static void saveBitmap(Bitmap bmp, String filename) {

		File f = creatSDFile("photo", filename + ".png");
		try {
			FileOutputStream out = new FileOutputStream(f);
			bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
	
	/*
     * Sheng Yuan 
     * */
	public static Bitmap convertViewToBitmap(View view){

		view.buildDrawingCache();
		Bitmap bitmap = view.getDrawingCache();
        return bitmap;
	}

    public static ArrayList<AuthEvent> loadRawData(String username,String password) {
        String path = APP_PATH+username+"/"+TRAINING_DATA_Dir+"/";
        File file = new File(path);
        ArrayList<AuthEvent> authEvents = new ArrayList<>();

        if (file.exists() && file.isDirectory()) {
            for (File tmp : file.listFiles()) {
                if (!tmp.getName().contains(password)) continue;
                try {
                    System.out.println("name :"+ tmp.getName());
                    BufferedReader reader = new BufferedReader(new FileReader(tmp));
                    String line  = reader.readLine();
                    if (line!=null) {
                        int size = Integer.valueOf(line);
                        if (size>0) {
                            ArrayList<TouchEvent> touchEvents = new ArrayList<>();
                            for (int i=0;i<size;i++) {
                                String key = reader.readLine();
                                ArrayList<ArrayList<Double>> data = new ArrayList<>();
                                for (int j=0;j<9;j++) {
                                    String[] strings = reader.readLine().split(" ");
                                    ArrayList<Double> nums = new ArrayList<>();
                                    for (String str : strings) {
                                        nums.add(Double.valueOf(str));
                                    }
                                    data.add(nums);
                                }
                                double startTime = data.get(0).get(0);
                                double endTime = data.get(0).get(1);

                                ArrayList<Double> sizeList = data.get(1);
                                ArrayList<Double> pressureList = data.get(2);

                                ArrayList<double[]> accelerometerDataList = new ArrayList<>();
                                for (int j=0;j<data.get(3).size();j++) {
                                    double [] a = {
                                            data.get(3).get(j),
                                            data.get(4).get(j),
                                            data.get(5).get(j),
                                    };
                                    accelerometerDataList.add(a);
                                }


                                ArrayList<double[]> gyroscopeDataList = new ArrayList<>();
                                for (int j=0;j<data.get(6).size();j++) {
                                    double [] a = {
                                            data.get(6).get(j),
                                            data.get(7).get(j),
                                            data.get(8).get(j),
                                    };
                                    gyroscopeDataList.add(a);
                                }

                                TouchEvent touchEvent = new TouchEvent(key,startTime,endTime,sizeList,pressureList,accelerometerDataList,gyroscopeDataList);
                                touchEvents.add(touchEvent);
                            }
                            // maybe a data filter is needed
                            if (touchEvents.size() == password.length()) {
                            	authEvents.add(new AuthEvent(touchEvents));
							}
                        }
                    }
                    reader.close();
                } catch (Exception e) {
//                    e.printStackTrace();
                    System.out.println(username + ": current data abnormal");
                    continue;
                }
            }
        }
        return authEvents;

    }

    public static void move(String username,String password,String from,String to) {
        String fromPath = APP_PATH+username+"/"+from+"/";
        String toPath = APP_PATH +username+ "/"+ to+ "/";
        File file = new File(fromPath);
//        int count = 0;
        if (file.exists() && file.isDirectory()) {
            for (File tmp : file.listFiles()) {
                if (tmp.getName().contains(password)) {
                    tmp.renameTo(new File(toPath+tmp.getName()));
                }
            }
        }
    }

    public static void clearFolder(String username,String password,String dir)  {
        String path = APP_PATH+username+"/"+dir+"/";
        File file = new File(path);
        if(!file.exists()) {
            return;
        }
        if(!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for(int i = 0; i < tempList.length; i++)  {
            if  (path.endsWith(File.separator))  {
                temp = new File(path  +  tempList[i]);
            } else {
                temp = new File(path  +  File.separator  +  tempList[i]);
            }
            if(temp.isFile()) {
                if (temp.getName().contains(password)) {
                    temp.delete();
                }
            }
        }
    }

    public static int getFileCount(String username, String password,String dir) {
        String path = APP_PATH + username+"/"+dir+"/";
        return countFiles(path,password);
    }

    public static int getTrainingTimes(String username, String password) {
        String path = APP_PATH + username+"/"+TRAINING_DATA_Dir+"/";
        return countFiles(path,password);
    }

    private static int countFiles(String path,String key) {
        File file = new File(path);
        int count = 0;
        if (file.exists() && file.isDirectory()) {
            for (File tmp : file.listFiles()) {
                if (tmp.getName().contains(key)) {
                    count++;
                }
            }
        }
        Log.d(TAG,"文件个数"+count);
        return count;
    }

    private static boolean creatDir(String username) {
        File file = new File(APP_PATH + username+"/");

        File list[] = {
                new File(APP_PATH+username+"/"+TRAINING_DATA_Dir+"/"),
                new File(APP_PATH+username+"/"+PREDICT_DATA_Dir+"/"),
                new File(APP_PATH+username+"/"+FEATURE_Dir+"/"),
                new File(APP_PATH+username+"/"+MODEL_Dir+"/"),
                new File(APP_PATH+username+"/"+PREDICT_TRUE_DATA_Dir+"/"),
                new File(APP_PATH+username+"/"+PREDICT_FALSE_DATA_Dir+"/")
        };

        Log.d(TAG,"Path:"+APP_PATH+username+"/");
        if (!file.exists()) {
            if(file.mkdirs()) {
//                Log.d(TAG,"用户创建成功");
                for (File f:list) {
                    if (!f.exists()) {
                        f.mkdirs();
                    }
                }
                return true;
            } else {
                Log.d(TAG,"用户创建失败");
                return false;
            }
        }
//        Log.d(TAG,"目录已存在");
        Log.d(TAG,"用户根目录生成完毕");
        return true;
    }
    
    public static ArrayList<String> searchUsers(String path) {
    	
		File file = new File(MyConst.SD_PATH + path);
		if (!file.exists()) {
			
			System.out.println("no data in dataspace");
			return null;
		}
		return new ArrayList<String>(Arrays.asList(file.list()));
	}
	
}
