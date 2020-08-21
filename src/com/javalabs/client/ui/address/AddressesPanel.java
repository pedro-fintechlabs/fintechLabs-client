package com.javalabs.client.ui.address;

import java.util.Date;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.javalabs.client.cache.CacheManager;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.client.ui.TitledPanel;
import com.javalabs.shared.dto.Address;

public class AddressesPanel extends TitledPanel {

	private List<Address> ADDRESS_DATA;
	private Address selectedAddress;
	
	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<Address> table;
	private AddressEditPanel panelAddressEdit;	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public AddressesPanel() {
		super("Addresses");

		this.setSpacing(20);		
		this.init();

		callGetAddressesService();
	}
	
	private void init() {

		  table = new CellTable<Address>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<Address> idColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Address> kuditelIdColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getKuditelId() != null ? object.getKuditelId() : "";
		     }
		  };
		  table.addColumn(kuditelIdColumn, "Kuditel ID");

		  TextColumn<Address> countryIdColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		    	if (object.getCountryId() == null) {
		    		return "";
		    	}
		    	return CacheManager.getCountryNameByCountryId(object.getCountryId());
		     }
		  };
		  table.addColumn(countryIdColumn, "Country");
		
		  TextColumn<Address> stateIdColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		    	if (object.getStateId() == null) {
		    		return "";
		    	}
		    	return CacheManager.getStateNameByStateId(object.getStateId());
		     }
		  };
		  table.addColumn(stateIdColumn, "State");
		  
		  TextColumn<Address> cityIdColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		    	 if (object.getCityId() == null) {
			    		return "";
			    	}
			    	return CacheManager.getCityNameByCityId(object.getCityId());
		     }
		  };
		  table.addColumn(cityIdColumn, "City");
		  
		  TextColumn<Address> zipcodeIdColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getZipcodeId() != null ? object.getZipcodeId().toString() : "";
		     }
		  };
		  table.addColumn(zipcodeIdColumn, "ZIP code");
		  
		  TextColumn<Address> streetNameColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getStreetName();
		     }
		  };
		  table.addColumn(streetNameColumn, "Street Name");

		  TextColumn<Address> streetNumberColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getStreetNumber();
		     }
		  };
		  table.addColumn(streetNumberColumn, "Street Number");

		  TextColumn<Address> addressableColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getAddressable();
		     }
		  };
		  table.addColumn(addressableColumn, "Addressable");
		  
		  TextColumn<Address> isVerifiedColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		        return object.getIsVerified();
		     }
		  };
		  table.addColumn(isVerifiedColumn, "Verified");

		  TextColumn<Address> timeStampColumn = new TextColumn<Address>() {
		     @Override
		     public String getValue(Address object) {
		    	 if (object.getTimeStamp() != null) {
					Date timeStamp = new Date(Long.parseLong(object.getTimeStamp()));
			        return timeStamp != null ? timeStamp.toString() : "";
		    	 } else {
		    		 return "";
		    	 }
		     }
		  };
		  table.addColumn(timeStampColumn, "Time Stamp");
		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle Address selection.
		  final SingleSelectionModel<Address> selectionModel = new SingleSelectionModel<Address>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Address selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedAddress = selected;
			        	initModelPanel(selected);
			        }
			     }
			  }
		  );
		  
		  //panel.setBorderWidth(1);
		  panel.setSpacing(10);
		  panel.add(table);

		  this.add(labelFectchInfo);
		  this.add(panel);
	}		

	private void initModelPanel(Address address) {
		if (panelAddressEdit != null) {
			panelAddressEdit.removeFromParent();
		}
		panelAddressEdit = new AddressEditPanel(this, address);	 
		panel.add(panelAddressEdit);
	}
		
	public void setModel(List<Address> model) {
		ADDRESS_DATA = model;
		
		GWT.log("ADDRESS_DATA.size: " + ADDRESS_DATA.size());
		
		//table.setPageSize(50);
		table.setPageSize(ADDRESS_DATA.size());
		table.setRowData(0, ADDRESS_DATA);
		table.setRowCount(ADDRESS_DATA.size(), false);
	}
	
	public void refresh() {
		callGetAddressesService();
	}
	
	private void callGetAddressesService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);

		startDate = new Date();
		
		ServiceFactory.ADDRESS_SERVICE.flux(new MethodCallback<List<Address>>() {

			@Override
			public void onSuccess(Method method, List<Address> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetAddressesService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Addresses in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetAddressesService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetAddressesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
