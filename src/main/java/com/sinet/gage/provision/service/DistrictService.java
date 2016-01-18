package com.sinet.gage.provision.service;

import com.sinet.gage.provision.data.model.District;
import com.sinet.gage.provision.data.model.School;


/**
 * District service interface 
 * 
 * @author Team Gage
 *
 */
public interface DistrictService {
	

	
	/**
	 * Insert District in database
	 * 
	 * @param 
	 *       district pojo to persists
	 * @return 
	 *       Districts entity
	 */
	public District insertDistrict( District district ) ;
	
	/**
	 * Update District in database
	 * 
	 * @param 
	 *       district pojo to persists
	 * @return 
	 *       Districts entity
	 */
	public District updateDistrict(District district);
	
	/**
	 * Delete District from database
	 * 
	 * @param districtId 
	 *        Id of the district entity to delete
	 * @return 
	 *        Districts entity
	 */
	public District deleteDistrict( Long districtId ) ;
	
	/**
	 * Insert school in database
	 * 
	 * @param districtId 
	 *        Id of the parent district domain of school domain
	 * @param school
	 *        School pojo to persists       
	 * @return 
	 *        School entity
	 */
	public School insertSchool( Long districtId, School school ) ;
	
	/**
	 * Delete school from database
	 * 
	 * @param schooldId
	 *        Id of school entity to delete
	 */
	public void deleteSchool ( Long schooldId );
	
	/**
	 * Update school in database
	 * 
	 * @param districtId 
	 *        Id of the parent district domain of school domain
	 * @param school
	 *        School pojo to persists       
	 * @return 
	 *        School entity
	 */
	public School updateSchool(Long districtId, School school);

}
