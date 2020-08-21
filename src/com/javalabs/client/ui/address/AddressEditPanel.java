package com.javalabs.client.ui.address;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.TextBox;
import com.javalabs.client.cache.CacheManager;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.client.ui.ModelType;
import com.javalabs.client.ui.NameValuePanel;
import com.javalabs.client.ui.RefDataModelType;
import com.javalabs.client.ui.TitledPanel;
import com.javalabs.shared.dto.Address;

public class AddressEditPanel extends TitledPanel {

	private boolean isNew;
	private Long id;
	
	private Address address;
	private AddressesPanel parent;
	
	private NameValuePanel panelCountry;
	private NameValuePanel panelState;
	private NameValuePanel panelCity;
	private NameValuePanel panelCounty;
	private NameValuePanel panelKuditel;
	private NameValuePanel panelStreetName;
	private NameValuePanel panelZipcode;
	private NameValuePanel panelAddressable;
	private NameValuePanel panelIsVerified;
	
	private Button saveButton = new Button("Save");
	private Label msgLabel = new Label("");
	
	public AddressEditPanel(AddressesPanel parent, Address address) {
		super(address == null ? "New Address" : "Edit Address");

		this.parent = parent;
		this.address = address;
		this.id = address.getId();

		this.setSpacing(10);
		
		init();
	}
	
	private void init() {
		
		if (address == null) { // create blank
//Window.alert("isNew");			
			isNew = true;
			address = new Address();
			address.setCountryId(136L); // USA
		}

		panelCountry = new NameValuePanel("Country", ModelType.REF_DATA, RefDataModelType.COUNTRY);
		if (address.getCountryId() != null) {
			((SuggestBox)panelCountry.getWidgetValue()).setText(CacheManager.getCountryNameByCountryId(address.getCountryId()));  
		}
		this.add(panelCountry);
		
		panelState = new NameValuePanel("State", ModelType.REF_DATA, RefDataModelType.STATE);
		if (address.getStateId() != null) {	
			((SuggestBox)panelState.getWidgetValue()).setText(CacheManager.getStateNameByStateId(address.getStateId()));  
		}
		this.add(panelState);
		
		panelCounty = new NameValuePanel("County", ModelType.REF_DATA, RefDataModelType.COUNTY);
		if (address.getCountyId() != null) {	
			((SuggestBox)panelCounty.getWidgetValue()).setText(CacheManager.getCountyNameByCountyId(address.getCountyId()));  
		}
		this.add(panelCounty);
		
		panelCity = new NameValuePanel("City", ModelType.REF_DATA, RefDataModelType.CITY);
		if (address.getCityId() != null) {	
			((SuggestBox)panelCity.getWidgetValue()).setText(CacheManager.getCityNameByCityId(address.getCityId()));  
		}
		this.add(panelCity);
		
/*
		panelZipcode = new NameValuePanel("ZIP code", ModelType.REF_DATA, RefDataModelType.ZIPCODE);
		if (address.getZipcodeId() != null) {	
			((SuggestBox)panelZipcode.getWidgetValue()).setText(CacheManager.getZipcodeByZipcodeId(address.getZipcodeId()));  
		}
		this.add(panelZipcode);
*/
		// no oracle on zipcodes
		panelZipcode = new NameValuePanel("ZIP code", ModelType.STRING);
		if (address.getZipcodeId() != null) {	
			((TextBox)panelZipcode.getWidgetValue()).setText(CacheManager.getZipcodeByZipcodeId(address.getZipcodeId()));  
		}
		this.add(panelZipcode);		
		

/*
		panelKuditelID = new NameValuePanel("Kuditel ID", ModelType.STRING);
		((TextBox)panelKuditelID.getWidgetValue()).setText(address.getKuditelId());
		this.add(panelKuditelID);		

		panelStreetName = new NameValuePanel("Street Name", ModelType.STRING);
		((TextBox)panelStreetName.getWidgetValue()).setText(address.getStreetName());
		this.add(panelStreetName);

		panelPostalCode = new NameValuePanel("Postal Code", ModelType.STRING);
		((TextBox)panelPostalCode.getWidgetValue()).setText(address.getPostalCode());
		this.add(panelPostalCode);

		panelAddressable = new NameValuePanel("Addressable", ModelType.BOOLEAN);
		((CheckBox)panelAddressable.getWidgetValue()).setEnabled(address.getAddressable().equals(true));
		this.add(panelAddressable);
*/
		// TODO there is a bug here
/*		
		panelIsVerified = new NameValuePanel("Verified", ModelType.BOOLEAN);
		((CheckBox)panelIsVerified.getWidgetValue()).setEnabled(address.getIsVerified().equals(true));
		this.add(panelIsVerified);
*/

		saveButton.addClickHandler(event -> {						
			callAddressSaveService();
		});
		
		this.add(saveButton);

		msgLabel.setStyleName("errorLbl");
		this.add(msgLabel);
	}
		
	public void setAddress(Address address) {
		this.id = address.getId();
		((TextBox)panelCountry.getWidgetValue()).setText(CacheManager.getCountryNameByCountryId(address.getId()));  
//		((TextBox)panelCity.getWidgetValue()).setText(address.getCity());		
		((TextBox)panelKuditel.getWidgetValue()).setText(address.getKuditelId());		
		((TextBox)panelStreetName.getWidgetValue()).setText(address.getStreetName());		
		((CheckBox)panelAddressable.getWidgetValue()).setEnabled(address.getAddressable().equals(true));
		// TODO FIX this bug, address.getIsVerified() is null 
		if (address.getIsVerified() == null) {
			address.setIsVerified("false");
		}
		((CheckBox)panelIsVerified.getWidgetValue()).setEnabled(address.getIsVerified().equals(true));
	}

	public Address getAddress() {		
		Address address = new Address();
		
		address.setId(this.id);
		address.setCountryId(CacheManager.getCountryIdByCountryName(((SuggestBox)panelCountry.getWidgetValue()).getText()));
//		address.setCity(((TextBox)panelCity.getWidgetValue()).getText());
		address.setKuditelId(((TextBox)panelKuditel.getWidgetValue()).getText());
		address.setStreetName(((TextBox)panelStreetName.getWidgetValue()).getText());
		address.setAddressable(((CheckBox)panelAddressable.getWidgetValue()).getValue().toString());
//		address.setIsVerified(((CheckBox)panelIsVerified.getWidgetValue()).getValue().toString());
		
		return address;
	}
	
	private boolean validate() {
		return true;
	}
	
	private void callAddressSaveService() {		
		if (!validate()) {
			return;
		}
		
		//Window.alert("callUserSaveService(), id: " + this.getAddress().getId());
		
		saveButton.getElement().getStyle().setCursor(Cursor.WAIT);
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		
		ServiceFactory.ADDRESS_SERVICE.save(this.getAddress(), new MethodCallback<Address>() {

			@Override
			public void onSuccess(Method method, Address response) {
				saveButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				parent.refresh();
				
				msgLabel.setStyleName("infoLbl");
				msgLabel.setText("Address Saved!");
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				saveButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
			}
		});
	}
	
}
