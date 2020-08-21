package com.javalabs.client.service;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import com.javalabs.shared.dto.Email;
import com.javalabs.shared.dto.User;

//@Path("http://localhost:8080/users")
@Path("http://ec2-34-243-238-149.eu-west-1.compute.amazonaws.com:8080/users")
public interface IUserService extends RestService {

	@POST
	@Path("/user_exists")
	public void userExists(@BeanParam Email email, MethodCallback<User> callback);

	@POST
	@Path("/login")
	public void login(@BeanParam User user, MethodCallback<String> callback);
	
	@GET
	@Path("/flux")
	public void flux(MethodCallback<List<User>> callback);
	
	@POST
	@Path("/save")
	public void save(@BeanParam User user, MethodCallback<User> callback);

	@POST
	@Path("/delete")
	public void delete(@BeanParam User user, MethodCallback<List<User>> callback);
	
}
