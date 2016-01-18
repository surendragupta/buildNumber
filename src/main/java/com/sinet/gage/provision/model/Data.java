package com.sinet.gage.provision.model;

/**
 * @author Team Gage
 *
 */
public class Data<T> {

	private T dataModel;
	
	public Data() {}
	
	public Data(T dataModel) {
    	this.dataModel = dataModel;
    }

	/**
	 * @return the dataModel
	 */
	public T getDataModel() {
		return dataModel;
	}

	/**
	 * @param dataModel the dataModel to set
	 */
	public void setDataModel(T dataModel) {
		this.dataModel = dataModel;
	}
	
	
}
