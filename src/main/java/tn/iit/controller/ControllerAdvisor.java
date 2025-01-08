package tn.iit.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tn.iit.exception.ClientNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import tn.iit.exception.CompteNotFoundException;
import tn.iit.exception.EmailAlreadyExistsException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
	@ExceptionHandler(CompteNotFoundException.class)
	public String handleCompteNotFoundException(CompteNotFoundException ex, Model model) {
		model.addAttribute("timestamp", LocalDateTime.now());
		model.addAttribute("message", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Exception ex, Model model) {
		model.addAttribute("timestamp", LocalDateTime.now());
		model.addAttribute("message", ex.getMessage());
		return "error";
	}

	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<String> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); // 400 Bad Request
	}
}
