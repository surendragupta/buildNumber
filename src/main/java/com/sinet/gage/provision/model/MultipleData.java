package com.sinet.gage.provision.model;

/**
 * 
 * @author Team Gage
 *
 * @param <T>
 */
public class MultipleData<T> {
	
	private T data1;
	private T data2;
	private T data3;
	private T data4;
	
	public MultipleData() {}
	
	public MultipleData(T data1, T data2, T data3, T data4) {
    	this.data1 = data1;
    	this.data2 = data2;
    	this.data3 = data3;
    	this.data4 = data4;
    }

	/**
	 * @return the data1
	 */
	public T getData1() {
		return data1;
	}

	/**
	 * @param data1 the data1 to set
	 */
	public void setData1(T data1) {
		this.data1 = data1;
	}

	/**
	 * @return the data2
	 */
	public T getData2() {
		return data2;
	}

	/**
	 * @param data2 the data2 to set
	 */
	public void setData2(T data2) {
		this.data2 = data2;
	}

	/**
	 * 
	 * @return
	 */
	public T getData3() {
		return data3;
	}

	/**
	 * 
	 * @param data3
	 */
	public void setData3(T data3) {
		this.data3 = data3;
	}

	/**
	 * 
	 * @return
	 */
	public T getData4() {
		return data4;
	}

	/**
	 * 
	 * @param data4
	 */
	public void setData4(T data4) {
		this.data4 = data4;
	}	
}
