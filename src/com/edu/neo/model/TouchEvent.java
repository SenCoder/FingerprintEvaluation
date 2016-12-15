package com.edu.neo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.edu.neo.utils.ArrayHandler;
import com.edu.neo.utils.DataHandler;

/**
 * Created by Yunpeng on 15/10/17.
 */
public class TouchEvent {
//    private static final String TAG = "TouchEvent";
    private String key;

    public String getKey() {
        return key;
    }

    public ArrayList<Double> getSizeList() {
        return sizeList;
    }

    public ArrayList<Double> getPressureList() {
        return pressureList;
    }

    public ArrayList<double[]> getAccelerometerDataList() {
        return accelerometerDataList;
    }

    public ArrayList<double[]> getGyroscopeDataList() {
        return gyroscopeDataList;
    }

    private ArrayList<Double> sizeList = new ArrayList<>();
    private ArrayList<Double> pressureList = new ArrayList<>();
    private ArrayList<double[]> accelerometerDataList;
    private ArrayList<double[]> gyroscopeDataList;

    private double startTime;
    private double endTime;


    public TouchEvent(String key,
                      double startTime,
                      double endTime,
                      ArrayList<Double> sizeList,
                      ArrayList<Double> pressureList,
                      ArrayList<double[]> accelerometerDataList,
                      ArrayList<double[]> gyroscopeDataList) {
        this.key = key;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sizeList = new ArrayList<>(sizeList);
        this.pressureList = new ArrayList<>(pressureList);
        this.accelerometerDataList = new ArrayList<>(accelerometerDataList);
        this.gyroscopeDataList = new ArrayList<>(gyroscopeDataList);
    }

    public double getTimeLength(){
        return endTime-startTime;
    }

    public double getStartTime() {
        return startTime;
    }
    public double getEndTime() {
        return endTime;
    }

    public double[][] matrix = new double[8][9];
    /**                    0  1    2   3            4               5       6   7           8
     *                   mean min max VARIANCE  quartileDeviation Median range skewness kurtosis
     *  [

     *
     *
     *accelerometer.x                   *           *                      *
     *accelerometer.y
     *accelerometer.z
     *
     *
     *      Gravity.x
     *      Gravity.y          *37      *39          *40                   *42
     *      Gravity.z
     *  ]
     *
     */
//    private static boolean isFilter = true;

    public double[] allFeatureVector() {
        double[] vector = null;
        double[] baseVector = {
                getTimeLength(),
                DataHandler.meanValueOf(DataHandler.trasferToArray(sizeList)),
                DataHandler.meanValueOf(DataHandler.trasferToArray(pressureList)),

        };
//            double[] sizeVector = DataHandler.meanValueOf(DataHandler.trasferToArray(sizeList));

//            double[] sizVector = countValue(DataHandler.trasferToArray(sizeList));
//            double[] pressureVector = countValue(DataHandler.trasferToArray(pressureList));

        double[][] acclerometerDatas = ArrayHandler.trasferTo2DArray(accelerometerDataList);
        double[] acclerometerXVector = countValue(acclerometerDatas[0]);
        double[] acclerometerYVector = countValue(acclerometerDatas[1]);
        double[] acclerometerZVector = countValue(acclerometerDatas[2]);


        double[][] gyroscopeDatas = ArrayHandler.trasferTo2DArray(gyroscopeDataList);
        double[] gyroscopeXVector = countValue(gyroscopeDatas[0]);
        double[] gyroscopeYVector = countValue(gyroscopeDatas[1]);
        double[] gyroscopeZVector = countValue(gyroscopeDatas[2]);

        vector = DataHandler.concatAll(baseVector, acclerometerXVector, acclerometerYVector, acclerometerZVector,
                gyroscopeXVector, gyroscopeYVector, gyroscopeZVector);
        return vector;
    }


    public double[] featureVector(Set<Integer> filter){
        List<Integer> filterList = new ArrayList<Integer>(filter);
        double[] vector = allFeatureVector();
        double [] filterVector = new double[filter.size()];
        for (int i=0;i<filter.size();i++) {
            int index = filterList.get(i);
            filterVector[i] = vector[index];
        }
        return filterVector;
    }


    public static final int MEAN = 0;                   //ƽ��ֵ
    public static final int MIN = 1;                    //��Сֵ
    public static final int MAX = 2;                    //���ֵ
    public static final int VARIANCE = 3;               //����
    public static final int QUARTILE_DEVIATION = 4;     //�ķ�λ��
    public static final int MEDIAN = 5;                 //��λ��
    public static final int RANGE=6;                    //����
    public static final int SKEWNESS=7;                 //ƫ��
    public static final int KURTOSIS=8;                 //���

    private double[] countValue(double[] values){

        double[] matrix = new double[9];
        matrix[MEAN] = DataHandler.meanValueOf(values);
        matrix[MIN] = DataHandler.minValueOf(values);
        matrix[MAX] = DataHandler.maxValueOf(values);
        matrix[VARIANCE] = DataHandler.varianceValueOf(values);
        matrix[QUARTILE_DEVIATION] = DataHandler.quartileDeviationValueOf(values);
        matrix[MEDIAN] =DataHandler.medianValueOf(values);
        matrix[RANGE] = matrix[MAX] - matrix[MIN];
        if (values.length < 4) {
//            System.out.println("��������,�޷������Ⱥ�ƫ��");
            matrix[SKEWNESS] = 0;
            matrix[KURTOSIS] = 0;
        } else {
            matrix[SKEWNESS] = DataHandler.skewnessValueOf(values);
            matrix[KURTOSIS] = DataHandler.kurtosis(values);
        }
        return matrix;
    }


}
