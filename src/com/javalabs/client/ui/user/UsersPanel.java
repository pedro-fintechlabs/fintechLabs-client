package com.javalabs.client.ui.user;

import java.util.Date;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.client.ui.TitledPanel;
import com.javalabs.shared.dto.User;

public class UsersPanel extends TitledPanel {

	private List<User> USER_DATA;
	private User selectedUser;
	
	private HorizontalPanel panel = new HorizontalPanel();
	private CellTable<User> table;
	private UserEditPanel panelUserEdit;
	private Button 
		buttonNewUser,
		buttonDeleteUser;

	private Label labelFectchInfo = new Label("Fetching...");
	private Date startDate;
	private Date endDate;
	
	public UsersPanel() {
		super("Users");

		this.setSpacing(20);		
		this.init();

		callGetUsersService();
	}
	
	private void init() {

		  table = new CellTable<User>();
		  table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);
		
		  TextColumn<User> idColumn = new TextColumn<User>() {
		     @Override
		     public String getValue(User object) {
		        return object.getId().toString();
		     }
		  };
		  table.addColumn(idColumn, "ID");

		  TextColumn<User> firstNameColumn = new TextColumn<User>() {
		     @Override
		     public String getValue(User object) {
		        return object.getFirstName() != null ? object.getFirstName().toString() : "";
		     }
		  };
		  table.addColumn(firstNameColumn, "First Name");

		  TextColumn<User> lastNameColumn = new TextColumn<User>() {
		     @Override
		     public String getValue(User object) {
		        return object.getLastName() != null ? object.getLastName().toString() : "";
		     }
		  };
		  table.addColumn(lastNameColumn, "Last Name");
		  
		  TextColumn<User> emailColumn = new TextColumn<User>() {
		     @Override
		     public String getValue(User object) {
		        return object.getEmail().toString();
		     }
		  };
		  table.addColumn(emailColumn, "Email");
		  		  
		  TextColumn<User> enabledColumn = new TextColumn<User>() {
		     @Override
		     public String getValue(User object) {
		        return object.getEnabled();
		     }
		  };
		  table.addColumn(enabledColumn, "Enabled");

		  TextColumn<User> rolesColumn = new TextColumn<User>() {
		     @Override
		     public String getValue(User object) {
		        return object.getRoles();
		     }
		  };
		  table.addColumn(rolesColumn, "Roles");
		  
		  /*
		  SimplePager.Resources resources = GWT.create(SimplePager.Resources.class); 
		  SimplePager simplePager = new SimplePager(TextLocation.CENTER, resources , false, 0, true);
		  simplePager.setDisplay(table);
		  simplePager.setPageSize(5);
		  this.add(simplePager);
		  */

		  // Add a selection model to handle user selection.
		  final SingleSelectionModel<User> selectionModel = new SingleSelectionModel<User>();
		  table.setSelectionModel(selectionModel);
		  selectionModel.addSelectionChangeHandler(
			  new SelectionChangeEvent.Handler() {
			     public void onSelectionChange(SelectionChangeEvent event) {
			    	User selected = selectionModel.getSelectedObject();
			        if (selected != null) {
			        	//Window.alert("You selected: " + selected.getId());
			        	selectedUser = selected;
			        	initModelPanel(selected);
			        	buttonDeleteUser.setEnabled(true);
			        }
			     }
			  }
		  );
		  
		  //panel.setBorderWidth(1);
		  panel.setSpacing(10);
		  panel.add(table);

		  this.add(labelFectchInfo);
		  this.add(panel);

		  // Buttons
		  buttonNewUser = new Button("New User");
		  buttonNewUser.addClickHandler(event -> {
			  initModelPanelNewUser();
		  });

		  buttonDeleteUser = new Button("Delete User");
		  buttonDeleteUser.addClickHandler(event -> {
			  callDeleteUserService();
		  });
		  
		  HorizontalPanel panelButtons = new HorizontalPanel();
		  panelButtons.setSpacing(10);
		  panelButtons.add(buttonNewUser);
//		  panelButtons.add(buttonDeleteUser);
		  
		  this.add(panelButtons);
	}		

	private void initModelPanelNewUser() {
		if (panelUserEdit != null) {
			panelUserEdit.removeFromParent();
		}
		
		panelUserEdit = new UserEditPanel(this, null);
		panel.add(panelUserEdit);
	}
	
	private void initModelPanel(User user) {
		if (panelUserEdit != null) {
			panelUserEdit.removeFromParent();
		}
		
		panelUserEdit = new UserEditPanel(this, user);
		this.add(panelUserEdit);
	}
	
	public void setModel(List<User> model) {
		USER_DATA = model;
		
		GWT.log("USER_DATA.size: " + USER_DATA.size());
		
		table.setPageSize(50);
		table.setRowData(0, USER_DATA);
		table.setRowCount(USER_DATA.size(), true);
	}
	
	public boolean userExists(String email) {
		for (User user: USER_DATA) {
			if (user.getEmail().equals(email)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void refresh() {
		callGetUsersService();
	}
	
	private void callGetUsersService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		
		startDate = new Date();
		
		ServiceFactory.USER_SERVICE.flux(new MethodCallback<List<User>>() {

			@Override
			public void onSuccess(Method method, List<User> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callGetUsersService - SUCCESS\n");
				
				setModel(response);
				
				endDate = new Date();
				labelFectchInfo.setText("Fetched " + response.size() + " Users in " + 
						(endDate.getTime()-startDate.getTime()) + "ms"
				);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
		        Window.alert("callGetUsersService - FAILURE:\n"  + method.getResponse().getText());
			}
		});		
	}

	private void callDeleteUserService() {
		RootPanel.getBodyElement().getStyle().setCursor(Cursor.WAIT);
		
		ServiceFactory.USER_SERVICE.delete(selectedUser, new MethodCallback<List<User>>() {

			@Override
			public void onSuccess(Method method, List<User> response) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
				//Window.alert("callDeleteUserService - SUCCESS\n");
				
				setModel(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				RootPanel.getBodyElement().getStyle().setCursor(Cursor.DEFAULT);
				
		        Window.alert("callDeleteUserService - FAILURE:\n"  + method.getResponse().getText());
			}
		});		
	}
	
}
