package com.pkd.assignment.espn.model;

public enum RecordStatus {
	ACTIVE(1),
	PASSIVE(0);
	
	int i;
	private RecordStatus(int i) {
		this.i = i;
	}
	
	public int getIntStatus(){
		return i;
	}
	
}
