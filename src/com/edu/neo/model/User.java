package com.edu.neo.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.edu.neo.classify.KNN;
import com.edu.neo.utils.DataHandler;
import com.edu.neo.utils.FileUtil;

/**
 * Created by Yunpeng on 15/10/17.
 */

public class User {
    private String username = "";
    private String password = "";
    private static final String TAG = "User";
    public KNN model = null;
    private int modelSize = 0;
    private boolean isAutoLearning = false;
    private double threshold = 1.41;

    private Set<Integer> featureIndexSet = null;

    private int featureSize = 10;
    private double[] weights;
    private double[] predictHistory;
    
    // for a unique user and unique password
    public User(String username, String password) {
		// TODO Auto-generated constructor stub
    	this.username = username;
    	this.password = password;
	}

    public int getFeatureSize() {
        return featureSize;
    }

    public void setFeatureSize(int featureSize) {
        this.featureSize = featureSize;
    }

    public Set<Integer> getFeatureIndexSet() {
        return featureIndexSet;
    }

    private static double[] pvalues = {
            766.267489293950,5175,788.982672178949,178.526787116376,225.809797284565,243.423241951754,608.036787969833,631.594631559441,192.812785440160,635.208999495299,1155.42321861164,1331.60937699031,81.2357591768035,114.550261959348,100.492582228455,587.789664539970,704.298066260126,89.4245760796788,628.007997763782,982.470167850892,1326.01960335482,311.058637852311,460.894510143650,385.009382471847,643.334721974585,672.378108583282,340.791327758711,640.269206172105,736.166031576355,1022.02315383711,557.276921602143,523.596292840421,478.826109155931,523.893196992084,542.490513738546,574.977063559623,540.041279408081,896.335047707811,1117.89218080349,437.119123382350,541.791965174250,390.341947784792,564.691059467453,602.765252935229,422.967915959882,582.857617524846,875.589284522498,1239.54928665874,573.484333017494,584.674555145760,584.794288542525,606.875480194375,657.656223437973,601.237212172027,611.533639175143,980.996802972754,1305.30595957091
    };

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public boolean isAutoLearning() {
        return isAutoLearning;
    }

    public void setIsAutoLearning(boolean isAutoLearning) {
        this.isAutoLearning = isAutoLearning;
    }

    public int getModelSize() {
        return modelSize;
    }

    public void setModelSize(int modelSize) {
        this.modelSize = modelSize;
    }

//    static User instance = new User();
//    public static User shareInstance() {
//        return instance;
//    }

