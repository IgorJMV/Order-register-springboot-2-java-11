package com.igorjmv2000.gmail.aulajpa.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ClientRegisterViewController {
	private boolean isRegister;

    @FXML private TextField textFieldId;
    @FXML private TextField textFieldName;

    @FXML private Label labelNameError;

    @FXML private DatePicker datePicker;

    @FXML private ComboBox<?> comboBoxGenre;

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

	public TextField getTextFieldName() {
		return textFieldName;
	}

	public DatePicker getDatePicker() {
		return datePicker;
	}

	public ComboBox<?> getComboBoxGenre() {
		return comboBoxGenre;
	}

}
