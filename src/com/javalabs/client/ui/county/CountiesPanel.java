package com.javalabs.client.ui.county;

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
import com.javalabs.shared.dto.County;
import com.javalabs.shared.dto.Model;

public class CountiesPanel extends TitledPanel {

	private County selectedCounty;

	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<County> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public CountiesPanel() {
		super("Counties");

		this.setSpacing(20);		
		this.init();

		callGetCountiesService();
	}
	
	private void init() {

		  table = new CellTable<County>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<County> idColumn = new TextColumn<County>() {
		     @Override
		     public String getValue(County object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<County> nameColumn = new TextColumn<County>() {
		     @Override
		     public String getValue(County object) {
		        return object.getName();
		     }
		  };
		  table.addColumn(nameColumn, "Name");
		  		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle County selection.
		  final SingleSelectionModel<County> selectionModel = new SingleSelectionModel<County>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	County selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedCounty = selected;
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

	public void setModel(List<County> model) {
		CacheManager.setCountyCache(model);
		
		//table.setPageSize(50);
		table.setPageSize(CacheManager.getCountyCache().size());
		table.setRowData(0, CacheManager.getCountyCache());
		table.setRowCount(CacheManager.getCountyCache().size(), false);
	}
	
	public void refresh() {
		CacheManager.getCountyCache().clear();
		
		callGetCountiesService();
	}
	
	private void callGetCountiesService() {
		if (CacheManager.hasCountyCache()) {
			
			setModel(CacheManager.getCountyCache());
			
			labelFectchInfo.setText(CacheManager.getCountyCache().size() + " Counties");				
			return;
		}
		
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		startDate = new Date();
		
		ServiceFactory.COUNTY_SERVICE.flux(new MethodCallback<List<County>>() {

			@Override
			public void onSuccess(Method method, List<County> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetCountyService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Counties in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetCountyService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetCountyService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
