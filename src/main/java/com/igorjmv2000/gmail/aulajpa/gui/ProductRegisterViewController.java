package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.dao.InvalidDataAccessApiUsageException;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.gui.exceptions.EmptyCollectionException;
import com.igorjmv2000.gmail.aulajpa.gui.util.Constraints;
import com.igorjmv2000.gmail.aulajpa.services.CategoryService;
import com.igorjmv2000.gmail.aulajpa.services.ProductService;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.stage.Stage;

public class ProductRegisterViewController implements Initializable{
	private boolean isRegister;
	
	private CategoryService categoryService = ConfigTest.getStaticCategoryService();
	
	private ProductService productService = ConfigTest.getStaticProductService();
	
	private List<CategoryDTO> categories = new ArrayList<>();

    @FXML private TextField textFieldId;
    @FXML private TextField textFieldName;
    @FXML private TextField textFieldPrice;

    @FXML private Label labelNameError;
    @FXML private Label labelPriceError;

    @FXML private TableView<CategoryDTO> tableView;
    @FXML private TableColumn<CategoryDTO, CategoryDTO> tableColumnCategory;
    @FXML private TableColumn<CategoryDTO, CategoryDTO> tableColumnRemove;

    @FXML private Button buttonMore;
    @FXML private Button buttonCancel;
    @FXML private Button buttonOk;

    @FXML
    void onButtonCancelAction(ActionEvent event) {
    	((Stage)buttonCancel.getScene().getWindow()).close();
    }

    @FXML
    void onButtonMoreAction(ActionEvent event) {
    	categories.add(new CategoryDTO(null, "Clique para escolher uma categoria"));
    	refreshTable();
    }

    @FXML
    void onButtonOkAction(ActionEvent event) {
    	String name = textFieldName.getText();
    	try {
    		Double price = Double.valueOf(textFieldPrice.getText());
    		validate(name);
    		
    		ProductDTO dto = new ProductDTO(null, name, price);
    		dto.getCategories().addAll(categories.stream().collect(Collectors.toSet()));
    		if(isRegister)
    			productService.insert(dto);
    		else {
    			Integer id = Integer.valueOf(textFieldId.getText());
    			productService.update(id, dto);
    		}
    		((Stage)buttonCancel.getScene().getWindow()).close();
    	}catch(NumberFormatException e) {
    		labelPriceError.setText("Valor inválido");
    		labelNameError.setText("");
    	}catch(IllegalArgumentException e) {
    		labelNameError.setText("Nome inválido");
    		labelPriceError.setText("");
    	}catch(EmptyCollectionException | InvalidDataAccessApiUsageException e) {
    		labelNameError.setText("");
    		labelPriceError.setText("");
    		Alert alert = new Alert(AlertType.ERROR);
    		alert.setTitle("Erro");
    		alert.setHeaderText(null);
    		alert.setContentText("Esse produto precisa estar em, pelo menos, uma categoria.");
    		alert.showAndWait();
    	}
    }
    
    private void validate(String name) {
		name = name.trim().replaceAll("\\s+", "");
		if(name.isEmpty()) {
			throw new IllegalArgumentException("invalid name");
		}
		if(categories.isEmpty()) {
			throw new EmptyCollectionException("list empty");
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

	public TextField getTextFieldPrice() {
		return textFieldPrice;
	}
	
	public List<CategoryDTO> getCategories(){
		return categories;
	}
	
	private void initRemoveButtons() {
		tableColumnRemove.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnRemove.setCellFactory(param -> new TableCell<CategoryDTO, CategoryDTO>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(CategoryDTO obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void initComboBox() {
		tableColumnCategory.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnCategory.setCellFactory(ComboBoxTableCell.forTableColumn(
				FXCollections.observableArrayList(categoryService.findAll())
		));
	}

	private void removeEntity(CategoryDTO obj) {
		categories.removeIf(c -> c.getDescription().equals(obj.getDescription()));
		System.out.println(categories.size());
		refreshTable();
	}
	
	protected void refreshTable() {		
		ObservableList<CategoryDTO> obs = FXCollections.observableArrayList(categories);
		tableView.setItems(obs);
		initRemoveButtons();
		initComboBox();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView.setEditable(true);
		
		Constraints.setTextFieldDouble(textFieldPrice);
		
		//initial category
		refreshTable();
		
		tableColumnCategory.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<CategoryDTO,CategoryDTO>>() {
			
			@Override
			public void handle(CellEditEvent<CategoryDTO, CategoryDTO> event) {
				boolean control = false;
				
				int index = event.getTablePosition().getRow();
				try {
					if(categories.get(index) != null) {
						control = true;
					}
				
					categories.add(index, event.getNewValue());
				
					if(control)
						categories.remove(index + 1);
				}catch(IndexOutOfBoundsException e) {
					categories.add(index, event.getNewValue());
				}
				
			}
		});
		
	}

}
