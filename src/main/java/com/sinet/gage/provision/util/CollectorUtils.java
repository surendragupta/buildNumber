package com.sinet.gage.provision.util;

import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Custom stream collector 
 * 
 * @author Team Gage
 *
 */
public class CollectorUtils {

	/**
	 * Collects the first element from the list
	 * 
	 * @return if result list is empty then return null
	 * otherwise first element of the list
	 */
	public static <T> Collector<T, ?, T> single() {
		return Collectors.collectingAndThen(
	            Collectors.toList(),
	            list -> {
	                if (list.size() != 1) {
	                    return null;
	                }
	                return list.get(0);
	            }
	    );
	}

}
