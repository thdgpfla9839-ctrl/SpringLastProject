package com.sist.commons;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonsControllerExcepction {

	@ExceptionHandler(Exception.class)
	private void exception(Exception ex) 
	{
	
		System.out.println("Controller에서 예외발생");
		ex.printStackTrace();

	}
	
	
	@ExceptionHandler(Throwable.class)
	private void throwable(Throwable ex) 
	{
		
		System.out.println("Controller에서 에러발생");
		ex.printStackTrace();
		
	}
}
