package com.hunt.poi.engine;

public class TemplateRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TemplateRuntimeException() {
		super();
	}
	
	public TemplateRuntimeException(String message) {
		super(message);
	}
	
	public TemplateRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
	
	public TemplateRuntimeException(Throwable cause) {
        super(cause);
    }

}
