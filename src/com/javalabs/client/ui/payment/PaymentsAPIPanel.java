package com.javalabs.client.ui.payment;

import com.google.gwt.user.client.ui.HTMLPanel;
import com.javalabs.client.resources.PaymentsAPIResources;
import com.javalabs.client.ui.TitledPanel;

public class PaymentsAPIPanel extends TitledPanel {

	private static HTMLPanel htmlApiDocs = new HTMLPanel(PaymentsAPIResources.INSTANCE.synchronous().getText());
	
	public PaymentsAPIPanel() {
		super("Payments API");
		
		this.setSpacing(20);		
		this.init();
	}
	
	private void init() {
		this.add(htmlApiDocs);
	}
}
