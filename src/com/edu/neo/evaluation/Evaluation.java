package com.edu.neo.evaluation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.edu.neo.consts.MyConst;
import com.edu.neo.data.EvaluationUnit;
import com.edu.neo.model.AuthEvent;
import com.edu.neo.model.User;
import com.edu.neo.utils.ArrayHandler;
import com.edu.neo.utils.FileUtil;

public class Evaluation {
	
	private ArrayList<ArrayList<AuthEvent>> dataset;

	private double[] sholdrange = ArrayHandler.range(0.0, 10.0, 25);
	private ArrayList<String> nameList;
	
	private EvaluationUnit eUnit;
	
	public Evaluation() {
		// TODO Auto-generated constructor stub
		File file = new File(MyConst.WORKSPACE);
		if (!file.exists()) {
			return ;
		}
		nameList = new ArrayList<String>(Arrays.asList(file.list()));
	}
	
	public void evaluate() {
		evaluate("01478");
		evaluate("014367");
	}
	
	/**
	 * 
	 * @param dataCu
	 * @param train_num
	 * @param userIndex
	 * @param user
	 */
	private void evaluate(List<AuthEvent> dataCu, int train_num, int userIndex ,User user) {
		if(dataCu.size() < train_num)
		{
			System.out.println("user: " + user.getUsername() + "does not have enough data for evaluation");
		}
		eUnit = new EvaluationUnit(user, sholdrange, train_num);
		//	cross valid train
		for (double thrd : sholdrange) {
			ArrayList<int[]> resultCu = new ArrayList<>();
			
			for (int j = 0; j + train_num < dataCu.size(); j += train_num) {
				user.loadModel(dataCu.subList(j, j + train_num));
				
				user.oneOutCrossValidation(thrd);
				// predict with a fixed threshold, before that, we need to set different thresholds. 1.41, 1.61 ...
				// positive predict test 
				for(int k = 0; k < dataCu.size(); k ++) {
					if (k < j || k >= j + train_num) {
						// add result to resultCu
						double score = user.predictByAuthEvent(dataCu.get(k));
						if (score > 0.5) {
							resultCu.add(new int[]{1, 1});
						}
						else {
							resultCu.add(new int[]{1, 0});
						}
					}
				}
				// negative test
				for(int m = 0; m < dataset.size(); m ++) {
					if(m != userIndex) {
						ArrayList<AuthEvent> testData = dataset.get(m);
						for (int k = 0; k < testData.size(); k++) {
							// add result to resultCu
							double score = user.predictByAuthEvent(testData.get(k));
							if (score > 0.5) {
								resultCu.add(new int[]{0, 1});
							}
							else {
								resultCu.add(new int[]{0, 0});
							}
						}
					}
				}
			}
			setFarRrr(resultCu, eUnit);
		}
		eUnit.saveToTxt();
	}
	
	void setFarRrr(ArrayList<int[]> resultCu, EvaluationUnit eUnit) {
		int TP = 0, TN = 0, FP = 0, FN = 0;
		for (int i = 0; i < resultCu.size(); i++) {
			if (resultCu.get(i)[0] == 1 && resultCu.get(i)[1] == 1) {
				TP ++;
			}
			else if (resultCu.get(i)[0] == 1 && resultCu.get(i)[1] == 0) {
				FN ++;
			}
			else if (resultCu.get(i)[0] == 0 && resultCu.get(i)[1] == 1) {
				FP ++;
			}
			else {
				TN ++;
			}
		}
		eUnit.fars.add(FP/(double)(FP + TN));
		eUnit.frrs.add(FN/(double)(FN + TP));
	}

	/**
	 * 
	 * @param password
	 */
	private void evaluate(String password) {
		
		loadAllData(password);
		for (int i = 0; i < nameList.size(); i++) {
			User user = new User(nameList.get(i), password);
			List<AuthEvent> dataCu = dataset.get(i);
			evaluate(dataCu, 5, i, user);
			evaluate(dataCu, 10, i, user);
			evaluate(dataCu, 20, i, user);
		}
	}
	
	/**
	 * 
	 * @param password
	 */
	private void loadAllData(String password) {
		if (dataset == null) {
			dataset = new ArrayList<>();
		}
		else {
			dataset.clear();
		}
		for (int i = 0; i < nameList.size(); i++) {
			ArrayList<AuthEvent> data = FileUtil.loadRawData(nameList.get(i), password);
			dataset.add(data);
		}
	}

}
