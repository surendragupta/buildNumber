package com.sinet.gage.provision.model;

import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;

/**
 * 
 * @author Team Gage
 *
 */
public class ReplaceNamingStrategy extends PropertyNamingStrategy {

	private static final long serialVersionUID = 1L;

	// used by other classes (this will be default field name that should be changed)
	private Map<String, String> replaceMap;

    public ReplaceNamingStrategy(Map<String, String> replaceMap) {
        this.replaceMap = replaceMap;
    }

    @Override
    public String nameForGetterMethod(MapperConfig<?> config, AnnotatedMethod method, String defaultName) {
        if (replaceMap.containsKey(defaultName)) {
            return replaceMap.get(defaultName);
        }
        return super.nameForGetterMethod(config, method, defaultName);
    }
}
