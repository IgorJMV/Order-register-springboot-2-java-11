package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.services.ProductService;
import com.igorjmv2000.gmail.aulajpa.services.exceptions.ObjectAssociationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProductViewController implements Initializable{
	private Node leadingUpNode;
	private ProductService productService = ConfigTest.getStaticProductService();
	private ProductDTO selectedObject;
	
    @FXML private Button buttonBack;

    @FXML private TextField textFieldSelectedObject;
    @FXML private TextField textFieldSearch;

    @FXML private Button buttonRegister;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonRemove;

    @FXML private TableView<ProductDTO> tableView;
    @FXML private TableColumn<ProductDTO, Integer> tableColumnId;
    @FXML private TableColumn<ProductDTO, String> tableColumnName;
    @FXML private TableColumn<ProductDTO, Double> tableColumnPrice;

    @FXML private ListView<CategoryDTO> listView;

    @FXML
    void onButtonBackAction(ActionEvent event) {
    	BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    	root.getChildren().remove(1);
    	root.setCenter(leadingUpNode);
    }

    @FXML
    void onButtonRegisterAction(ActionEvent event) {
    	openNewModalView("ProductRegisterView.fxml", event);
    }

    @FXML
    void onButtonRemoveAction(ActionEvent event) {
    	try {
    		productService.deleteById(selectedObject.getId());
    		refreshTable();
    	}catch(ObjectAssociationException e) {
    		Alert alert = new Alert(AlertType.INFORMATION);
    		alert.setTitle("Ação descontinuada");
    		alert.setHeaderText("Impossível deleter um produto que esteja em um pedido!");
    		alert.setContentText("O seguinte produto não foi removido: \n"
    				+ "		Id: " + selectedObject.getId() + "\n"
    				+ "		Nome: " + selectedObject.getName() + "\n"
    				+ "		Preço: R$" + String.format("%.2f", selectedObject.getPrice()));
    		alert.showAndWait();
    	}
    }

    @FXML
    void onButtonUpdateAction(ActionEvent event) {
    	openNewModalView("ProductRegisterView.fxml", event);
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
    		
    		ProductRegisterViewController controller = (ProductRegisterViewController)loader.getController();
    		if(event.getSource().equals(buttonRegister)) {
    			controller.setRegister(true);
    			stage.setTitle("Registrar novo produto");
    		}else if(event.getSource().equals(buttonUpdate)) {
    			controller.setRegister(false);
    			stage.setTitle("Atualizar produto");
    			
    			//set selectedObject
    			controller.getTextFieldId().setText(String.valueOf(selectedObject.getId()));
    			controller.getTextFieldName().setText(selectedObject.getName());
    			controller.getTextFieldPrice().setText(String.valueOf(selectedObject.getPrice()));
    			controller.getCategories().addAll(selectedObject.getCategories().stream().collect(Collectors.toList()));
    			controller.refreshTable();
    		}
    		
    		stage.initOwner(buttonRegister.getParent().getScene().getWindow());
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.setResizable(false);
    		stage.setOnHiding(e -> refreshTable());
    		stage.showAndWait();
    	}catch(IOException e) {
    		e.printStackTrace();
    	}
    }
    
    private void refreshTable() {
    	ObservableList<ProductDTO> obsList = FXCollections.observableArrayList(productService.findAll());
		tableView.setItems(obsList);
		tableView.refresh();
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = "file:///" + System.getProperty("user.dir") + "\\Icon";
		
		Image img = new Image(path + "\\Back.png");
		ImageView imgView = new ImageView(img);
		buttonBack.setGraphic(imgView);
		
		//TableConfig
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		
		//initial charge
		refreshTable();
		
		//Selected object
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateSelectedObject(event));
		tableView.addEventFilter(KeyEvent.KEY_RELEASED, event -> updateSelectedObject(event));
	
		//button initial config
		buttonRemove.setDisable(true);
		buttonUpdate.setDisable(true);
		
		//search
		textFieldSearch.textProperty().addListener((obs, oldVal, newVal) -> {
			List<ProductDTO> oldList;
			if(tableView.getItems().isEmpty() || newVal.length() < oldVal.length()) {
				oldList = productService.findAll();
			}else {
				oldList = tableView.getItems().stream().collect(Collectors.toList());
			}
			List<ProductDTO> newList = oldList.stream().filter(x -> x.getName().toUpperCase().contains(newVal.toUpperCase())).collect(Collectors.toList());
			tableView.setItems(FXCollections.observableArrayList(newList));
		});
    }
    
    private void updateSelectedObject(Event event) {
		try {
			selectedObject = tableView.getSelectionModel().getSelectedItem();
			textFieldSelectedObject.setText(selectedObject.getName());
			buttonRemove.setDisable(false);
			buttonUpdate.setDisable(false);
			
			//listView config
			ObservableList<CategoryDTO> categories = FXCollections.observableArrayList(selectedObject.getCategories());
			listView.setItems(categories);
		}catch(NullPointerException e) {
			textFieldSelectedObject.clear();
		}
	}

}
