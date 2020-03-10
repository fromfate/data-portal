package com.asiainfo.dto;

/**
 * restful接口返回对象
 */
public class RestResponse {
    /**需要返回的对象*/
    private Object data;
    /**提示信息*/
    private String message;

    public RestResponse(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    public RestResponse(Object data) {
    	this(data,"");
    }

    public RestResponse(String message) {
        this(null, message);
    }
    



	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
