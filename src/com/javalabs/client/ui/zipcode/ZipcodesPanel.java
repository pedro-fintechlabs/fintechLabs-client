package com.javalabs.client.ui.zipcode;

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
import com.javalabs.shared.dto.Zipcode;

public class ZipcodesPanel extends TitledPanel {

	private Zipcode selectedZipcode;

	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<Zipcode> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public ZipcodesPanel() {
		super("Zip codes");

		this.setSpacing(20);		
		this.init();

		callGetZipcodesService();
	}
	
	private void init() {

		  table = new CellTable<Zipcode>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		  TextColumn<Zipcode> idColumn = new TextColumn<Zipcode>() {
		     @Override
		     public String getValue(Zipcode object) {	    	 
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Zipcode> zipcodeColumn = new TextColumn<Zipcode>() {
		     @Override
		     public String getValue(Zipcode object) {
		        return object.getZip();
		     }
		  };
		  table.addColumn(zipcodeColumn, "Zip code");
		  
		  TextColumn<Zipcode> countyColumn = new TextColumn<Zipcode>() {
		     @Override
		     public String getValue(Zipcode object) {
		        return CacheManager.getCountyNameByCountyId(object.getCountyId());
		     }
		  };
		  table.addColumn(countyColumn, "County");
		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle Zipcode selection.
		  final SingleSelectionModel<Zipcode> selectionModel = new SingleSelectionModel<Zipcode>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Zipcode selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedZipcode = selected;
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

	public void setModel(List<Zipcode> model) {
		CacheManager.setZipcodeCache(model);
		
		//table.setPageSize(50);
		table.setPageSize(CacheManager.getZipcodeCache().size());
		table.setRowData(0, CacheManager.getZipcodeCache());
		table.setRowCount(CacheManager.getZipcodeCache().size(), false);
	}
	
	public void refresh() {
		CacheManager.getZipcodeCache().clear();
		
		callGetZipcodesService();
	}
	
	private void callGetZipcodesService() {
		if (CacheManager.hasZipcodeCache()) {
			
			setModel(CacheManager.getZipcodeCache());
			
			labelFectchInfo.setText(CacheManager.getZipcodeCache().size() + " Zipcodes");				
			return;
		}
		
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		startDate = new Date();
		
		ServiceFactory.ZIPCODE_SERVICE.flux(new MethodCallback<List<Zipcode>>() {

			@Override
			public void onSuccess(Method method, List<Zipcode> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetZipcodesService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Zipcodes in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetZipcodesService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetZipcodesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
