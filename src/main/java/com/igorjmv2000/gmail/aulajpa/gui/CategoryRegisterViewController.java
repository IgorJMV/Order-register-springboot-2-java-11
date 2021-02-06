package com.igorjmv2000.gmail.aulajpa.gui;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.services.CategoryService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CategoryRegisterViewController{
	private boolean isRegister;
	
	private CategoryService categoryService = ConfigTest.getStaticCategoryService();
	
    @FXML private TextField textFieldId;
    @FXML private TextField textFieldDescription;

    @FXML private Label labelError;

    @FXML private Button buttonCancel;
    @FXML private Button buttonOk;

    @FXML
    void onButtonCancelAction(ActionEvent event) {
    	((Stage)buttonCancel.getScene().getWindow()).close();
    }

    @FXML
    void onButtonOkAction(ActionEvent event) {
    	String description = textFieldDescription.getText();
    	try {
    		validate(description);
    		CategoryDTO dto = new CategoryDTO(null, description);
    		if(isRegister)
    			categoryService.insert(dto);
    		else {
    			Integer id = Integer.valueOf(textFieldId.getText());
    			categoryService.update(id, dto);
    		}
    		((Stage)buttonOk.getScene().getWindow()).close();
    	}catch(IllegalArgumentException e) {
    		labelError.setText("Descrição inválida");
    	}
    }
    
    private void validate(String text) {
    	text = text.trim().replaceAll("\\s+", "");
		if(text.isEmpty()) {
			throw new IllegalArgumentException("invalid argument");
		}
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
