package com.javalabs.client.ui.audit;

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
import com.javalabs.shared.dto.Audit;

public class AuditsPanel extends TitledPanel {

	private List<Audit> AUDIT_DATA;
	private Audit selectedAudit;
	
	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<Audit> table;
	private Label labelFectchInfo = new Label("Fetching...");
	private Date 
		startDate,
		endDate;
	
	public AuditsPanel() {
		super("Audit Log");

		this.setSpacing(20);		
		this.init();

		callGetAuditsService();
	}
	
	private void init() {

		  table = new CellTable<Audit>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<Audit> idColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Audit> userTypeColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getUserType();
		     }
		  };
		  table.addColumn(userTypeColumn, "User Type");
/*
		  TextColumn<Audit> userIdColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		    	 return object.getUserId().toString();
		     }
		  };
		  table.addColumn(userIdColumn, "User ID");
*/		  
		  TextColumn<Audit> userEmailColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		    	return object.getUserEmail();
		     }
		  };
		  table.addColumn(userEmailColumn, "User Email");
		  
		
		  TextColumn<Audit> eventColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		    	return object.getEvent();
		     }
		  };
		  table.addColumn(eventColumn, "Event");
		  
		  TextColumn<Audit> auditableColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		    	 return object.getAuditable().toString();
		     }
		  };
		  table.addColumn(auditableColumn, "Auditable");
		  
		  TextColumn<Audit> oldValuesColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getOldValues();
		     }
		  };
		  table.addColumn(oldValuesColumn, "Old Values");
		  
		  TextColumn<Audit> newValuesColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getNewValues();
		     }
		  };
		  table.addColumn(newValuesColumn, "New Values");
/*
		  TextColumn<Audit> urlColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getUrl();
		     }
		  };
		  table.addColumn(urlColumn, "URL");
*/
/*
		  TextColumn<Audit> ipAddressColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getIpAddress();
		     }
		  };
		  table.addColumn(ipAddressColumn, "IP Address");
*/		  
		  TextColumn<Audit> userAgentColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getUserAgent();
		     }
		  };
		  table.addColumn(userAgentColumn, "User Agent");
		  
		  TextColumn<Audit> tagsColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		        return object.getTags();
		     }
		  };
		  table.addColumn(tagsColumn, "Tags");

		  TextColumn<Audit> timeStampColumn = new TextColumn<Audit>() {
		     @Override
		     public String getValue(Audit object) {
		    	 return object.getTimeStamp();
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

		  // Add a selection model to handle Audit selection.
		  final SingleSelectionModel<Audit> selectionModel = new SingleSelectionModel<Audit>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Audit selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedAudit = selected;
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

	private void initModelPanel(Audit address) {
	}
		
	public void setModel(List<Audit> model) {
		AUDIT_DATA = model;
		
		GWT.log("ADDRESS_DATA.size: " + AUDIT_DATA.size());
		
		//table.setPageSize(50);
		table.setPageSize(AUDIT_DATA.size());
		table.setRowData(0, AUDIT_DATA);
		table.setRowCount(AUDIT_DATA.size(), false);
	}
	
	public void refresh() {
		callGetAuditsService();
	}
	
	private void callGetAuditsService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);

		startDate = new Date();
		
		ServiceFactory.AUDIT_SERVICE.flux(new MethodCallback<List<Audit>>() {

			@Override
			public void onSuccess(Method method, List<Audit> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetAuditsService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Audits in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetAuditsService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetAuditsService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