    public void login(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    // load model for giving training data.
    public void loadModel(List<AuthEvent> authEventsForTrain) {
    	fetchFeatureIndexSet(authEventsForTrain);
    	if (password.length()!= 0) {
            model = new KNN(password.length() + 1, this);
            List<AuthEvent> authEvents = authEventsForTrain;
            for (AuthEvent authEvent:authEvents) {
                trainByAuthEvent(authEvent);
            }
//            Log.d(TAG,"重新加载样本,训练样本:"+authEvents.size());
        }
    	// the above part in this function is doing something like data pre-processing and integration  
    	// change parameter threshold rather than fix it with User.Threshold = 1.41
//        oneOutCrossValidation(threshold);
	}
    
    public void oneOutCrossValidation(double threshold) {
    	model.leaveOneOutCrossValidation(threshold);// 产生每个模型的阈值
    }

    public void reloadModel() {
//        System.out.println("重新加载模型");
//        fetchFeatureIndexSet(); // 特征筛选
//        System.out.println("特征:"+featureIndexSet.toString());
        if (password.length()!= 0) {
            model = new KNN(password.length() + 1, this);
            ArrayList<AuthEvent> authEvents = FileUtil.loadRawData(username,password);
            for (AuthEvent authEvent:authEvents) {
                trainByAuthEvent(authEvent);
            }
//            Log.d(TAG,"重新加载样本,训练样本:"+authEvents.size());
        }
        model.leaveOneOutCrossValidation(threshold);// 产生每个模型的阈值
    }

    private void fetchFeatureIndexSet(List<AuthEvent> authEvents) {
        featureIndexSet = new HashSet<>();
        featureIndexSet.clear();
//        ArrayList<AuthEvent> authEvents = FileUtil.loadRawData(username, password);
        
        double[][] data = new double[authEvents.size()][];
        double[] withinClassScatter = new double[pvalues.length];
        for (int index =0; index<password.length();index ++ ) {
            for (int i = 0; i < authEvents.size(); i++) {
                data[i] = authEvents.get(i).getTouchEvents().get(index).allFeatureVector();
            }
            for (int k =0;k<data[0].length;k++) {
                double[] column = fetchColumn(data, k);
                double v = DataHandler.varianceValueOf(column);
                if (v == 0) {
                    if (DataHandler.meanValueOf(column) == 0) {
                        // 数据全为0, 则v置为-1
                        v = -1;
                    }
                }
                withinClassScatter[k] += v;
            }
        }
        for (int k=0;k<withinClassScatter.length;k++) {
            withinClassScatter[k] /= password.length();
        }
//        System.out.println("withinClassScatter="+Arrays.toString(withinClassScatter));
        double[] weight = new double[pvalues.length];
        for (int i=0;i<pvalues.length;i++) {
            weight[i] = withinClassScatter[i] * pvalues[i];
            if (weight[i] < 0) {
                weight[i] = Double.MAX_VALUE;
            }
        }
        // add at 11-26-16-27
        weight = pvalues;
//        System.out.println("weight="+Arrays.toString(weight));

        for (int i=0;i<featureSize;i++) {
            int minIndex = 0;
            for (int j=0;j<weight.length;j++) {
                if (featureIndexSet.contains(j)) {
//                    System.out.println("已添加"+j);
                    continue;
                }
                if (weight[minIndex]>weight[j]) {
                    minIndex = j;
                }
            }
            featureIndexSet.add(minIndex);
//            System.out.println("添加本次最小值的位置"+minIndex);
        }
    }

    private static double[] fetchColumn(double[][] x,int column) {
        double[]singleData = new double[x.length];
        for (int i=0;i<x.length;i++){
            singleData[i] = x[i][column];
        }
        return singleData;
    }


    public void updatePassword(String password) {
        this.password = password;
//        model = new KNN(password.length()+1);
        model = new KNN(password.length()+1, this);
        weights = new double[password.length()+1];
        predictHistory = new double[password.length()+1];
        for (int i = 0; i < predictHistory.length; i++) {
			predictHistory[i] = 1.0;
		}
//        Log.d(TAG, "模型初始化完成");
    }

    // train for one time
    public boolean trainByAuthEvent(AuthEvent authEvent) {
        if (authEvent.getTouchEvents().size() == password.length()) {
//            Log.d(TAG,"认证序列长度正常");
        } else {
        	// we already have feature selection mechanism, why we need this ?
            Log.d(TAG,"del 导致训练序列长度异常,取消此次训练");
            return false;
        }
        for(int index = 0; index<password.length();index++) {
            TouchEvent touchEvent = authEvent.getTouchEvents().get(index);
//            Log.d(TAG, "训练" + index + "个模型");
            try {
            	// get feature vector from touch event sequences, including size, pressure and sensor data.
                double [] vector = touchEvent.featureVector(featureIndexSet);
                // train model from vectors for each index.
                model.train(vector, index);
            } catch (Exception e) {
                Log.d(TAG,"数据异常，结束本次训练");
                return true;
            }
            //model.train(fetchVector(touchEvent),index);
        }
//        Log.d(TAG,"训练时间模型");
        // train model from time data
        model.train(authEvent.getTimeVector(), password.length());
//        Log.d(TAG,"此次训练完毕");
        return true;
    }

    // 由 AuthEvent.predict() 方法调用
    public double predictByAuthEvent(AuthEvent authEvent) {
        if (authEvent.getTouchEvents().size() == password.length()) {
//            Log.d(TAG,"认证序列长度正常");
        } else {
            Log.d(TAG,"认证序列长度异常,取消此次认证");
            return - 1.0;
        }
        double[] result = new double[password.length()+1];
        for(int index = 0; index<password.length();index++) {
            TouchEvent touchEvent = authEvent.getTouchEvents().get(index);
//            Log.d(TAG,"正在匹配第"+index+"个模型");
            try {
                double [] vector = touchEvent.featureVector(featureIndexSet);
                result[index] = model.predict(vector,index);
            } catch (Exception e) {
                Log.d(TAG,"数据异常，跳过此次匹配");
                result[index] = 0;
            }
//          result[index] = model.predict(fetchVector(touchEvent),index);
        }
//        Log.d(TAG,"正在匹配时间模型");
        model.setMethod(KNN.ComputationalMethod.kEuclideanDistance);
        result[password.length()] = model.predict(authEvent.getTimeVector(),password.length());
        model.setMethod(KNN.ComputationalMethod.kManhattanDistance);
        
        double p = 0;
//        Log.d(TAG, "预测结果:" + Arrays.toString(result));
        if (isAutoLearning) {
        	updateWeights(result);
        	double r =0;
        	for (int i=0;i<weights.length;i++) {
        		r += result[i] * weights[i];
        	}
        	p = r;
        } else {
        	double count=0;
        	for (double r:result) {
        		count += r;
        	}
        	p = count/(password.length()+1);
        }
        return p;
//        return "判定结果:"+(p>0.6?"真":"假")+"\n"+ p;
    }

    public void updateWeights(double[] x) {
        double min, max, mean, standardDev;
        mean = DataHandler.meanValueOf(x);
        standardDev = DataHandler.standardValueOf(x);
        min = mean - 3 * standardDev;
        max = mean + 3 * standardDev;
        for (int i=0;i<x.length;i++) {
            if (x[i]<max && x[i]>min) {
                predictHistory[i] += 1.0;
            }
        }
        for (int i=0;i<weights.length;i++) {
//            weights[i] =
        }

        double sum = 0;
        for (double t:predictHistory) {
            sum += t;
        }
        for (int i=0;i<weights.length;i++) {
            weights[i] = predictHistory[i]/ sum;
        }
    }

    public int trainingTimes() {
        return FileUtil.getTrainingTimes(username,password);
    }

    public void logout() {
        username = "";
        password = "";
        model = null;
    }

}
