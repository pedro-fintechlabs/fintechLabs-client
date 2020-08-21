package com.javalabs.client.ui.city;

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
import com.javalabs.shared.dto.City;
import com.javalabs.shared.dto.Model;

public class CitiesPanel extends TitledPanel {

	private City selectedCity;

	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<City> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public CitiesPanel() {
		super("Cities");

		this.setSpacing(20);		
		this.init();

		callGetCitysService();
	}
	
	private void init() {

		  table = new CellTable<City>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<City> idColumn = new TextColumn<City>() {
		     @Override
		     public String getValue(City object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<City> nameColumn = new TextColumn<City>() {
		     @Override
		     public String getValue(City object) {
		        return object.getName();
		     }
		  };
		  table.addColumn(nameColumn, "Name");
		  
		  TextColumn<City> stateColumn = new TextColumn<City>() {
		     @Override
		     public String getValue(City object) {
		        return CacheManager.getStateNameByStateId(object.getStateId());
		     }
		  };
		  table.addColumn(stateColumn, "State");
		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle City selection.
		  final SingleSelectionModel<City> selectionModel = new SingleSelectionModel<City>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	City selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedCity = selected;
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

	public void setModel(List<City> model) {
		CacheManager.setCityCache(model);
		
		//table.setPageSize(50);
		table.setPageSize(CacheManager.getCityCache().size());
		table.setRowData(0, CacheManager.getCityCache());
		table.setRowCount(CacheManager.getCityCache().size(), false);
	}
	
	public void refresh() {
		CacheManager.getCityCache().clear();
		
		callGetCitysService();
	}
	
	private void callGetCitysService() {
		if (CacheManager.hasCityCache()) {
			
			setModel(CacheManager.getCityCache());
			
			labelFectchInfo.setText(CacheManager.getCityCache().size() + " Cities");				
			return;
		}
		
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		startDate = new Date();
		
		ServiceFactory.CITY_SERVICE.flux(new MethodCallback<List<City>>() {

			@Override
			public void onSuccess(Method method, List<City> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetCitiesService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Cities in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetCitiesService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetCitiesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
