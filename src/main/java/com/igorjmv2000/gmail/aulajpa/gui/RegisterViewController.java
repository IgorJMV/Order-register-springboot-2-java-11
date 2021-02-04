package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class RegisterViewController implements Initializable{
	private Node leadingUpNode;
	
    @FXML private Button buttonBack;
    @FXML private Button buttonClient;
    @FXML private Button buttonProduct;
    @FXML private Button buttonCategory;

    @FXML
    void onButtonBackAction(ActionEvent event) {
    	BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    	root.getChildren().remove(1);
    	root.setCenter(leadingUpNode);
    }

    @FXML
    void onButtonCategoryAction(ActionEvent event) {

    }

    @FXML
    void onButtonClientAction(ActionEvent event) {

    }

    @FXML
    void onButtonProductAction(ActionEvent event) {

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