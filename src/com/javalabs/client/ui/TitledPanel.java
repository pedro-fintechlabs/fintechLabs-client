package com.javalabs.client.ui;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class TitledPanel extends VerticalPanel {

	private Label titleLabel;
	
	public TitledPanel(String title) {
		this.setStyleName("titledPanel");
		this.titleLabel = new Label(title);
		this.titleLabel.setStyleName("titledPanelLabel");
		this.add(titleLabel);
	}
	
}
