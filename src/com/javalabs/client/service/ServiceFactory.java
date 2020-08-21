package com.javalabs.client.service;

import com.google.gwt.core.client.GWT;

public class ServiceFactory {

	public static final IUserService USER_SERVICE = GWT.create(IUserService.class);
	public static final ICountryService COUNTRY_SERVICE = GWT.create(ICountryService.class);
	public static final IPaymentService PAYMENT_SERVICE = GWT.create(IPaymentService.class);
	public static final IRefundService REFUND_SERVICE = GWT.create(IRefundService.class);
	public static final IReversalService REVERSAL_SERVICE = GWT.create(IReversalService.class);
	public static final IAddressService ADDRESS_SERVICE = GWT.create(IAddressService.class);
	public static final IStateService STATE_SERVICE = GWT.create(IStateService.class);
	public static final ICityService CITY_SERVICE = GWT.create(ICityService.class);
	public static final ICountyService COUNTY_SERVICE = GWT.create(ICountyService.class);
	public static final IZipcodeService ZIPCODE_SERVICE = GWT.create(IZipcodeService.class);
	public static final IAuditService AUDIT_SERVICE = GWT.create(IAuditService.class);

}
