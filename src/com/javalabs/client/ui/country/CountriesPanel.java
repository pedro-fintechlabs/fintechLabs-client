package com.javalabs.client.ui.country;

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
import com.javalabs.shared.dto.Country;
import com.javalabs.shared.dto.Model;

public class CountriesPanel extends TitledPanel {

	private Country selectedCountry;

	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<Country> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public CountriesPanel() {
		super("Countries");

		this.setSpacing(20);		
		this.init();

		callGetCountriesService();
	}
	
	private void init() {

		  table = new CellTable<Country>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<Country> idColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Country> kuditelIdColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getKuditelId();
		     }
		  };
		  table.addColumn(kuditelIdColumn, "Kuditel ID");

		  TextColumn<Country> nameColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getName();
		     }
		  };
		  table.addColumn(nameColumn, "Name");
		  
		  TextColumn<Country> currencyColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getCurrency();
		     }
		  };
		  table.addColumn(currencyColumn, "Currency");
		  		  
		  TextColumn<Country> phoneCodeColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getPhoneCode();
		     }
		  };
		  table.addColumn(phoneCodeColumn, "Phone Code");

		  TextColumn<Country> isoCodeColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getIsoCode();
		     }
		  };
		  table.addColumn(isoCodeColumn, "ISO Code");

		  TextColumn<Country> numberCodeColumn = new TextColumn<Country>() {
		     @Override
		     public String getValue(Country object) {
		        return object.getNumberCode();
		     }
		  };
		  table.addColumn(numberCodeColumn, "Number Code");
		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle Country selection.
		  final SingleSelectionModel<Country> selectionModel = new SingleSelectionModel<Country>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Country selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedCountry = selected;
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

	public void setModel(List<Country> model) {
		CacheManager.setCountryCache(model);
		
		//table.setPageSize(50);
		table.setPageSize(CacheManager.getCountryCache().size());
		table.setRowData(0, CacheManager.getCountryCache());
		table.setRowCount(CacheManager.getCountryCache().size(), false);
	}
	
	public void refresh() {
		CacheManager.getCountryCache().clear();
		
		callGetCountriesService();
	}
	
	private void callGetCountriesService() {
		if (CacheManager.hasCountryCache()) {
			
			setModel(CacheManager.getCountryCache());
			
			labelFectchInfo.setText(CacheManager.getCountryCache().size() + " Countries");				
			return;
		}
		
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		startDate = new Date();
		
		ServiceFactory.COUNTRY_SERVICE.flux(new MethodCallback<List<Country>>() {

			@Override
			public void onSuccess(Method method, List<Country> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetCountriesService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Countries in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetCountriesService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetCountriesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
