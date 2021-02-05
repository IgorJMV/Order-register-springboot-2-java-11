package com.igorjmv2000.gmail.aulajpa.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductRegisterViewController {
	private boolean isRegister;

    @FXML private TextField textFieldId;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldPrice;

    @FXML private Label labelNameError;
    @FXML private Label labelPriceError;

    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> tableColumnCategory;
    @FXML private TableColumn<?, ?> tableColumnRemove;

    @FXML private Button buttonMore;
    @FXML private Button buttonCancel;
    @FXML private Button buttonOk;

    @FXML
    void onButtonCancelAction(ActionEvent event) {

    }

    @FXML
    void onButtonMoreAction(ActionEvent event) {

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

	public TextField getTextFieldPrice() {
		return textFieldPrice;
	}

}
