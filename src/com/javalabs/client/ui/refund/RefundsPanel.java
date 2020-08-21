package com.javalabs.client.ui.refund;

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
import com.javalabs.shared.dto.Refund;

public class RefundsPanel extends TitledPanel {

	private List<Refund> REFUND_DATA;
	private Refund selectedRefund;
	private CellTable<Refund> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date startDate;
	private Date endDate;
	
	public RefundsPanel() {
		super("Refunds");

		this.setSpacing(20);		
		this.init();

		callGetRefundsService();
	}
	
	private void init() {

		  table = new CellTable<Refund>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<Refund> idColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Refund> kuditelIdColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
		        return object.getKuditelId();
		     }
		  };
		  table.addColumn(kuditelIdColumn, "Kuditel ID");

		  TextColumn<Refund> paymentIdColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
		        return object.getPaymentId();
		     }
		  };
		  table.addColumn(paymentIdColumn, "Payment ID");
		  
		  TextColumn<Refund> amountColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
		        return object.getAmount();
		     }
		  };
		  table.addColumn(amountColumn, "Amount");
		  		  
		  TextColumn<Refund> responseCodeColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
		        return object.getResponseCode();
		     }
		  };
		  table.addColumn(responseCodeColumn, "Response Code");

		  TextColumn<Refund> responseMessageColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
		        return object.getResponseMessage();
		     }
		  };
		  table.addColumn(responseMessageColumn, "Response Message");
		  
		  TextColumn<Refund> timeStampColumn = new TextColumn<Refund>() {
		     @Override
		     public String getValue(Refund object) {
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

		  // Add a selection model to handle Refund selection.
		  final SingleSelectionModel<Refund> selectionModel = new SingleSelectionModel<Refund>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Refund selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedRefund = selected;
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

	public void setModel(List<Refund> model) {
		REFUND_DATA = model;
		
		GWT.log("REFUND_DATA.size: " + REFUND_DATA.size());
		
		//table.setPageSize(50);
		table.setPageSize(REFUND_DATA.size());
		table.setRowData(0, REFUND_DATA);
		table.setRowCount(REFUND_DATA.size(), false);
	}
	
	public void refresh() {
		callGetRefundsService();
	}
	
	private void callGetRefundsService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);

		startDate = new Date();
		
		ServiceFactory.REFUND_SERVICE.flux(new MethodCallback<List<Refund>>() {

			@Override
			public void onSuccess(Method method, List<Refund> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetRefundsService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Refunds in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetRefundsService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetRefundsService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
