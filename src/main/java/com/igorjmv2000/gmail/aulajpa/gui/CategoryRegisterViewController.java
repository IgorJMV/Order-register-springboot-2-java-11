package com.igorjmv2000.gmail.aulajpa.gui;

import java.util.List;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.gui.exceptions.ExistingObjectException;
import com.igorjmv2000.gmail.aulajpa.services.CategoryService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
    	}catch(ExistingObjectException e) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Ação descontinuada");
    		alert.setHeaderText(null);
    		alert.setContentText(e.getMessage());
    		alert.showAndWait();
    	}
    }
    
    private void validate(String text) {
    	String txt = text.trim().replaceAll("\\s+", "");
    	List<CategoryDTO> cat = categoryService.findAll().stream().filter(c -> c.getDescription().toUpperCase().equals(txt.toUpperCase())).collect(Collectors.toList());
		if(txt.isEmpty()) {
			throw new IllegalArgumentException("invalid argument");
		}
		if(!cat.isEmpty()) {
			throw new ExistingObjectException("Há uma categoria com a mesma descrição!");
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
