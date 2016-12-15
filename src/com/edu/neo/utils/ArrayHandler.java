package com.edu.neo.utils;

import java.util.ArrayList;

/**
 * Created by Yunpeng on 15/10/18.
 */
public class ArrayHandler {

    public static ArrayList<Double> trasferToArrayList(double [] x) {
        ArrayList<Double> result = new ArrayList<>();
        for (int i=0;i<x.length;i++) {
            result.add(x[i]);
        }
        return result;
    }


    public static double[] trasferToArray (ArrayList<Double> list) {
        double[] array = new double[list.size()];
        for (int i=0;i<array.length;i++) {
            array[i] = list.get(i);
        }
        return array;
    }


    public static double[][] trasferTo2DArray(ArrayList<double[]> list) {
        double[][] result = new double[3][list.size()];
        for (int i=0;i<list.size();i++) {
            double[] tmp = list.get(i);
            for (int j=0;j<3;j++) {
                result[j][i] = tmp[j];
            }
        }
        return result;
    }
    
    public static double[] range(double start, double end, int num) {
		double[] ret = new double[num];
		double by = (end - start)/num;
		for (int i = 0; i < ret.length; i++) {
			ret[i] = start + by * i;
		}
		return ret;
	}
}
