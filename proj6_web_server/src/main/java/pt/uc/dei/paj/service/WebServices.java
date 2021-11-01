package pt.uc.dei.paj.service;

import javax.ws.rs.core.Response;

import pt.uc.dei.paj.dto.HttpMessageDto;

public abstract class WebServices {

	protected static final String AUTHENTICATION_FAILED = "Authentication failed.";
	protected static final String NO_PERMISSIONS = "No permissions.";
	protected static final String NOT_FOUND = "Not found.";
	protected static final String VALIDATION_ERROR = "Validation error.";
	protected static final String ERROR_NOT_IDENTIFIED = "Error not identified.";

	protected Response defaultErrors(int status) {
		switch(status) {
		case 400:
			return Response.status(status)
					.entity(new HttpMessageDto(WebServices.VALIDATION_ERROR)).build();
		case 401:
			return Response.status(status)
					.entity(new HttpMessageDto(WebServices.AUTHENTICATION_FAILED)).build();
		case 403:
			return Response.status(status)
					.entity(new HttpMessageDto(WebServices.NO_PERMISSIONS)).build();
		case 404:
			return Response.status(status)
					.entity(new HttpMessageDto(WebServices.NOT_FOUND)).build();
		case 409:
			return Response.status(status)
					.entity(new HttpMessageDto(WebServices.AUTHENTICATION_FAILED)).build();		
		}
		System.out.println("Erro: WebUserServices.register > " + status);
		return Response.status(status)
				.entity(new HttpMessageDto(WebServices.ERROR_NOT_IDENTIFIED)).build();			
	}
	
}
