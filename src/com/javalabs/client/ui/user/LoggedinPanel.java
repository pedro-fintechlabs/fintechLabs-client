package com.javalabs.client.ui.user;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.javalabs.client.JavaLabs;
import com.javalabs.shared.dto.User;

public class LoggedinPanel extends HorizontalPanel {
	
	public LoggedinPanel(User user) {
		this.setStyleName("loggedinPanel");
		this.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);

		VerticalPanel loginLogoutPanel = new VerticalPanel();
		
		Label loggedinLabel = new Label("Logged in as: " + user.getEmail());
		loggedinLabel.addStyleName("loggedinLabel");
		loginLogoutPanel.add(loggedinLabel);
		
		Anchor logoutAnchor = new Anchor("Log out");
		loginLogoutPanel.add(logoutAnchor);
		logoutAnchor.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if (Window.confirm("Are you sure you want to log out?")) {
					JavaLabs.logOut();
				}
			}
	      });
		
		this.add(loginLogoutPanel);
		
		// TODO get User image
		Image userAvatarImage;
		if (JavaLabs.GET_USER().getEmail().equals("superman@mail")) {
			userAvatarImage = new Image("images/superman_avatar.jpg");
		} else {
			userAvatarImage = new Image("images/user_avatar.jpg");
		}
		userAvatarImage.setPixelSize(50, 50);
		this.add(userAvatarImage);
		
	}
}
