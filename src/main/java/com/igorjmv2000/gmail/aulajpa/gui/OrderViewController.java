package com.igorjmv2000.gmail.aulajpa.gui;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderItemDTO;
import com.igorjmv2000.gmail.aulajpa.domain.enums.OrderStatus;
import com.igorjmv2000.gmail.aulajpa.gui.util.OrderItemDetails;
import com.igorjmv2000.gmail.aulajpa.services.OrderItemService;
import com.igorjmv2000.gmail.aulajpa.services.OrderService;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OrderViewController implements Initializable{
	private Node leadingUpNode;
	
	private OrderService orderService = ConfigTest.getStaticOrderService();
	
	private OrderItemService orderItemService = ConfigTest.getStaticOrderItemService();
	
	private OrderDTO selectedObject;
	
    @FXML private RadioButton radioButtonId;
    @FXML private RadioButton radioButtonClient;
    @FXML private ToggleGroup radioGroup;

    @FXML private TextField textFieldSearch;
    @FXML private TextField textFieldSelectedObject;

    @FXML private CheckBox checkBoxTimeFilter;
    @FXML private CheckBox checkBoxStatusFilter;

    @FXML private DatePicker datePickerInitial;
    @FXML private DatePicker datePickerFinal;

    @FXML private ComboBox<OrderStatus> comboBoxStatus;

    @FXML private Button buttonBack;
    @FXML private Button buttonRegister;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonRemove;

    @FXML private TableView<OrderDTO> tableView;
    @FXML private TableColumn<OrderDTO, Integer> tableColumnId;
    @FXML private TableColumn<OrderDTO, Date> tableColumnMoment;
    @FXML private TableColumn<OrderDTO, OrderStatus> tableColumnStatus;
    @FXML private TableColumn<OrderDTO, ClientDTO> tableColumnClient;
    
    private ChangeListener<String> changeListener = (obs, oldValue, newValue) -> {
		if (newValue != null && !newValue.matches("\\d*")) {
			textFieldSearch.setText(oldValue);
		}
	};

    @FXML
    void onButtonBackAction(ActionEvent event) {
    	BorderPane root = ((BorderPane)buttonBack.getParent().getParent().getParent());
    	root.getChildren().remove(1);
    	root.setCenter(leadingUpNode);
    }
    
    @FXML
    void onButtonRegisterAction(ActionEvent event) {
    	openNewModalView("OrderRegisterView.fxml", event);
    }

    @FXML
    void onButtonRemoveAction(ActionEvent event) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirme para prosseguir");
    	alert.setHeaderText("Você está prestes a deletar o pedido abaixo. Quer continuar?");
    	alert.setContentText("Id: " + selectedObject.getId() + "\n"
    						+ "Feito por: " + selectedObject.getClient().getName() + "\n"
    						+ "Status: " + selectedObject.getStatus().getDescription());
    	ButtonType type = alert.showAndWait().get();
    	
    	if(type.equals(ButtonType.OK)) {
    		for(OrderItemDTO dto : selectedObject.getItems()) {
    			orderItemService.delete(dto);
    		}
    		orderService.deleteById(selectedObject.getId());
    		refreshTable();
    	}
    }

    @FXML
    void onButtonUpdateAction(ActionEvent event) {
    	openNewModalView("OrderRegisterView.fxml", event);
    }
    
    @FXML
    void onRadioButtonsCommit(ActionEvent event) {
    	textFieldSearch.clear();
    	if(!radioButtonClient.isSelected()) {
    		textFieldSearch.textProperty().addListener(changeListener);
    		textFieldSearch.setPromptText("Buscar por id");
    	} else {
    		textFieldSearch.textProperty().removeListener(changeListener);
    		textFieldSearch.setPromptText("Buscar por cliente");
    	}
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
    		
    		OrderRegisterViewController controller = (OrderRegisterViewController)loader.getController();
    		if(event.getSource().equals(buttonRegister)) {
    			controller.setRegister(true);
    			stage.setTitle("Registrar novo pedido");
    		}else if(event.getSource().equals(buttonUpdate)) {
    			controller.setRegister(false);
    			stage.setTitle("Atualizar pedido");
    			
    			//set selectedObject
    			controller.getTextFieldId().setText(String.valueOf(selectedObject.getId()));
    			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    			controller.getTextFieldMoment().setText(df.format(selectedObject.getMoment()));
    			controller.getComboBoxStatus().getSelectionModel().clearAndSelect(selectedObject.getStatus().getCod()-1);
    			controller.setClient(selectedObject.getClient());
    			controller.getTextFieldClient().setText(selectedObject.getClient().getName());
    			ObservableList<OrderItemDTO> obsList = FXCollections.observableArrayList(selectedObject.getItems());
    			controller.getListViewProduct().getItems().addAll(obsList);
    			controller.updateTotalPrice();
    			
    		}
    		controller.updateContextMenuProduct();    		
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
    	ObservableList<OrderDTO> obsList = FXCollections.observableArrayList(orderService.findAll());
		tableView.setItems(obsList);
		tableView.refresh();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = "file:///" + System.getProperty("user.dir") + "\\Icon";
		
		Image img = new Image(path + "\\Back.png");
		ImageView imgView = new ImageView(img);
		buttonBack.setGraphic(imgView);
		
		//tableView config
		tableView.setOnMouseClicked(event -> {
			if(event.getButton().equals(MouseButton.PRIMARY)) {
				if(event.getClickCount() == 2) {
					openOrderDetails(event);
				}
			}
		});
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnMoment.setCellValueFactory(new PropertyValueFactory<>("moment"));
		tableColumnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		tableColumnClient.setCellValueFactory(new PropertyValueFactory<>("client"));
		
		//initial charge
		refreshTable();
		
		//Selected object
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateSelectedObject(event));
		tableView.addEventFilter(KeyEvent.KEY_RELEASED, event -> updateSelectedObject(event));
		
		//search
		textFieldSearch.textProperty().addListener((obs, oldVal, newVal) -> {
			List<OrderDTO> oldList;
			if(tableView.getItems().isEmpty() || newVal.length() < oldVal.length()) {
				oldList = orderService.findAll();
			}else {
				oldList = tableView.getItems().stream().collect(Collectors.toList());
			}
			List<OrderDTO> newList = null;
			if(radioButtonClient.isSelected()) {
				newList = oldList.stream().filter(x -> x.getClient().getName().toUpperCase().contains(newVal.toUpperCase())).collect(Collectors.toList());
			} else {
				try {
					newList = oldList.stream().filter(x -> x.getId().equals(Integer.valueOf(newVal))).collect(Collectors.toList());
				}catch(NumberFormatException e) {
					newList = orderService.findAll();
				}
			}
			tableView.setItems(FXCollections.observableArrayList(newList));
		});
		
		//filters (time)
		checkBoxTimeFilter.selectedProperty().addListener((obs, oldVal, newVal) -> {
			if(newVal && !checkBoxStatusFilter.isSelected()) {
				datePickerInitial.setDisable(false);
				datePickerFinal.setDisable(false);
				filterByTime();
			} else if(newVal && checkBoxStatusFilter.isSelected()) {
				datePickerInitial.setDisable(false);
				datePickerFinal.setDisable(false);
				filterByTimeAndStatus();
			} else if (!newVal && checkBoxStatusFilter.isSelected()) {
				datePickerInitial.setDisable(true);
				datePickerFinal.setDisable(true);
				filterByStatus();
			} else {
				refreshTable();
				datePickerInitial.setDisable(true);
				datePickerFinal.setDisable(true);
			}
		});
		datePickerInitial.valueProperty().addListener((obs, oldVal, newVal) -> {
			filterByTime();
		});
		datePickerFinal.valueProperty().addListener((obs, oldVal, newVal) -> {
			filterByTime();
		});
		
		//filters (status)
		ObservableList<OrderStatus> obsComboList = FXCollections.observableArrayList(OrderStatus.values());
		comboBoxStatus.setItems(obsComboList);
		comboBoxStatus.getSelectionModel().clearAndSelect(0);
		comboBoxStatus.valueProperty().addListener((obs, oldVal, newVal) -> {
			filterByStatus();
		});
		checkBoxStatusFilter.selectedProperty().addListener((obs, oldVal, newVal) -> {
			if(newVal && !checkBoxTimeFilter.isSelected()) {
				filterByStatus();
				comboBoxStatus.setDisable(false);
			} else if (newVal && checkBoxTimeFilter.isSelected()) {
				filterByTimeAndStatus();
				comboBoxStatus.setDisable(false);
			} else if (!newVal && checkBoxTimeFilter.isSelected()) {
				comboBoxStatus.setDisable(true);
				filterByTime();
			} else {
				refreshTable();
				comboBoxStatus.setDisable(true);
			}
		});
	}
	
	private void openOrderDetails(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDetailsView.fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root);
			
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.initOwner(buttonRegister.getScene().getWindow());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Detalhes do pedido");
			
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat df2 = new SimpleDateFormat("dd/MM/yyyy");
			OrderDetailsViewController controller = loader.getController();
			controller.getTextFieldId().setText(String.valueOf(selectedObject.getId()));
			controller.getTextFieldMoment().setText(df.format(selectedObject.getMoment()));
			controller.getTextFieldStatus().setText(selectedObject.getStatus().getDescription());
			
			//table view client
			ClientDTO client = selectedObject.getClient();
			controller.getTextFieldClientId().setText(String.valueOf(client.getId()));
			controller.getTextFieldClientName().setText(client.getName());
			controller.getTextFieldClientBirthDate().setText(df2.format(client.getBirthDate()));
			controller.getTextFieldClientGenre().setText(client.getGenre().getDescription());
			
			//table View orderItem
			ObservableList<OrderItemDetails> obsListOrderItem = FXCollections.observableArrayList(selectedObject.getItems().stream().map(
					oi -> new OrderItemDetails(oi.getProduct().getId(), oi.getProduct().getName(), oi.getPrice(), oi.getQuantity(), oi.price())
			).collect(Collectors.toList()));
			controller.getTableViewOrderItem().setItems(obsListOrderItem);
			
			//total value
			controller.getLabelTotalPrice().setText(String.format("R$%.2f", selectedObject.totalPrice()));
			
			stage.showAndWait();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void filterByTimeAndStatus() {
		try {
			Date initialDate = Date.from(datePickerInitial.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date finalDate = Date.from(datePickerFinal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			validateDates(initialDate, finalDate);
			
			List<OrderDTO> newList = orderService.findAll().stream().filter(o -> 
				o.getMoment().getTime() >= initialDate.getTime() && o.getMoment().getTime() <= finalDate.getTime()
				&&
				o.getStatus().equals(comboBoxStatus.getValue())
			).collect(Collectors.toList());
			tableView.setItems(FXCollections.observableArrayList(newList));
		}catch(NullPointerException e) {
			
		}catch(IllegalArgumentException e) {
			LocalDate aux = datePickerInitial.getValue();
			datePickerInitial.setValue(datePickerFinal.getValue());
			datePickerFinal.setValue(aux);
		}
	}

	private void filterByStatus() {
		List<OrderDTO> newList = orderService.findAll().stream().filter(o -> o.getStatus().equals(comboBoxStatus.getValue())).collect(Collectors.toList());
		tableView.setItems(FXCollections.observableArrayList(newList));
	}

	private void filterByTime() {
		try {
			Date initialDate = Date.from(datePickerInitial.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Date finalDate = Date.from(datePickerFinal.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
			
			validateDates(initialDate, finalDate);
			
			List<OrderDTO> newList = orderService.findAll().stream().filter(o -> o.getMoment().getTime() >= initialDate.getTime() && o.getMoment().getTime() <= finalDate.getTime()).collect(Collectors.toList());
			tableView.setItems(FXCollections.observableArrayList(newList));
		}catch(NullPointerException e) {
			
		}catch(IllegalArgumentException e) {
			LocalDate aux = datePickerInitial.getValue();
			datePickerInitial.setValue(datePickerFinal.getValue());
			datePickerFinal.setValue(aux);
		}
	}

	private void validateDates(Date initialDate, Date finalDate) {
		if((finalDate.getTime() - initialDate.getTime()) < 0)
			throw new IllegalArgumentException("End date must be greater than the start date");
	}

	private void updateSelectedObject(Event event) {
		try {
			selectedObject = tableView.getSelectionModel().getSelectedItem();
			textFieldSelectedObject.setText("Id: " + String.valueOf(selectedObject.getId()));
			buttonRemove.setDisable(false);
			buttonUpdate.setDisable(false);
		}catch(NullPointerException e) {
			textFieldSelectedObject.clear();
		}
	}

}
