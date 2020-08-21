package com.javalabs.client.ui.audit;

import java.util.Date;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.javalabs.client.JavaLabs;
import com.javalabs.client.service.ServiceFactory;
import com.javalabs.shared.dto.Audit;

public class AuditFactory {

	static public void log(AuditEvent event) {

		//Window.alert(JavaLabs.GET_USER().toString());
		
		Audit auditEntry = new Audit(
			"", //String userType 
			0L, //JavaLabs.GET_USER().getId(), //Long userId 
			JavaLabs.GET_USER().getEmail(), //String userEmail
			event.toString(), //String event, 
			true, //Boolean auditable, 
			"", //String oldValues, 
			"", //String newValues,
			Window.Location.getHostName(), //String url, 
			Window.Location.getHost(), //String ipAddress, 
			getAppName(), //String userAgent, 
			"", //String tags, 
			new Date().toString() //Date timeStamp
		);
		
		callAuditService(auditEntry);
	}
	
	static private void callAuditService(Audit auditEntry) {
		//Window.alert("callAuditService:\n" + auditEntry.getEvent());
		
		ServiceFactory.AUDIT_SERVICE.log(auditEntry, new MethodCallback<String>() {

			@Override
			public void onSuccess(Method method, String response) {
				//Window.alert(response);
			}

			@Override
			public void onFailure(Method method, Throwable exception) {
				//GWT.log("ERROR: Unable to create LOG entry:\n" + exception.getMessage());
			}
		});
	}

	/**
	 * Gets the navigator.appName.
	 *
	 * @return the window's navigator.appName.
	 */
	public static native String getAppName() /*-{
	  return $wnd.navigator.appName;
	}-*/;
}
