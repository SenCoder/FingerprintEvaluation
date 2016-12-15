package com.edu.neo.data;

public class TableRowData {

	String username;
	double[] eers;
	
	public TableRowData(String username) {
		super();
		this.username = username;
	}
	
	/**
	 * 
	 * @param username
	 * @param eers
	 */
	public TableRowData(String username, double[] eers) {
		super();
		this.username = username;
		this.eers = eers;
	}



	public String getUsername() {
		return username;
	}

	public double[] getEers() {
		return eers;
	}
	
}
