package com.javalabs.client.ui.user;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.javalabs.client.constants.Role;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.client.ui.ModelType;
import com.javalabs.client.ui.NameValuePanel;
import com.javalabs.client.ui.TitledPanel;
import com.javalabs.shared.dto.User;

public class UserEditPanel extends TitledPanel {

	private boolean isNew;
	
	private User user;
	private UsersPanel parent;
	
	private NameValuePanel panelEmail;
	private NameValuePanel panelPassword;
	private NameValuePanel panelConfirmPassword;
	private NameValuePanel panelRoleMaster;
	private NameValuePanel panelRoleAgent;
	private NameValuePanel panelRoleMerchants;
	
	private Button saveButton = new Button("Save");
	private Label msgLabel = new Label("");
	
	public UserEditPanel(UsersPanel parent, User user) {
		super(user == null ? "New User" : "Edit User");

		this.parent = parent;
		this.user = user;
	
		this.setSpacing(10);
		
		init();
	}
	
	@SuppressWarnings("deprecation")
	private void init() {
		
		if (user == null) { // create blank
			isNew = true;
			user = new User();
		}

		HorizontalPanel panelNameSection = new HorizontalPanel();
		panelNameSection.setSpacing(10);
		
		NameValuePanel panelFirstName = new NameValuePanel("First Name", ModelType.STRING);
		((TextBox)panelFirstName.getWidgetValue()).setText(user.getFirstName());
		panelNameSection.add(panelFirstName);

		NameValuePanel panelLastName = new NameValuePanel("Last Name", ModelType.STRING);
		((TextBox)panelLastName.getWidgetValue()).setText(user.getLastName());
		panelNameSection.add(panelLastName);
		
		this.add(panelNameSection);

		HorizontalPanel panelEmailSection = new HorizontalPanel();
		panelEmailSection.setSpacing(10);
		
		panelEmail = new NameValuePanel("Email", ModelType.STRING);
		((TextBox)panelEmail.getWidgetValue()).setText(user.getEmail());
		panelEmailSection.add(panelEmail);

		this.add(panelEmailSection);

		HorizontalPanel panelPasswordSection = new HorizontalPanel();
		panelPasswordSection.setSpacing(10);

		panelPassword = new NameValuePanel("Password", ModelType.PASSWORD);	
		((TextBox)panelPassword.getWidgetValue()).setText(user.getPassword());
		panelPasswordSection.add(panelPassword);

		panelConfirmPassword = new NameValuePanel("Confirm Password", ModelType.PASSWORD);
		((TextBox)panelConfirmPassword.getWidgetValue()).setText(user.getPassword());
		panelPasswordSection.add(panelConfirmPassword);
		
		this.add(panelPasswordSection);
		
		HorizontalPanel panelRolesSection = new HorizontalPanel();
		panelRolesSection.setSpacing(10);

		panelRoleMaster = new NameValuePanel(Role.master.toString(), ModelType.BOOLEAN);
		((CheckBox)panelRoleMaster.getWidgetValue()).setValue(user.getRoles().contains(Role.master.toString()));
		panelRolesSection.add(panelRoleMaster);

		panelRoleAgent = new NameValuePanel(Role.agent.toString(), ModelType.BOOLEAN);
		((CheckBox)panelRoleAgent.getWidgetValue()).setValue(user.getRoles().contains(Role.agent.toString()));
		panelRolesSection.add(panelRoleAgent);

		panelRoleMerchants = new NameValuePanel(Role.merchants.toString(), ModelType.BOOLEAN);
		((CheckBox)panelRoleMerchants.getWidgetValue()).setValue(user.getRoles().contains(Role.merchants.toString()));
		panelRolesSection.add(panelRoleMerchants);
		
		this.add(panelRolesSection);
		
		saveButton.addClickHandler(event -> {
			if (this.isNew && parent.userExists(((TextBox)panelEmail.getWidgetValue()).getText())) {
				this.msgLabel.setText("User already exists!");
				return;
			}
			
			user.setFirstName(((TextBox)panelFirstName.getWidgetValue()).getText());
			user.setLastName(((TextBox)panelLastName.getWidgetValue()).getText());
			user.setEmail(((TextBox)panelEmail.getWidgetValue()).getText());
			user.setPassword(((TextBox)panelPassword.getWidgetValue()).getText());
			
			String roles = "";
			if (((CheckBox)panelRoleMaster.getWidgetValue()).isChecked()) {
				if (!roles.equals("")) {
					roles = roles + ",";
				}
				roles = roles + Role.master.toString();
			}
			if (((CheckBox)panelRoleAgent.getWidgetValue()).isChecked()) {
				if (!roles.equals("")) {
					roles = roles + ",";
				}
				roles = roles + Role.agent.toString();
			}
			if (((CheckBox)panelRoleMerchants.getWidgetValue()).isChecked()) {
				if (!roles.equals("")) {
					roles = roles + ",";
				}				
				roles = roles + Role.merchants.toString();
			}
			user.setRoles(roles);
			
			callUserSaveService();

		});
		
		this.add(saveButton);

		msgLabel.setStyleName("errorLbl");
		this.add(msgLabel);
	}
		
	private boolean validate() {
		if (((TextBox)panelEmail.getWidgetValue()).getText().equals("")) {
			this.msgLabel.setText("Email cannot be empty!");
			return false;
		}
		if (((TextBox)panelPassword.getWidgetValue()).getText().equals("")) {
			this.msgLabel.setText("Password cannot be empty!");
			return false;
		}		
		if (!((TextBox)panelPassword.getWidgetValue()).getText().equals(((TextBox)panelConfirmPassword.getWidgetValue()).getText())) {
			this.msgLabel.setText("Password and Confirm must match!");
			return false;			
		}
		if (!((CheckBox)panelRoleMaster.getWidgetValue()).isChecked() 
				&& !((CheckBox)panelRoleAgent.getWidgetValue()).isChecked()
				&& !((CheckBox)panelRoleMerchants.getWidgetValue()).isChecked()
		) {
			this.msgLabel.setText("Please select at least one Role.");
			return false;						
		}
		
		this.msgLabel.setText("");
		
		return true;
	}
	
	private void callUserSaveService() {
		//saveButton.getElement().getStyle().setCursor(Cursor.WAIT);
		//RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		
		if (!validate()) {
			return;
		}
		
		//Window.alert("callUserSaveService()");
		
		ServiceFactory.USER_SERVICE.save(user, new MethodCallback<User>() {

			@Override
			public void onSuccess(Method method, User response) {
				saveButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				parent.refresh();
				
				msgLabel.setStyleName("infoLbl");
				msgLabel.setText("User Saved!");
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				saveButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
			}
		});
	}
	
}
