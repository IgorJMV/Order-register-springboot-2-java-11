package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    	openNewView("CategoryView.fxml", event);
    }

    @FXML
    void onButtonClientAction(ActionEvent event) {
    	openNewView("ClientView.fxml", event);
    }

    @FXML
    void onButtonProductAction(ActionEvent event) {
    	openNewView("ProductView.fxml", event);
    }
    
    public void setLeadingUpNode(Node node) {
    	leadingUpNode = node;
    }

    private void openNewView(String name, ActionEvent event) {
    	try {
    		//Identificando e removendo o painel anterior
    		BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    		Node node = root.getChildren().get(1);
    		root.getChildren().remove(1);
    	
    		//Abrindo o novo painel
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
    		Parent view = loader.load();
    		
    		if(event.getSource().equals(buttonCategory))
    			((CategoryViewController)loader.getController()).setLeadingUpNode(node);
    		if(event.getSource().equals(buttonProduct))
    			((ProductViewController)loader.getController()).setLeadingUpNode(node);
    		if(event.getSource().equals(buttonClient))
    			((ClientViewController)loader.getController()).setLeadingUpNode(node);
    		
    		root.setCenter(view);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}catch(NullPointerException e) {
    		System.err.println("Error: " + e.getMessage());
    	}
    }
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = "file:///" + System.getProperty("user.dir") + "\\Icon";
		
		Image img = new Image(path + "\\Back.png");
		ImageView imgView = new ImageView(img);
		buttonBack.setGraphic(imgView);
	}

}
