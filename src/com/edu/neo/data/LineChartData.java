package com.edu.neo.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import com.edu.neo.consts.MyConst;
import com.edu.neo.utils.DataHandler;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

public class LineChartData {
	
	ArrayList<ArrayList<double[]>> dataForFiles = new ArrayList<ArrayList<double[]>>();
//	ArrayList<ArrayList<double[]>> dataForViews = new ArrayList<ArrayList<double[]>>();
	ArrayList<Object> dataForViews = new ArrayList<>();
	
	String username;
	
	String[] filenames = new String[]{"01478_5.txt", "01478_10.txt", "01478_20.txt", "014367_5.txt", "014367_10.txt", "014367_20.txt"};
	
	public LineChartData(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
		loadData(username);
		setDataForViews();
	}
	
	/**
	 * 
	 * @param username
	 */
	public void loadData(String username) {
		// read data of far frr from file.
		File file = new File(MyConst.SD_PATH + MyConst.RESULT_DIR + "/" + username);
		if (!file.exists()) {
			return ;
		}
		for (int i = 0; i < filenames.length; i++) {
			File f = new File(MyConst.SD_PATH + MyConst.RESULT_DIR + "/" + username + "/" + filenames[i]);
			if (!f.exists()) {
				System.out.println(username + "： " +filenames[i] + " not exist");
				dataForFiles.add(null);
				continue;
			}
			try {
				ArrayList<double[]> dataFile = new ArrayList<>();
				BufferedReader bw = new BufferedReader(new FileReader(f));
				String s = bw.readLine();	// skip first line
				while ((s = bw.readLine()) != null) {
					String[] xs = s.split(" ");
					double[] vals = new double[xs.length];
					for (int j = 0; j < vals.length; j++) {
						vals[j] = Float.parseFloat(xs[j]);
						vals[j] = DataHandler.format(vals[j]);
					}
//					System.out.println(Arrays.toString(vals));
					dataFile.add(vals);
				}
				dataForFiles.add(dataFile);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 
	 */
	private void setDataForViews() {
		
		dataForViews.add("password = 01478");
		ArrayList<double[]> dataView;
		if (dataForFiles.get(0) != null && dataForFiles.get(1) != null && dataForFiles.get(2) != null) {
			dataView = new ArrayList<>();
			dataView.add(dataForFiles.get(0).get(0));
			dataView.add(dataForFiles.get(0).get(1));
			dataView.add(dataForFiles.get(1).get(1));
			dataView.add(dataForFiles.get(2).get(1));
			dataForViews.add(dataView);
			
			dataView = new ArrayList<>();
			dataView.add(dataForFiles.get(0).get(0));
			dataView.add(dataForFiles.get(0).get(2));
			dataView.add(dataForFiles.get(1).get(2));
			dataView.add(dataForFiles.get(2).get(2));
			dataForViews.add(dataView);
			
			dataView = new ArrayList<>();
			dataView.add(dataForFiles.get(0).get(0));
			dataView.add(dataForFiles.get(0).get(1));
			dataView.add(dataForFiles.get(1).get(1));
			dataView.add(dataForFiles.get(2).get(1));
			dataView.add(dataForFiles.get(0).get(2));
			dataView.add(dataForFiles.get(1).get(2));
			dataView.add(dataForFiles.get(2).get(2));
			dataForViews.add(dataView);
		}
		else {
			System.out.println(username + ": no data for 01478");
			dataForViews.add(null);
			dataForViews.add(null);
			dataForViews.add(null);
		}
		dataForViews.add("password = 014367");
		
		if (dataForFiles.get(3) != null && dataForFiles.get(3) != null && dataForFiles.get(5) != null) {
			dataView = new ArrayList<>();
			dataView.add(dataForFiles.get(3).get(0));
			dataView.add(dataForFiles.get(3).get(1));
			dataView.add(dataForFiles.get(4).get(1));
			dataView.add(dataForFiles.get(5).get(1));
			dataForViews.add(dataView);
			
			dataView = new ArrayList<>();
			dataView.add(dataForFiles.get(3).get(0));
			dataView.add(dataForFiles.get(3).get(2));
			dataView.add(dataForFiles.get(4).get(2));
			dataView.add(dataForFiles.get(5).get(2));
			dataForViews.add(dataView);
			
			dataView = new ArrayList<>();
			dataView.add(dataForFiles.get(3).get(0));
			dataView.add(dataForFiles.get(3).get(1));
			dataView.add(dataForFiles.get(4).get(1));
			dataView.add(dataForFiles.get(5).get(1));
			dataView.add(dataForFiles.get(3).get(2));
			dataView.add(dataForFiles.get(4).get(2));
			dataView.add(dataForFiles.get(5).get(2));
			dataForViews.add(dataView);
		}
		else {
			System.out.println(username + ": no data for 014367");
			dataForViews.add(null);
			dataForViews.add(null);
			dataForViews.add(null);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Object> dataForAdapter() {
		ArrayList<Object> ret = new ArrayList<>();
		for (int i = 0; i < dataForViews.size(); i++) {
			if (i%4 != 0) {
				ret.add(toLineData((ArrayList<double[]>) dataForViews.get(i)));
			}
			else {
				ret.add((String) dataForViews.get(i));
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param dataView
	 * @return
	 */
	private LineData toLineData(ArrayList<double[]> dataView) {
		// TODO Auto-generated method stub
		if (dataView == null) {
			return null;
		}
		ArrayList<String> xValues = new ArrayList<String>();  
        for (int i = 0; i < dataView.get(0).length; i++) {
            //x轴显示的数据，这里默认使用数字下标显示  
            xValues.add("" + dataView.get(0)[i]);
        }
        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        
        for(int i = 1; i < dataView.size(); i++) {
        	ArrayList<Entry> yValues = new ArrayList<Entry>();
        	for (int j = 0; j < dataView.get(i).length; j++) {
				yValues.add(new Entry((float) dataView.get(i)[j], j));
			}
        	lineDataSets.add(new LineDataSet(yValues, indexToLegend(i)));	//add the datasets
        }
        
        for (int i = 0; i < lineDataSets.size(); i++) {
        	// lineDataSets.get(i).setFillAlpha(110);
            // lineDataSets.get(i).setFillColor(Color.RED);
        	int mcolor = ColorTemplate.JOYFUL_COLORS[i%3];
			lineDataSets.get(i).setLineWidth(1.75f);
			lineDataSets.get(i).setCircleSize(3f);		//显示的圆形大小 
			lineDataSets.get(i).setColor(mcolor);		//显示颜色
			lineDataSets.get(i).setCircleColor(mcolor);	//圆形的颜色  
			lineDataSets.get(i).setHighLightColor(mcolor);	//高亮的线的颜色 
			lineDataSets.get(i).setDrawCircles(false);
			lineDataSets.get(i).setDrawCubic(false);	//允许平滑
//	        lineDataSets.get(i).setCubicIntensity(0.05f);
			lineDataSets.get(i).setDrawFilled(false);
//			lineDataSets.get(i).setValueTextSize(20);
		}
        return new LineData(xValues, lineDataSets);
	}
	
	/**
	 * 
	 * @param index
	 * @return
	 */
	private String indexToLegend(int index) {
		if (index%3 == 1) {
			return "5 train";
		}
		else if (index%3 == 2) {
			return "10 train";
		}
		else if (index%3 == 0) {
			return "20 train";
		}
		else {
			return null;
		}
	}

}
