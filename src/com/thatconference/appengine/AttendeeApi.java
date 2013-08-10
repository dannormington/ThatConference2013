package com.thatconference.appengine;

import com.google.api.server.spi.ServiceException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

@Api(
		name="thatconference",
		version = "v1",
		description = "That Conference Sample API",
		clientIds = {com.google.api.server.spi.Constant.API_EXPLORER_CLIENT_ID},
		scopes = "https://www.googleapis.com/auth/userinfo.email"
		)
public class AttendeeApi {
	
	@ApiMethod(
		httpMethod = "POST",
		path = "register"
	)
	public AttendeeRegistrationResult register(
			RegisterAttendeeCommand command,
			User user) throws ServiceException{
		
		if(user == null)
    		throw new UnauthorizedException("The user is not authorized.");	
		
		AttendeeService service = new AttendeeService();
		return service.register(user, command.getFirstName(), command.getLastName());
	}
}
