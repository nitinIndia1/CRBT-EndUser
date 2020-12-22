package com.crbt.api.services.exception;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.crbt.api.services.bean.ErrorMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@ControllerAdvice
public class DefaultExceptionHandler {

	// Exception handler for null result returned when search
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFound(ResourceNotFoundException ex) {
		ErrorMessage response = new ErrorMessage();
		response.setErrorField("Not Found");
		response.setErrorCode("404");
		response.setErrorDescription(ex.getMessage());
		return new ResponseEntity<ErrorMessage>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	public ResponseEntity<?> methodNotSupportErrorHandler(HttpServletRequest request, Exception e) throws Exception {
		RestError error = new RestError("BadRequestException", 400, "Method not supported");
		return new ResponseEntity<RestError>(error, HttpStatus.BAD_REQUEST);
	}

	// Exception for handling wrong data format
	@ExceptionHandler(value = { HttpMessageNotReadableException.class, InvalidFormatException.class })
	public ResponseEntity<?> invalidDataFormat(HttpServletRequest request, Exception e) throws Exception {
		RestError error = new RestError("InvalidFormatException", 400, "Wrong data format!");
		return new ResponseEntity<RestError>(error, HttpStatus.BAD_REQUEST);
	}

	// Exception handler for controller not found
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ResponseBody
	public RestError requestHandlingNoHandlerFound() {
		return new RestError("BadRequestException", 404, "Requested resource is not found!");
	}

	// Exception handler for illegal params after controller
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorMessage> badHttpRequest(MethodArgumentTypeMismatchException ex) {
		ErrorMessage response = new ErrorMessage();
		response.setErrorField("Bad Request");
		response.setErrorCode("400");
		response.setErrorDescription("Query parameter is not valid!");

		return new ResponseEntity<ErrorMessage>(response, HttpStatus.BAD_REQUEST);
	}

	@JsonPropertyOrder(value = { "error_type", "code", "error_message" })
	public class RestError {

		@JsonProperty("code")
		int code;

		@JsonProperty("error_type")
		String type;

		@JsonProperty("error_message")
		String message;

		public RestError() {
			super();
		}

		public RestError(String type, int code, String message) {
			this.code = code;
			this.type = type;
			this.message = message;
		}

		public int getCode() {
			return code;
		}

		public void setCode(int code) {
			this.code = code;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
