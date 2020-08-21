package com.javalabs.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.javalabs.client.cache.CacheManager;
import com.javalabs.shared.dto.City;
import com.javalabs.shared.dto.Country;
import com.javalabs.shared.dto.County;
import com.javalabs.shared.dto.State;
import com.javalabs.shared.dto.Zipcode;

public class NameValuePanel extends VerticalPanel {

	private ModelType modelType;
	private RefDataModelType refDataModelType;
	private Widget widgetValue; 

	public NameValuePanel(String name, ModelType type) {
		this(name, type, null);
	}
	
	public NameValuePanel(String name, ModelType modelType, RefDataModelType refDataModelType) {
		this.modelType = modelType;
		this.refDataModelType = refDataModelType;
		
		Label labelName = new Label(name);
		this.add(labelName);
		
		switch(modelType) {
			case STRING:
				widgetValue = new TextBox();
				break;
			case INTEGER:
				break;
			case BOOLEAN:
				widgetValue = new CheckBox();
				break;
			case PASSWORD:
				widgetValue = new PasswordTextBox();
				break;
			case DATE:
				break;
			default:
				widgetValue = this.constructorForRefData(name, refDataModelType);
		}

		this.add(widgetValue);
	}

	@SuppressWarnings("incomplete-switch")
	private Widget constructorForRefData(String name, RefDataModelType type) {
		this.modelType = ModelType.REF_DATA;
		
		// Define the oracle that finds suggestions
	    MultiWordSuggestOracle oracle = new MultiWordSuggestOracle(); // NOT Larry

	    switch(type) {
		    case COUNTRY:
			    for (Country country: CacheManager.getCountryCache()) {
			    	oracle.add(country.getName());
			    }
		    	break;
	
		    case STATE:
			    for (State state: CacheManager.getStateCache()) {
			    	oracle.add(state.getName());
			    }
		    	break;
		    	
		    case COUNTY:
			    for (County county: CacheManager.getCountyCache()) {
			    	oracle.add(county.getName());
			    }
		    	break;
		    	
		    case CITY:
			    for (City city: CacheManager.getCityCache()) {
			    	oracle.add(city.getName());
			    }
		    	break;
		    	
		    case ZIPCODE:
		    	//Window.alert("ZIPCODE Cache:\n" + CacheManager.getZipcodeCache());
			    for (Zipcode zipcode: CacheManager.getZipcodeCache()) {
			    	 oracle.add(zipcode.getZip());
			    }
		    	break;
	    }

	    return new SuggestBox(oracle);
	}
	
	public Widget getWidgetValue() {
		return widgetValue;
	}

}
