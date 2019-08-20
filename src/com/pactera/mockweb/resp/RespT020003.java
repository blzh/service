package com.pactera.mockweb.resp;

import java.util.List;

public class RespT020003 {
	private int resultCount;
	private String currentDayIncrease;
	private List<SelectedStockList> selectedStockList;
	
	
	public int getResultCount() {
		return resultCount;
	}


	public void setResultCount(int resultCount) {
		this.resultCount = resultCount;
	}


	public String getCurrentDayIncrease() {
		return currentDayIncrease;
	}


	public void setCurrentDayIncrease(String currentDayIncrease) {
		this.currentDayIncrease = currentDayIncrease;
	}


	public List<SelectedStockList> getSelectedStockList() {
		return selectedStockList;
	}


	public void setSelectedStockList(List<SelectedStockList> selectedStockList) {
		this.selectedStockList = selectedStockList;
	}


	public static class SelectedStockList{
		private int codeType;
		private String code;
		private String name;
		
		public SelectedStockList(int codeType,String code, String name) {
			super();
			this.codeType = codeType;
			this.code = code;
			this.name = name;
		}
		
		
		
		public int getCodeType() {
			return codeType;
		}



		public void setCodeType(int codeType) {
			this.codeType = codeType;
		}



		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
