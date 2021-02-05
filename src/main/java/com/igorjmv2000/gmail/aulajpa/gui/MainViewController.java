package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;

public class MainViewController {

    @FXML private MenuItem menuItemClose;
    @FXML private MenuItem menuItemAbout;

    @FXML private MenuBar menuBar;

    @FXML private Button buttonOrders;
    @FXML private Button buttonRegister;

    @FXML
    void onButtonOrdersAction(ActionEvent event) {
    	openNewView("OrderView.fxml", event);
    }

    @FXML
    void onButtonRegisterAction(ActionEvent event) {
    	openNewView("RegisterView.fxml", event);
    }

    @FXML
    void onMenuItemAboutAction(ActionEvent event) {
    	openNewView("AboutView.fxml", event);
    }

    @FXML
    void onMenuItemCloseAction(ActionEvent event) {
    	System.exit(0);
    }
    
    private void openNewView(String name, ActionEvent event) {
    	try {
    		//Identificando e removendo o painel anterior
    		BorderPane root = ((BorderPane)menuBar.getParent());
    		Node node = root.getChildren().get(1);
    		root.getChildren().remove(1);
    	
    		//Abrindo o novo painel
    		FXMLLoader loader = new FXMLLoader(getClass().getResource(name));
    		Parent aboutView = loader.load();
    		
    		if(event.getSource().equals(menuItemAbout)) {
    			AboutViewController controller = ((AboutViewController)loader.getController());
    			controller.setLeadingUpNode(node);
    			controller.setMenuItemAbout(menuItemAbout);
    			menuItemAbout.setDisable(true);
    		}
    		if(event.getSource().equals(buttonRegister))
    			((RegisterViewController)loader.getController()).setLeadingUpNode(node);
    		if(event.getSource().equals(buttonOrders))
    			((OrderViewController)loader.getController()).setLeadingUpNode(node);
    		
    		root.setCenter(aboutView);
    	}catch(IOException e) {
    		e.printStackTrace();
    	}catch(NullPointerException e) {
    		System.err.println("Error: " + e.getMessage());
    	}
    }

}
