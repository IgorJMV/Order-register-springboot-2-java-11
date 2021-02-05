package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.enums.Genre;
import com.igorjmv2000.gmail.aulajpa.services.ClientService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    }

    @FXML
    void onButtonRemoveAction(ActionEvent event) {

    }

    @FXML
    void onButtonUpdateAction(ActionEvent event) {

    }
    
    public void setLeadingUpNode(Node node) {
    	leadingUpNode = node;
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
		ObservableList<ClientDTO> obsList = FXCollections.observableArrayList(clientService.findAll());
		tableView.setItems(obsList);
		
		//Selected object
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateSelectedObject(event));
		tableView.addEventFilter(KeyEvent.KEY_RELEASED, event -> updateSelectedObject(event));
	}
    
    private void updateSelectedObject(Event event) {
		try {
			selectedObject = tableView.getSelectionModel().getSelectedItem();
			textFieldSelectedObject.setText(selectedObject.getName());
		}catch(NullPointerException e) {
			textFieldSelectedObject.clear();
		}
	}

}
