package com.javalabs.client.cache;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.javalabs.shared.dto.City;
import com.javalabs.shared.dto.Country;
import com.javalabs.shared.dto.County;
import com.javalabs.shared.dto.State;
import com.javalabs.shared.dto.Zipcode;

/**
 * Static Cache Manager for Reference Data.
 * Fetch Reference Data from server only once.
 *
 */
public class CacheManager {

	static List<Country> countryCache;
	static List<State> stateCache;
	static List<City> cityCache;
	static List<County> countyCache;
	static List<Zipcode> zipcodeCache;
	
	public static void clear() {
		countryCache.clear();
		stateCache.clear();
		cityCache.clear();
		countyCache.clear();
		zipcodeCache.clear();
	}
	
	// Country
	
	public static boolean hasCountryCache() {
		return countryCache != null;
	}
	
	public static void setCountryCache(List<Country> model) {
		countryCache = model;
		showStats();
	}
		
	public static List<Country> getCountryCache() {
		return countryCache;
	}

	// State
	
	public static boolean hasStateCache() {
		return stateCache != null;
	}
	
	public static void setStateCache(List<State> model) {
		stateCache = model;
		showStats();
	}
		
	public static List<State> getStateCache() {
		return stateCache;
	}

	// City
	
	public static boolean hasCityCache() {
		return cityCache != null;
	}
	
	public static void setCityCache(List<City> model) {
		cityCache = model;
		showStats();
	}
		
	public static List<City> getCityCache() {
		return cityCache;
	}

	// County
	
	public static boolean hasCountyCache() {
		return countyCache != null;
	}
	
	public static void setCountyCache(List<County> model) {
		countyCache = model;
		showStats();
	}
		
	public static List<County> getCountyCache() {
		return countyCache;
	}

	// Zipcode

	public static boolean hasZipcodeCache() {
		return zipcodeCache != null;
	}
	
	public static void setZipcodeCache(List<Zipcode> model) {
		zipcodeCache = model;
		showStats();
	}
		
	public static List<Zipcode> getZipcodeCache() {
		return zipcodeCache;
	}
	
	// showStats
	
	public static void showStats() {
	}

	// gets
	
	public static Long getCountryIdByCountryName(String countryName) {
		for (Country country: countryCache) {
			if (country.getName().equals(countryName)) {
				return country.getId();
			}
		}
		
		return null;
	}

	public static String getCountryNameByCountryId(Long countryId) {
		GWT.log("countryCache.size:" + countryCache.size());
		GWT.log("getCountryNameByCountryId, countryId:" + countryId); 
		for (Country country: countryCache) {
			if (country.getId().equals(countryId)) {
				GWT.log("FOUND: " + country.getName()); 
				return country.getName();
			}
		}
		GWT.log("NOT FOUND");
		return null;
	}

	public static String getStateNameByStateId(Long stateId) {
		GWT.log("stateCache.size:" + stateCache.size());
		GWT.log("getStateNameByStateId, stateId:" + stateId); 
		for (State state: stateCache) {
			if (state.getId().equals(stateId)) {
				GWT.log("FOUND: " + state.getName()); 
				return state.getName();
			}
		}
		GWT.log("NOT FOUND");
		return null;
	}

	public static String getCountyNameByCountyId(Long countyId) {
		GWT.log("countyCache.size:" + countyCache.size());
		GWT.log("getCountyNameByCountyId, countyId:" + countyId); 
		for (County county: countyCache) {
			if (county.getId().equals(countyId)) {
				GWT.log("FOUND: " + county.getName()); 
				return county.getName();
			}
		}
		GWT.log("NOT FOUND");
		return null;
	}

	public static String getCityNameByCityId(Long cityId) {
		GWT.log("cityCache.size:" + cityCache.size());
		GWT.log("getCityNameByCityId, cityId:" + cityId); 
		for (City city: cityCache) {
			if (city.getId().equals(cityId)) {
				GWT.log("FOUND: " + city.getName()); 
				return city.getName();
			}
		}
		GWT.log("NOT FOUND");
		return null;
	}	
	
	public static String getZipcodeByZipcodeId(Long zipcodeId) {
		GWT.log("zipcodeCache.size:" + zipcodeCache.size());
		GWT.log("getZipcodeByZipcodeId, zipcodeId:" + zipcodeId); 
		for (Zipcode zipcode: zipcodeCache) {
			if (zipcode.getId().equals(zipcodeId)) {
				GWT.log("FOUND: " + zipcode.getZip()); 
				return zipcode.getZip();
			}
		}
		GWT.log("NOT FOUND");
		return null;
	}
	
}
