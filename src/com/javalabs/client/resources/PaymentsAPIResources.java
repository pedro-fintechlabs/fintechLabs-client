package com.javalabs.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface PaymentsAPIResources extends ClientBundle {
	
	    public static final PaymentsAPIResources INSTANCE = GWT.create(PaymentsAPIResources.class);

	    @Source("index.html")
	    TextResource synchronous();

	}