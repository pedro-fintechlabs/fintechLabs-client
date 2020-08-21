package com.javalabs.client.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.javalabs.shared.dto.Refund;

@Path("http://ec2-34-243-238-149.eu-west-1.compute.amazonaws.com:8080/refunds")
public interface IRefundService extends RestService {

	@GET
	@Path("/flux")
	public void flux(MethodCallback<List<Refund>> callback);
	
}
