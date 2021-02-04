package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class MainViewController {

    @FXML private MenuItem menuItemClose;
    @FXML private MenuItem menuItemAbout;

    @FXML private AnchorPane mainPane;

    @FXML private Button buttonOrders;
    @FXML private Button buttonRegister;

    @FXML
    void onButtonOrdersAction(ActionEvent event) {

    }

    @FXML
    void onButtonRegisterAction(ActionEvent event) {

    }

    @FXML
    void onMenuItemAboutAction(ActionEvent event) {
    	try {
    		//Identificando e removendo o painel anterior
    		BorderPane root = ((BorderPane)mainPane.getParent());
    		Node node = root.getChildren().get(1);
    		root.getChildren().remove(1);
    	
    		//Abrindo o novo painel
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("AboutView.fxml"));
    		Parent aboutView = loader.load();
    		
    		((AboutViewController)loader.getController()).setLeadingUpNode(node);
    		
    		root.setCenter(aboutView);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}catch(NullPointerException e) {
    		System.err.println("Error: " + e.getMessage());
    	}
    }

    @FXML
    void onMenuItemCloseAction(ActionEvent event) {
    	System.exit(0);
    }

}
