package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.igorjmv2000.gmail.aulajpa.gui.util.OrderItemDetails;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class OrderDetailsViewController implements Initializable{

    @FXML private TextField textFieldId;
    @FXML private TextField textFieldMoment;
    @FXML private TextField textFieldStatus;

    @FXML private TextField textFieldClientId;
    @FXML private TextField textFieldClientName;
    @FXML private TextField textFieldClientBirthDate;
    @FXML private TextField textFieldClientGenre;

    @FXML private TableView<OrderItemDetails> tableViewOrderItem;
    @FXML private TableColumn<OrderItemDetails, Integer> tableColumnOrderItemId;
    @FXML private TableColumn<OrderItemDetails, String> tableColumnOrderItemName;
    @FXML private TableColumn<OrderItemDetails, Double> tableColumnOrderItemIndividualPrice;
    @FXML private TableColumn<OrderItemDetails, Integer> tableColumnOrderItemQuantity;
    @FXML private TableColumn<OrderItemDetails, Double> tableColumnOrderItemFinalPrice;

    @FXML private Label labelTotalPrice;

    @FXML private Button buttonClose;

    @FXML
    void onButtonCloseAction(ActionEvent event) {
    	((Stage)buttonClose.getScene().getWindow()).close();
    }

	public TextField getTextFieldId() {
		return textFieldId;
	}

	public TextField getTextFieldMoment() {
		return textFieldMoment;
	}

	public TextField getTextFieldStatus() {
		return textFieldStatus;
	}

	public TableView<OrderItemDetails> getTableViewOrderItem() {
		return tableViewOrderItem;
	}

	public Label getLabelTotalPrice() {
		return labelTotalPrice;
	}

	public TextField getTextFieldClientId() {
		return textFieldClientId;
	}

	public TextField getTextFieldClientName() {
		return textFieldClientName;
	}

	public TextField getTextFieldClientBirthDate() {
		return textFieldClientBirthDate;
	}

	public TextField getTextFieldClientGenre() {
		return textFieldClientGenre;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//config product table view
		tableColumnOrderItemId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnOrderItemName.setCellValueFactory(new PropertyValueFactory<>("name"));
		tableColumnOrderItemIndividualPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		tableColumnOrderItemQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
		tableColumnOrderItemFinalPrice.setCellValueFactory(new PropertyValueFactory<>("finalPrice"));
	}

}
