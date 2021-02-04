package com.igorjmv2000.gmail.aulajpa.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

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

    }

    @FXML
    void onMenuItemCloseAction(ActionEvent event) {
    	System.exit(0);
    }

}
