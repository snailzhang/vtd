package com.esd.ps.model;

public class WorkerDownPackHistoryTrans {
	private String downPackName;

	private String downTime;

	private int taskCount;

	private int packStatu;

	public String getDownPackName() {
		return downPackName;
	}

	public void setDownPackName(String downPackName) {
		this.downPackName = downPackName;
	}

	public String getDownTime() {
		return downTime;
	}

	public void setDownTime(String downTime) {
		this.downTime = downTime;
	}

	public int getTaskCount() {
		return taskCount;
	}

	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	public int getPackStatu() {
		return packStatu;
	}

	public void setPackStatu(int packStatu) {
		this.packStatu = packStatu;
	}

}
