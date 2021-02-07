package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.domain.enums.OrderStatus;
import com.igorjmv2000.gmail.aulajpa.services.OrderService;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class OrderViewController implements Initializable{
	private Node leadingUpNode;
	
	private OrderService orderService = ConfigTest.getStaticOrderService();
	
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

    }

    @FXML
    void onButtonRemoveAction(ActionEvent event) {

    }

    @FXML
    void onButtonUpdateAction(ActionEvent event) {

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
