package com.pactera.mockweb.resp;

import java.util.List;

public class RespT020006 {
	private List<MethodList> methodList;
	
	public List<MethodList> getMethodList() {
		return methodList;
	}

	public void setMethodList(List<MethodList> methodList) {
		this.methodList = methodList;
	}

	public static class MethodList{
		private String id;
		private String name;
		private List<SelectedsubContent> selectedsubContent;
		private int stockCount;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<SelectedsubContent> getSelectedsubContent() {
			return selectedsubContent;
		}
		public void setSelectedsubContent(List<SelectedsubContent> selectedsubContent) {
			this.selectedsubContent = selectedsubContent;
		}
		public int getStockCount() {
			return stockCount;
		}
		public void setStockCount(int stockCount) {
			this.stockCount = stockCount;
		}
		
		public static class SelectedsubContent{
			private String id;
			private String name;
			public String getId() {
				return id;
			}
			public void setId(String id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public SelectedsubContent(String id, String name) {
				super();
				this.id = id;
				this.name = name;
			}
			
		}
		
		
		
	}
}
