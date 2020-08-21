package com.javalabs.client.ui.state;

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
import com.javalabs.shared.dto.State;
import com.javalabs.shared.dto.Model;

public class StatesPanel extends TitledPanel {

	private State selectedState;

	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<State> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public StatesPanel() {
		super("States");

		this.setSpacing(20);		
		this.init();

		callGetStatesService();
	}
	
	private void init() {

		  table = new CellTable<State>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<State> idColumn = new TextColumn<State>() {
		     @Override
		     public String getValue(State object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");
/*
		  TextColumn<State> countryIdColumn = new TextColumn<State>() {
		     @Override
		     public String getValue(State object) {
		        return CacheManager.getCountryNameByCountryId(object.getCountryId());
		     }
		  };
		  table.addColumn(countryIdColumn, "Country ID");
*/
		  TextColumn<State> nameColumn = new TextColumn<State>() {
		     @Override
		     public String getValue(State object) {
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

		  // Add a selection model to handle State selection.
		  final SingleSelectionModel<State> selectionModel = new SingleSelectionModel<State>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	State selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedState = selected;
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

	public void setModel(List<State> model) {
		CacheManager.setStateCache(model);
		
		//table.setPageSize(50);
		table.setPageSize(CacheManager.getStateCache().size());
		table.setRowData(0, CacheManager.getStateCache());
		table.setRowCount(CacheManager.getStateCache().size(), false);
	}
	
	public void refresh() {
		CacheManager.getStateCache().clear();
		
		callGetStatesService();
	}
	
	private void callGetStatesService() {
		if (CacheManager.hasStateCache()) {
			
			setModel(CacheManager.getStateCache());
			
			labelFectchInfo.setText(CacheManager.getStateCache().size() + " States");				
			return;
		}
		
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		startDate = new Date();
		
		ServiceFactory.STATE_SERVICE.flux(new MethodCallback<List<State>>() {

			@Override
			public void onSuccess(Method method, List<State> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetStatesService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " States in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetStatesService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetStatesService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
