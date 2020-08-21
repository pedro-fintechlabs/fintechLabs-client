package com.javalabs.client.ui.reversal;

import java.util.Date;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.SimplePager.TextLocation;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.client.ui.TitledPanel;
import com.javalabs.shared.dto.Reversal;

public class ReversalsPanel extends TitledPanel {

	private List<Reversal> REVERSAL_DATA;
	private Reversal selectedReversal;
	private CellTable<Reversal> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date startDate;
	private Date endDate;
	
	public ReversalsPanel() {
		super("Reversals");

		this.setSpacing(20);		
		this.init();

		callGetReversalsService();
	}
	
	private void init() {

		  table = new CellTable<Reversal>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<Reversal> idColumn = new TextColumn<Reversal>() {
		     @Override
		     public String getValue(Reversal object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Reversal> kuditelIdColumn = new TextColumn<Reversal>() {
		     @Override
		     public String getValue(Reversal object) {
		        return object.getKuditelId();
		     }
		  };
		  table.addColumn(kuditelIdColumn, "Kuditel ID");

		  TextColumn<Reversal> paymentIdColumn = new TextColumn<Reversal>() {
		     @Override
		     public String getValue(Reversal object) {
		        return object.getPaymentId();
		     }
		  };
		  table.addColumn(paymentIdColumn, "Payment ID");
		  
		  TextColumn<Reversal> responseCodeColumn = new TextColumn<Reversal>() {
		     @Override
		     public String getValue(Reversal object) {
		        return object.getResponseCode();
		     }
		  };
		  table.addColumn(responseCodeColumn, "Response Code");

		  TextColumn<Reversal> responseMessageColumn = new TextColumn<Reversal>() {
		     @Override
		     public String getValue(Reversal object) {
		        return object.getResponseMessage();
		     }
		  };
		  table.addColumn(responseMessageColumn, "Response Message");
		  
		  TextColumn<Reversal> timeStampColumn = new TextColumn<Reversal>() {
		     @Override
		     public String getValue(Reversal object) {
				Date timeStamp = new Date(Long.parseLong(object.getTimeStamp()));
		        return timeStamp.toString();
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

		  // Add a selection model to handle Reversal selection.
		  final SingleSelectionModel<Reversal> selectionModel = new SingleSelectionModel<Reversal>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Reversal selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedReversal = selected;
			        }
			     }
			  }
		  );
		  
		  VerticalPanel panel = new VerticalPanel();
		  panel.setBorderWidth(1);	    
		  panel.setWidth("400");
		  panel.add(table);

		  this.add(labelFectchInfo);
		  this.add(panel);
	}		

	public void setModel(List<Reversal> model) {
		REVERSAL_DATA = model;
		
		GWT.log("REVERSAL_DATA.size: " + REVERSAL_DATA.size());
		
		//table.setPageSize(50);
		table.setPageSize(REVERSAL_DATA.size());
		table.setRowData(0, REVERSAL_DATA);
		table.setRowCount(REVERSAL_DATA.size(), false);
	}
	
	public void refresh() {
		callGetReversalsService();
	}
	
	private void callGetReversalsService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);

		startDate = new Date();
		
		ServiceFactory.REVERSAL_SERVICE.flux(new MethodCallback<List<Reversal>>() {

			@Override
			public void onSuccess(Method method, List<Reversal> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetReversalsService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Reversals in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetReversalsService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetReversalsService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
