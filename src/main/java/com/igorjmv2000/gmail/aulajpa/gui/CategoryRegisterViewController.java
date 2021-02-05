package com.igorjmv2000.gmail.aulajpa.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CategoryRegisterViewController{
	private boolean isRegister;
	
    @FXML private TextField textFieldId;
    @FXML private TextField textFieldDescription;

    @FXML private Label labelError;

    @FXML private Button buttonCancel;
    @FXML private Button buttonOk;

    @FXML
    void onButtonCancelAction(ActionEvent event) {

    }

    @FXML
    void onButtonOkAction(ActionEvent event) {

    }
    
    public void setRegister(boolean isRegister) {
    	this.isRegister = isRegister;
    }

	public TextField getTextFieldId() {
		return textFieldId;
	}

	public TextField getTextFieldDescription() {
		return textFieldDescription;
	}

}
