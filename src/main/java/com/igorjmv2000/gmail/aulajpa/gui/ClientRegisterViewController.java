package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.enums.Genre;
import com.igorjmv2000.gmail.aulajpa.services.ClientService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ClientRegisterViewController implements Initializable{
	private boolean isRegister;

	private ClientService clientService = ConfigTest.getStaticClientService();
	
    @FXML private TextField textFieldId;
    @FXML private TextField textFieldName;

    @FXML private Label labelNameError;
    @FXML private Label labelBirthDateError;

    @FXML private DatePicker datePicker;

    @FXML private ComboBox<Genre> comboBoxGenre;

    @FXML private Button buttonCancel;
    @FXML private Button buttonOk;

    @FXML
    void onButtonCancelAction(ActionEvent event) {
    	((Stage)buttonCancel.getScene().getWindow()).close();
    }

    @FXML
    void onButtonOkAction(ActionEvent event) {
    	String name = textFieldName.getText();
    	try {
    		validate(name);
    		Date birthDate = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        	Genre genre = comboBoxGenre.getSelectionModel().getSelectedItem();
    		
    		ClientDTO dto = new ClientDTO(null, name, birthDate, genre);
    		if(isRegister)
    			clientService.insert(dto);
    		else {
    			Integer id = Integer.valueOf(textFieldId.getText());
    			clientService.update(id, dto);
    		}
    		((Stage)buttonOk.getScene().getWindow()).close();
    	}catch(IllegalArgumentException e) {
    		labelNameError.setText("Nome inválido");
    		labelBirthDateError.setText("");
    	}catch(NullPointerException e) {
    		labelBirthDateError.setText("Data inválida");
    		labelNameError.setText("");
    	}
    }
    
    private void validate(String name) {
    	name = name.trim().replaceAll("\\s+", "");
		if(name.isEmpty()) {
			throw new IllegalArgumentException("invalid argument");
		}
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

	public ComboBox<Genre> getComboBoxGenre() {
		return comboBoxGenre;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//comboBox config
		ObservableList<Genre> obsList = FXCollections.observableArrayList(Genre.values());
		comboBoxGenre.setItems(obsList);
		comboBoxGenre.getSelectionModel().clearAndSelect(0);
	}

}
