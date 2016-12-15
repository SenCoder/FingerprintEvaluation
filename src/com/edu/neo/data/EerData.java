package com.edu.neo.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import com.edu.neo.consts.MyConst;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import static com.edu.neo.consts.MyConst.userList;

public class EerData {

	private String password;
	
	public EerData(String password) {
		super();
		this.password = password;
	}

	/**
	 * Make data for a fixed password.
	 * @return
	 */
	public ArrayList<TableRowData> makeTableData() {
		
		ArrayList<TableRowData> tableData = new ArrayList<>();
		ArrayList<String> userList = MyConst.userList;
		for (String username : userList) {
			tableData.add(eerDataByUser(username));
		}
		return tableData;
	}
	
	/**
	 * Generate data for Radar chart.
	 * @return
	 */
	public RadarData makeRadarData() {
		// TODO Auto-generated method stub
//		ArrayList<RadarData> dataList = new ArrayList<>();
		ArrayList<TableRowData> tabList = makeTableData();
		
		ArrayList<String> xVals = new ArrayList<String>();
		for (int i = 0; i < userList.size(); i++)
		  xVals.add((i+1)+"");
		
		ArrayList<Entry> yVals1 = new ArrayList<Entry>();
		ArrayList<Entry> yVals2 = new ArrayList<Entry>();
		ArrayList<Entry> yVals3 = new ArrayList<Entry>();

		for (int i = 0; i < userList.size(); i++) {
			yVals1.add(new Entry((float) tabList.get(i).eers[0], i));
			yVals2.add(new Entry((float) tabList.get(i).eers[1], i));
			yVals3.add(new Entry((float) tabList.get(i).eers[2], i));
		}
		
		ArrayList<RadarDataSet> sets = new ArrayList<RadarDataSet>();
		RadarDataSet set1 = new RadarDataSet(yVals1, "5 train");
		RadarDataSet set2 = new RadarDataSet(yVals2, "10 train");
		RadarDataSet set3 = new RadarDataSet(yVals3, "20 train");
		sets.add(set1);
		sets.add(set2);
		sets.add(set3);
		
		for (int i = 0; i < sets.size(); i++) {
			sets.get(i).setDrawFilled(false);
			sets.get(i).setColor(ColorTemplate.VORDIPLOM_COLORS[i * 2]);
			sets.get(i).setLineWidth(2f);
		}
		RadarData radarData = new RadarData(xVals, sets);
//		radarData.setValueTypeface(tf);
		radarData.setValueTextSize(8f);
		radarData.setDrawValues(false);
		
		return radarData;
	}
	
	/**
	 * Make table data for a certain password and username
	 * @param username
	 * @return
	 */
	private TableRowData eerDataByUser(String username) {
		File cDir = new File(MyConst.SD_PATH + MyConst.RESULT_DIR + "/" + username);
		
		File[] files = cDir.listFiles(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String filename) {
				// TODO Auto-generated method stub
				if (filename.contains(password)) {
					return true;
				}
				return false;
			}
		});
		if (files == null || files.length == 0) {
			System.out.println("no data for eer");
			return null;
		}
		double[] eers = new double[files.length];
		TableRowData data = new TableRowData(username);
		for (int i= 0; i < files.length; i ++) {
			try {
				BufferedReader br = new BufferedReader(new FileReader(files[i]));
				eers[i] = Double.parseDouble(br.readLine());
				br.close();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		data.eers = eers;
		return data;
	}
	
}
