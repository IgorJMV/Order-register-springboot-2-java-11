package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.services.CategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CategoryViewController implements Initializable{
	private Node leadingUpNode;
	private CategoryService categoryService = ConfigTest.getStaticCategoryService();
	private CategoryDTO selectedObject;
	
    @FXML private Button buttonBack;

    @FXML private TextField textFieldSelectedObject;
    @FXML private TextField textFieldSearch;

    @FXML private Button buttonRegister;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonRemove;

    @FXML private TableView<CategoryDTO> tableView;
    @FXML private TableColumn<CategoryDTO, Integer> tableColumnId;
    @FXML private TableColumn<CategoryDTO, String> tableColumnDescription;

    @FXML private ListView<ProductDTO> listView;

    @FXML
    void onButtonBackAction(ActionEvent event) {
    	BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    	root.getChildren().remove(1);
    	root.setCenter(leadingUpNode);
    }

    @FXML
    void onButtonRegisterAction(ActionEvent event) {
    	openNewModalView("CategoryRegisterView.fxml", event);
    }

    @FXML
    void onButtonRemoveAction(ActionEvent event) {

    }

    @FXML
    void onButtonUpdateAction(ActionEvent event) {
    	openNewModalView("CategoryRegisterView.fxml", event);
    }
    
    public void setLeadingUpNode(Node node) {
    	leadingUpNode = node;
    }
    
    private void openNewModalView(String name, ActionEvent event) {
    	try {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
    		BorderPane root = loader.load();    		
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		
    		CategoryRegisterViewController controller = (CategoryRegisterViewController)loader.getController();
    		if(event.getSource().equals(buttonRegister)) {
    			controller.setRegister(true);
    			stage.setTitle("Registrar nova categoria");
    		}else if(event.getSource().equals(buttonUpdate)) {
    			controller.setRegister(false);
    			stage.setTitle("Atualizar categoria");
    			
    			//set selectedObject
    			controller.getTextFieldId().setText(String.valueOf(selectedObject.getId()));
    			controller.getTextFieldDescription().setText(selectedObject.getDescription());
    		}
    		
    		stage.initOwner(buttonRegister.getParent().getScene().getWindow());
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setResizable(false);
    		stage.showAndWait();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = "file:///" + System.getProperty("user.dir") + "\\Icon";
		
		Image img = new Image(path + "\\Back.png");
		ImageView imgView = new ImageView(img);
		buttonBack.setGraphic(imgView);
		
		//table config
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		//initial charge
		ObservableList<CategoryDTO> obsList = FXCollections.observableArrayList(categoryService.findAll());
		tableView.setItems(obsList);
		
		//Selected object
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateSelectedObject(event));
		tableView.addEventFilter(KeyEvent.KEY_RELEASED, event -> updateSelectedObject(event));
		
		//button initial config
		buttonRemove.setDisable(true);
		buttonUpdate.setDisable(true);
		
		//search
		textFieldSearch.textProperty().addListener((obs, oldVal, newVal) -> {
			List<CategoryDTO> oldList;
			if(tableView.getItems().isEmpty() || newVal.length() < oldVal.length()) {
				oldList = categoryService.findAll();
			}else {
				oldList = tableView.getItems().stream().collect(Collectors.toList());
			}
			List<CategoryDTO> newList = oldList.stream().filter(x -> x.getDescription().toUpperCase().contains(newVal.toUpperCase())).collect(Collectors.toList());
			tableView.setItems(FXCollections.observableArrayList(newList));
		});
    }

	private void updateSelectedObject(Event event) {
		try {
			selectedObject = tableView.getSelectionModel().getSelectedItem();
			textFieldSelectedObject.setText(selectedObject.getDescription());
			buttonRemove.setDisable(false);
			buttonUpdate.setDisable(false);
		}catch(NullPointerException e) {
			textFieldSelectedObject.clear();
		}
	}

}
