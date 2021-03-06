package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

public class AboutViewController implements Initializable{
	private Node leadingUpNode;
	private MenuItem menuItemAbout;

    @FXML private Button buttonBack;

    @FXML
    void onButtonBackAction(ActionEvent event) {
    	menuItemAbout.setDisable(false);
    	
    	BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    	root.getChildren().remove(1);
    	root.setCenter(leadingUpNode);
    }
    
    public void setLeadingUpNode(Node node) {
    	leadingUpNode = node;
    }
    
    public void setMenuItemAbout(MenuItem menuItem) {
    	menuItemAbout = menuItem;
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = "file:///" + System.getProperty("user.dir") + "\\Icon";
		
		Image img = new Image(path + "\\Back.png");
		ImageView imgView = new ImageView(img);
		buttonBack.setGraphic(imgView);
	}

}
