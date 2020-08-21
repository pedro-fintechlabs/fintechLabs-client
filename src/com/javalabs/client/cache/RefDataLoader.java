package com.javalabs.client.cache;

import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.shared.dto.City;
import com.javalabs.shared.dto.Country;
import com.javalabs.shared.dto.County;
import com.javalabs.shared.dto.State;
import com.javalabs.shared.dto.Zipcode;

public class RefDataLoader {

	public static void callGetCountriesService() {

		ServiceFactory.COUNTRY_SERVICE.flux(new MethodCallback<List<Country>>() {

			@Override
			public void onSuccess(Method method, List<Country> response) {
				//Window.alert("callGetCountriesService - SUCCESS\n");
				
				CacheManager.setCountryCache(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetCountriesService - FAILURE:\n"  + method.getResponse().getText());
		        //Window.alert("callGetCountriesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}

	public static void callGetStatesService() {

		ServiceFactory.STATE_SERVICE.flux(new MethodCallback<List<State>>() {

			@Override
			public void onSuccess(Method method, List<State> response) {
				//Window.alert("callGetStatesService - SUCCESS\n");
				
				CacheManager.setStateCache(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetStatesService - FAILURE:\n"  + method.getResponse().getText());
		        //Window.alert("callGetStatesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}

	public static void callGetCountiesService() {

		ServiceFactory.COUNTY_SERVICE.flux(new MethodCallback<List<County>>() {

			@Override
			public void onSuccess(Method method, List<County> response) {
				//Window.alert("callGetCountiesService - SUCCESS\n");
				
				CacheManager.setCountyCache(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetCountiesService - FAILURE:\n"  + method.getResponse().getText());
		        //Window.alert("callGetCountiesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}

	public static void callGetCitiesService() {

		ServiceFactory.CITY_SERVICE.flux(new MethodCallback<List<City>>() {

			@Override
			public void onSuccess(Method method, List<City> response) {
				//Window.alert("callGetCitiesService - SUCCESS\n");
				
				CacheManager.setCityCache(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetCitiesService - FAILURE:\n"  + method.getResponse().getText());
		        //Window.alert("callGetCitiesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}

	public static void callGetZipcodesService() {

		ServiceFactory.ZIPCODE_SERVICE.flux(new MethodCallback<List<Zipcode>>() {

			@Override
			public void onSuccess(Method method, List<Zipcode> response) {
				Window.alert("callGetZipcodesService - SUCCESS\n" + response.toString());
				
				CacheManager.setZipcodeCache(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetZipcodesService - FAILURE:\n"  + method.getResponse().getText());
		        //Window.alert("callGetZipcodesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
