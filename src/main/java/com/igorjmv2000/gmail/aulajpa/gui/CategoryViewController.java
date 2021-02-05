package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class CategoryViewController implements Initializable{
	private Node leadingUpNode;

    @FXML private Button buttonBack;

    @FXML private TextField textFieldSelectedObject;
    @FXML private TextField textFieldSearch;

    @FXML private Button buttonRegister;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonRemove;

    @FXML private TableView<?> tableView;
    @FXML private TableColumn<?, ?> tableColumnId;
    @FXML private TableColumn<?, ?> tableColumnDescription;

    @FXML private ListView<?> listView;

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
	}

}
