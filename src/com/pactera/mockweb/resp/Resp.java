package com.pactera.mockweb.resp;

public class Resp {
	private String message;
	private String status;// ":"success",
	private int code = 200; //200成功，其他失败
	private Object info;
	
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	private Resp() {}
	
	public static Resp getSuccessResp(Object info) {
		 Resp resp = new Resp();
		 resp.setMessage("交易成功");
		 resp.setStatus("success");
		 resp.setCode(200);
		 resp.setInfo(info);
		 return resp;
	}
	
	public static Resp getFailureResp(String message) {
		 Resp resp = new Resp();
		 message = (message == null || "".equals(message)) ? "交易失败" : message;
		 resp.setMessage(message);
		 resp.setStatus("failure");
		 resp.setCode(100);
		 return resp;
	}
	
}
