package com.incture.pmc.dto;

import java.util.List;

import com.incture.pmc.poadapter.services.TaskModelDto;

public class ProcessModel{

	private String processName;
	private String processDisplayName;
	private List<TaskModelDto> modelList;
	
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessDisplayName() {
		return processDisplayName;
	}
	public void setProcessDisplayName(String processDisplayName) {
		this.processDisplayName = processDisplayName;
	}
	public List<TaskModelDto> getModelList() {
		return modelList;
	}
	public void setModelList(List<TaskModelDto> modelList) {
		this.modelList = modelList;
	}

	@Override
	public String toString() {
		return "ProcessModelList [processName=" + processName + ", processDisplayName=" + processDisplayName
				+ ", modelList=" + modelList + "]";
	}
	
	
	
}