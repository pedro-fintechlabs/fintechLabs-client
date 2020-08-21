package com.javalabs.client.ui;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.javalabs.client.JavaLabs;
import com.javalabs.client.ui.address.AddressesPanel;
import com.javalabs.client.ui.audit.AuditsPanel;
import com.javalabs.client.ui.city.CitiesPanel;
import com.javalabs.client.ui.country.CountriesPanel;
import com.javalabs.client.ui.county.CountiesPanel;
import com.javalabs.client.ui.payment.PaymentsAPIPanel;
import com.javalabs.client.ui.payment.PaymentsPanel;
import com.javalabs.client.ui.refund.RefundsPanel;
import com.javalabs.client.ui.reversal.ReversalsPanel;
import com.javalabs.client.ui.state.StatesPanel;
import com.javalabs.client.ui.user.UsersPanel;
import com.javalabs.client.ui.zipcode.ZipcodesPanel;

public class MenuPanel extends StackLayoutPanel {
	
	private static DashboardPanel panelDashboard;
	private static PaymentsAPIPanel panelPaymentsAPI;
	private static PaymentsPanel panelPayments;
	private static RefundsPanel panelRefunds;
	private static ReversalsPanel panelReversals;
	private static UsersPanel panelUsers;
	private static CountriesPanel panelCountries;
	private static StatesPanel panelStates;
	private static CitiesPanel panelCities;
	private static CountiesPanel panelCounties;
	private static ZipcodesPanel panelZipcodes;
	private static AddressesPanel panelAddresses;

	private VerticalPanel panelDashboardMenu = new VerticalPanel();
	private VerticalPanel panelPaymentsMenu = new VerticalPanel();
//	private VerticalPanel panelBillingMenu = new VerticalPanel();
	private VerticalPanel panelConfigurationMenu = new VerticalPanel();
	private VerticalPanel panelAuditMenu = new VerticalPanel();
	
	@SuppressWarnings("deprecation")
	public MenuPanel() {
		super(Unit.EM);
		
		// Dashboard
		
		Hyperlink linkDashboardInsights = new Hyperlink("Insights", "");
		linkDashboardInsights.addClickHandler(event -> {
			panelDashboard = new DashboardPanel(); 
			JavaLabs.GET().showView(panelDashboard);
		});
		linkDashboardInsights.setWidth("191px");
		panelDashboardMenu.add(linkDashboardInsights);

		// Payments / Refunds / Reversals

		Hyperlink linkPaymentsAPI = new Hyperlink("Payments API", "");
		linkPaymentsAPI.addClickHandler(event -> {
			panelPaymentsAPI = new PaymentsAPIPanel(); 
			JavaLabs.GET().showView(panelPaymentsAPI);
		});
		linkPaymentsAPI.setWidth("191px");
		panelPaymentsMenu.add(linkPaymentsAPI);
		
		Hyperlink linkPayments = new Hyperlink("Payments", "");
		linkPayments.addClickHandler(event -> {
			panelPayments = new PaymentsPanel(); 
			JavaLabs.GET().showView(panelPayments);
		});
		linkPayments.setWidth("191px");
		panelPaymentsMenu.add(linkPayments);
		
		Hyperlink linkRefunds = new Hyperlink("Refunds", "");
		linkRefunds.addClickHandler(event -> {
			panelRefunds = new RefundsPanel(); 
			JavaLabs.GET().showView(panelRefunds);
		});
		linkRefunds.setWidth("191px");
		panelPaymentsMenu.add(linkRefunds);

		Hyperlink linkReversals = new Hyperlink("Reversals", "");
		linkReversals.addClickHandler(event -> {
			panelReversals = new ReversalsPanel(); 
			JavaLabs.GET().showView(panelReversals);
		});
		linkReversals.setWidth("191px");
		panelPaymentsMenu.add(linkReversals);

		// Configuration

		Hyperlink linkUsers = new Hyperlink("Users", "");
		linkUsers.addClickHandler(event -> {
			panelUsers = new UsersPanel(); 
			JavaLabs.GET().showView(panelUsers);
		});
		linkUsers.setWidth("191px");
		panelConfigurationMenu.add(linkUsers);

		Hyperlink linkCountries = new Hyperlink("Countries", "");
		linkCountries.addClickHandler(event -> {
			if (panelCountries == null) {
				panelCountries = new CountriesPanel();
			}
			JavaLabs.GET().showView(panelCountries);
		});
		linkCountries.setWidth("191px");
		panelConfigurationMenu.add(linkCountries);

		Hyperlink linkStates = new Hyperlink("States", "");
		linkStates.addClickHandler(event -> {
			if (panelStates == null) {
				panelStates = new StatesPanel();
			}
			JavaLabs.GET().showView(panelStates);
		});
		linkStates.setWidth("191px");
		panelConfigurationMenu.add(linkStates);

		Hyperlink linkCities = new Hyperlink("Cities", "");
		linkCities.addClickHandler(event -> {
			if (panelCities == null) {
				panelCities = new CitiesPanel();
			}
			JavaLabs.GET().showView(panelCities);
		});
		linkCities.setWidth("191px");
		panelConfigurationMenu.add(linkCities);

		Hyperlink linkCounties = new Hyperlink("Counties", "");
		linkCounties.addClickHandler(event -> {
			if (panelCounties == null) {
				panelCounties = new CountiesPanel();
			}
			JavaLabs.GET().showView(panelCounties);
		});
		linkCounties.setWidth("191px");
		panelConfigurationMenu.add(linkCounties);
		
/*
		Hyperlink linkZipcodes = new Hyperlink("Zip codes", "");
		linkZipcodes.addClickHandler(event -> {
			if (panelZipcodes == null) {
				panelZipcodes = new ZipcodesPanel();
			}
			JavaLabs.GET().showView(panelZipcodes);
		});
		linkZipcodes.setWidth("191px");
		panelConfigurationMenu.add(linkZipcodes);
*/		
		Hyperlink linkAddresses = new Hyperlink("Addresses", "");
		linkAddresses.addClickHandler(event -> {			
			panelAddresses = new AddressesPanel();			
			JavaLabs.GET().showView(panelAddresses);
		});
		linkAddresses.setWidth("191px");
		panelConfigurationMenu.add(linkAddresses);

		// Audit

		Hyperlink linkAuditLog = new Hyperlink("Audit Log", "");
		linkAuditLog.addClickHandler(event -> {
			AuditsPanel panelAudits = new AuditsPanel(); 
			JavaLabs.GET().showView(panelAudits);
		});
		linkAuditLog.setWidth("191px");
		panelAuditMenu.add(linkAuditLog);

		// this MenuPanel
		
		this.add(panelDashboardMenu, new HTML("Dashboard"), 4);   
		this.add(new HTML("Open Banking options"), new HTML("Open Banking"), 4);  
		this.add(panelPaymentsMenu, new HTML("Payments"), 4);
		this.add(/*panelBillingMenu*/new HTML("Billing options"), new HTML("Billing"), 4);
		this.add(panelConfigurationMenu, new HTML("Configuration"), 4);
		this.add(panelAuditMenu, new HTML("Audit"), 4);

	}

}
