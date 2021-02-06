package com.igorjmv2000.gmail.aulajpa.gui.exceptions;

public class ExistingObjectException extends RuntimeException{
	private static final long serialVersionUID = 1L;

	public ExistingObjectException(String msg) {
		super(msg);
	}
}
