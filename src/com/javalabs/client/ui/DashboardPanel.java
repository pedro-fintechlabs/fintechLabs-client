package com.javalabs.client.ui;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.javalabs.client.ui.TitledPanel;
import com.javalabs.client.ui.chart.GeoChartPanel;

public class DashboardPanel extends TitledPanel {
	
	//private static ColumnChartPanel columnChartPanel;
	private static GeoChartPanel geoChartPanel;

	public DashboardPanel() {
		super("Dashboard - Global Payments");
		
		this.setSpacing(20);		
		this.init();
	}
	
	private void init() {
		/*
		columnChartPanel = new ColumnChartPanel();
		this.add(columnChartPanel);
		*/
		geoChartPanel = new GeoChartPanel();
		geoChartPanel.setPixelSize(1000, 500);
		this.add(geoChartPanel);

	}
}
