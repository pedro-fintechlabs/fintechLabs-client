package com.javalabs.client.ui.user;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.javalabs.client.JavaLabs;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.client.ui.dialogs.CreateAccountDialog;
import com.javalabs.shared.dto.User;

public class LoginPanel extends HorizontalPanel {
	
	private Button 
		loginButton, 
		createAccountButton,
		dialogCloseButton;
	private Label 
		//nameLbl, 
		emailLbl, 
		passwordLbl, 
		//reenterPasswordLbl, 
		textToServerLbl, 
		errorLbl;
	private TextBox 
		//nameField, 
		emailField;
	private PasswordTextBox 
		passwordField/*, 
		reenterPasswordField*/
		;
	private HTML serverResponseLabel;
	private DialogBox dialogBox;

	public LoginPanel() {
		this.setStyleName("centerPanels");
		this.setSpacing(5);
		this.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		this.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		
		//nameLbl = new Label("Enter Name:");
		//nameField = new TextBox();

		emailLbl = new Label("Email:");
		emailField = new TextBox();

		passwordLbl = new Label("Password:");
		passwordField = new PasswordTextBox();
		passwordField.addFocusHandler(
			new FocusHandler() {
				@Override
			    public void onFocus(FocusEvent event) {
					errorLbl.setText("");
			    }
			});
/*
		reenterPasswordLbl = new Label("Renter Password:");
		reenterPasswordField = new PasswordTextBox();
		reenterPasswordField.addFocusHandler(
			new FocusHandler() {
				@Override
				public void onFocus(FocusEvent event) {
					errorLbl.setText("");
				}
			});
*/
		loginButton = new Button("Login");
		createAccountButton = new Button("Create Account");

		emailField.setFocus(true);
		emailField.selectAll();
		
		errorLbl = new Label();
		errorLbl.setStyleName("errorLbl");

		createDialogBox();

		loginButton.addClickHandler(event -> {
			if (emailField.getText().equals("")) {
				errorLbl.setText("Email cannot be empty!");
			}
			else if (passwordField.getText().equals("")) {
				errorLbl.setText("Password cannot be empty!");
			}
/*			
			else if (!passwordField.getText().equals(reenterPasswordField.getText())) {
				errorLbl.setText("Passwords must match!");
			} 
*/			
			else {
				textToServerLbl.setText(emailField.getText());
				callLoginService();
			}
		});
		
		createAccountButton.addClickHandler(event -> {
			CreateAccountDialog createAccountDialog = new CreateAccountDialog(
				emailField.getText(),
				passwordField.getText()
			);
			createAccountDialog.show();
		});

		this.add(emailLbl);
		this.add(emailField);
		this.add(passwordLbl);
		this.add(passwordField);
//		this.add(reenterPasswordLbl);
//		this.add(reenterPasswordField);		
		this.add(loginButton);
		this.add(createAccountButton);
		this.add(errorLbl);
	}

	public void clear() {
//		reenterPasswordField.setText("");
	}
	
	private void createDialogBox() {
		dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);

		dialogCloseButton = new Button("Close");
		dialogCloseButton.getElement().setId("closeButton");
		textToServerLbl = new Label();
		serverResponseLabel = new HTML();

		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");

		dialogVPanel.add(new HTML("<b>Login via email:</b>"));
		dialogVPanel.add(textToServerLbl);

		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);

		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(dialogCloseButton);
		dialogBox.setWidget(dialogVPanel);

		dialogCloseButton.addClickHandler(event -> {
			dialogBox.hide();
			loginButton.setEnabled(true);
			loginButton.setFocus(true);
		});
	}
	
	private void showDialogBox(String title, String htmlText) {
		dialogBox.setText(title);
		serverResponseLabel.setHTML(htmlText);
		dialogBox.center();
		dialogCloseButton.setFocus(true);
	}

	public void autoLogin() {
		emailField.setText("superman@mail");
		passwordField.setText("password"); 
		loginButton.click();
	}
	
	private void callLoginService() {
		//Window.alert("callLoginService");
		
		loginButton.getElement().getStyle().setCursor(Cursor.WAIT);
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		
		User user = new User();
		user.setFirstName("");
		user.setEmail(emailField.getText());
		user.setPassword(passwordField.getText());
		
		ServiceFactory.USER_SERVICE.login(user, new MethodCallback<String>() {

			@Override
			public void onSuccess(Method method, String response) {
				
				//Window.alert(response);
				
				loginButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				serverResponseLabel.removeStyleName("errorLbl");
				
				//showDialogBox("Login  - SUCCESS", "");
				//Window.alert("Login SUCCESS");
				
				JavaLabs.letsGo(response);
			}

			//TODO: Check why its FAILURE, do LOGIN_SUCCESS for now
			@Override
			public void onFailure(Method method, Throwable exception) {
				loginButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert(method.getResponse().getText());
				//Window.alert("Login FAILURE");

				JavaLabs.letsGo(method.getResponse().getText());
				
				/*
				serverResponseLabel.addStyleName("errorLbl");
				//showDialogBox("Login  - FAILURE", method.getResponse().getText());
				
				JSONValue responseValue = JSONParser.parse(method.getResponse().getText());
		        JSONObject responseObj = responseValue.isObject();
		        
		        errorLbl.setText(responseObj.get("message").isString().stringValue());
		        */
			}
		});
	}

/*	
	private void callLoadService() {
		loadAllButton.getElement().getStyle().setCursor(Cursor.WAIT);
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		
		ServiceFactory.USER_SERVICE.getAllUsers(new MethodCallback<List<User>>() {

			@Override
			public void onSuccess(Method method, List<User> response) {
				loadAllButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				serverResponseLabel.removeStyleName("errorLbl");
				String names = response.stream()
					.map(e -> "<li>" + 
						"User name:" + e.getName() +
						", User email:" + e.getEmail() +
						", ID: " + e.getId() + 
						"</li>")
					.collect(Collectors.joining(""));
				showDialogBox("REST endpoint call", "<ul>" + names + "<ul>");
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				loadAllButton.getElement().getStyle().setCursor(Cursor.DEFAULT);
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				serverResponseLabel.addStyleName("errorLbl");
				showDialogBox("REST endpoint call - Failure", IUIConstants.SERVER_ERROR);
			}
		});
	}
*/	
}
