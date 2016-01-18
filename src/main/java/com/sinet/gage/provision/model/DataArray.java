package com.sinet.gage.provision.model;

/**
 * @author Team Gage
 *
 */
public class DataArray<T> {

	private T[] dataModels;
	
	public DataArray() {
		
	}
	
	public DataArray(T[] dataModels) {
    	this.dataModels = dataModels;
    }

	/**
	 * @return the dataModels
	 */
	public T[] getDataModels() {
		return dataModels;
	}

	/**
	 * @param dataModels the dataModels to set
	 */
	public void setDataModels(T[] dataModels) {
		this.dataModels = dataModels;
	}
	
	
}
