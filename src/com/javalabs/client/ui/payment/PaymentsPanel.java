package com.javalabs.client.ui.payment;

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
import com.javalabs.shared.dto.Payment;

public class PaymentsPanel extends TitledPanel {

	private List<Payment> PAYMENT_DATA;
	private Payment selectedPayment;
	private CellTable<Payment> table;
	
	private Label labelFectchInfo = new Label("Fetching...");
	private Date startDate;
	private Date endDate;
	
	public PaymentsPanel() {
		super("Payments");

		this.setSpacing(20);		
		this.init();

		callGetPaymentsService();
	}
	
	private void init() {

		  table = new CellTable<Payment>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<Payment> idColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<Payment> kuditelIdColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getKuditelId();
		     }
		  };
		  table.addColumn(kuditelIdColumn, "Kuditel ID");

		  TextColumn<Payment> accountNumberColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getAccountNumber();
		     }
		  };
		  table.addColumn(accountNumberColumn, "Account Number");
		  
		  TextColumn<Payment> descriptionColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getDescription();
		     }
		  };
		  table.addColumn(descriptionColumn, "Description");
		  		  
		  TextColumn<Payment> amountColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getAmount();
		     }
		  };
		  table.addColumn(amountColumn, "Amount");

		  TextColumn<Payment> actualAmountColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getActualAmount();
		     }
		  };
		  table.addColumn(actualAmountColumn, "Actual Amount");

		  TextColumn<Payment> feesColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getFees();
		     }
		  };
		  table.addColumn(feesColumn, "Fees");

		  TextColumn<Payment> expiryMonthColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getExpiryMonth();
		     }
		  };
		  table.addColumn(expiryMonthColumn, "Expiry Month");

		  TextColumn<Payment> expiryYearColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getExpiryYear();
		     }
		  };
		  table.addColumn(expiryYearColumn, "Expiry Year");

		  TextColumn<Payment> holderNameColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getHolderName();
		     }
		  };
		  table.addColumn(holderNameColumn, "Holder Name");

		  TextColumn<Payment> merchantIdColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getMerchantId();
		     }
		  };
		  table.addColumn(merchantIdColumn, "Merchant ID");

		  TextColumn<Payment> stanColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getStan();
		     }
		  };
		  table.addColumn(stanColumn, "STAN");

		  TextColumn<Payment> transactionIdColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getTransactionId();
		     }
		  };
		  table.addColumn(transactionIdColumn, "Transaction ID");

		  TextColumn<Payment> responseCodeColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getResponseCode();
		     }
		  };
		  table.addColumn(responseCodeColumn, "Response Code");

		  TextColumn<Payment> responseMessageColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getResponseMessage();
		     }
		  };
		  table.addColumn(responseMessageColumn, "Response Message");

		  TextColumn<Payment> responseUrlColumn = new TextColumn<Payment>() {
		     @Override
		     public String getValue(Payment object) {
		        return object.getResponseUrl();
		     }
		  };
		  table.addColumn(responseUrlColumn, "Response URL");
		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle Payment selection.
		  final SingleSelectionModel<Payment> selectionModel = new SingleSelectionModel<Payment>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	Payment selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedPayment = selected;
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

	public void setModel(List<Payment> model) {
		PAYMENT_DATA = model;
		
		GWT.log("PAYMENT_DATA.size: " + PAYMENT_DATA.size());
		
		//table.setPageSize(50);
		table.setPageSize(PAYMENT_DATA.size());
		table.setRowData(0, PAYMENT_DATA);
		table.setRowCount(PAYMENT_DATA.size(), false);
	}
	
	public void refresh() {
		callGetPaymentsService();
	}
	
	private void callGetPaymentsService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);

		startDate = new Date();
		
		ServiceFactory.PAYMENT_SERVICE.flux(new MethodCallback<List<Payment>>() {

			@Override
			public void onSuccess(Method method, List<Payment> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetPaymentsService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Payments in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);				
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				GWT.log(method.getResponse().getText());
		        //Window.alert("callGetPaymentsService - FAILURE:\n"  + method.getResponse().getText());
		        Window.alert("callGetPaymentsService - FAILURE:\n"  + exception.getMessage());
			}
		});		
	}
	
}
