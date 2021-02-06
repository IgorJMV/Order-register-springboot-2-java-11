package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.enums.Genre;
import com.igorjmv2000.gmail.aulajpa.services.ClientService;

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

public class ClientViewController implements Initializable{
	private Node leadingUpNode;
	private ClientService clientService = ConfigTest.getStaticClientService();
	private ClientDTO selectedObject;
	
    @FXML private Button buttonBack;

    @FXML private TextField textFieldSelectedObject;
    @FXML private TextField textFieldSearch;

    @FXML private Button buttonRegister;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonRemove;

    @FXML private TableView<ClientDTO> tableView;
    @FXML private TableColumn<ClientDTO, Integer> tableColumnId;
    @FXML private TableColumn<ClientDTO, String> tableColumnName;
    @FXML private TableColumn<ClientDTO, Date> tableColumnBirthDate;
    @FXML private TableColumn<ClientDTO, Genre> tableColumnGenre;

    @FXML
    void onButtonBackAction(ActionEvent event) {
    	BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    	root.getChildren().remove(1);
    	root.setCenter(leadingUpNode);
    }

    @FXML
    void onButtonRegisterAction(ActionEvent event) {
    	openNewModalView("ClientRegisterView.fxml", event);
    }

    @FXML
    void onButtonRemoveAction(ActionEvent event) {

    }

    @FXML
    void onButtonUpdateAction(ActionEvent event) {
    	openNewModalView("ClientRegisterView.fxml", event);
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
    		
    		ClientRegisterViewController controller = (ClientRegisterViewController)loader.getController();
    		if(event.getSource().equals(buttonRegister)) {
    			controller.setRegister(true);
    			stage.setTitle("Registrar novo cliente");
    		}else if(event.getSource().equals(buttonUpdate)) {
    			controller.setRegister(false);
    			stage.setTitle("Atualizar cliente");
    			
    			//set selectedObject
    			controller.getTextFieldId().setText(String.valueOf(selectedObject.getId()));
    			controller.getTextFieldName().setText(selectedObject.getName());
    			
    			LocalDate localDate = Instant.ofEpochMilli(selectedObject.getBirthDate().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    			controller.getDatePicker().setValue(localDate);
    			
    			controller.getComboBoxGenre().getSelectionModel().clearAndSelect(selectedObject.getGenre().getCod()-1);
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
    	ObservableList<ClientDTO> obsList = FXCollections.observableArrayList(clientService.findAll());
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
		tableColumnBirthDate.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		tableColumnGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
		
		//Initial charge
		refreshTable();
		
		//Selected object
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateSelectedObject(event));
		tableView.addEventFilter(KeyEvent.KEY_RELEASED, event -> updateSelectedObject(event));
	
		//button initial config
		buttonRemove.setDisable(true);
		buttonUpdate.setDisable(true);
		
		//search
		textFieldSearch.textProperty().addListener((obs, oldVal, newVal) -> {
			List<ClientDTO> oldList;
			if(tableView.getItems().isEmpty() || newVal.length() < oldVal.length()) {
				oldList = clientService.findAll();
			}else {
				oldList = tableView.getItems().stream().collect(Collectors.toList());
			}
			List<ClientDTO> newList = oldList.stream().filter(x -> x.getName().toUpperCase().contains(newVal.toUpperCase())).collect(Collectors.toList());
			tableView.setItems(FXCollections.observableArrayList(newList));
		});
    }
    
    private void updateSelectedObject(Event event) {
		try {
			selectedObject = tableView.getSelectionModel().getSelectedItem();
			textFieldSelectedObject.setText(selectedObject.getName());
			buttonRemove.setDisable(false);
			buttonUpdate.setDisable(false);
		}catch(NullPointerException e) {
			textFieldSelectedObject.clear();
		}
	}

}
