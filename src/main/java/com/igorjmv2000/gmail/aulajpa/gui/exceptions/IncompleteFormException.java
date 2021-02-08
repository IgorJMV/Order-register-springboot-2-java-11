package com.igorjmv2000.gmail.aulajpa.gui.exceptions;

public class IncompleteFormException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private Boolean control;
	
	public IncompleteFormException(String msg, Boolean control) {
		super(msg);
		this.control = control;
	}

	public Boolean getControl() {
		return control;
	}

	public void setControl(Boolean control) {
		this.control = control;
	}
	
}
