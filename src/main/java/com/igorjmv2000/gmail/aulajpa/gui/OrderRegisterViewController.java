package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ClientDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.OrderItemDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.domain.enums.OrderStatus;
import com.igorjmv2000.gmail.aulajpa.gui.exceptions.IncompleteFormException;
import com.igorjmv2000.gmail.aulajpa.services.ClientService;
import com.igorjmv2000.gmail.aulajpa.services.OrderItemService;
import com.igorjmv2000.gmail.aulajpa.services.OrderService;
import com.igorjmv2000.gmail.aulajpa.services.ProductService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OrderRegisterViewController implements Initializable{
	private boolean isRegister;
	
	private ClientService clientService = ConfigTest.getStaticClientService();
	
	private ProductService productService = ConfigTest.getStaticProductService();
	
	private OrderService orderService = ConfigTest.getStaticOrderService();
	
	private OrderItemService orderItemService = ConfigTest.getStaticOrderItemService();
	
	private ClientDTO client;
	
	private ProductDTO product;

    @FXML private TextField textFieldId;
    @FXML private TextField textFieldMoment;
    @FXML private TextField textFieldClient;
    @FXML private TextField textFieldSearchProduct;
    @FXML private TextField textFieldSelectedProduct;
    @FXML private TextField textFieldQuantityProduct;

    @FXML private ComboBox<OrderStatus> comboBoxStatus;

    @FXML private Button buttonAddProduct;
    @FXML private Button buttonRemoveProduct;
    @FXML private Button buttonMoreProduct;
    @FXML private Button buttonDecreaseProduct;
    @FXML private Button buttonCancel;
    @FXML private Button buttonOk;

    @FXML private ListView<OrderItemDTO> listViewProduct;
    
    @FXML private Label labelTotalPrice;
    @FXML private Label labelNameError;
    
    private ContextMenu contextMenuClient;
    
    private ContextMenu contextMenuProduct;
    private List<MenuItem> contextMenuProducts;
    private OrderItemDTO listViewSelectedObject;

    @FXML
    void onButtonAddProductAction(ActionEvent event) {
    	try {
    		OrderItemDTO dto = new OrderItemDTO(null, product, 1, product.getPrice());
    		listViewProduct.getItems().add(dto);
    		updateContextMenuProduct();
    		textFieldSearchProduct.clear();
    		product = null;
    		updateTotalPrice();
    	}catch(NullPointerException e) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Aviso");
    		alert.setHeaderText(null);
    		alert.setContentText("Não há produto para adicionar!");
    		alert.showAndWait();
    	}
    }

    @FXML
    void onButtonCancelAction(ActionEvent event) {
    	((Stage)buttonCancel.getScene().getWindow()).close();
    }

    @FXML
    void onButtonDecreaseProductAction(ActionEvent event) {
    	Integer quantity = listViewSelectedObject.getQuantity();
    	if(quantity.intValue() == 2)
    		buttonDecreaseProduct.setDisable(true);
    	
    	quantity--;
    	
    	listViewSelectedObject.setQuantity(quantity);
    	textFieldQuantityProduct.setText(String.valueOf(quantity));
    	listViewProduct.refresh();
    	updateTotalPrice();
    }

    @FXML
    void onButtonMoreProductAction(ActionEvent event) {
    	Integer quantity = listViewSelectedObject.getQuantity();
    	if(quantity.intValue() == 1)
    		buttonDecreaseProduct.setDisable(false);
    	
    	quantity++;
    	listViewSelectedObject.setQuantity(quantity);
    	textFieldQuantityProduct.setText(String.valueOf(quantity));
    	listViewProduct.refresh();
    	updateTotalPrice();
    }

    @FXML
    void onButtonOkAction(ActionEvent event) {
    	try {
    		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		Date moment = df.parse(textFieldMoment.getText());
    		OrderStatus status = comboBoxStatus.getSelectionModel().getSelectedItem();
    		validate();
    		OrderDTO dto = new OrderDTO(null, moment, status, client);
    		
    		if(isRegister)
    			dto = orderService.insert(dto);
    		else
    			dto = orderService.update(Integer.valueOf(textFieldId.getText()), dto);
    		
    		List<OrderItemDTO> ois = listViewProduct.getItems().stream().collect(Collectors.toList());
    		for(int i = 0; i < ois.size(); i++) {
    			ois.get(i).setOrder(dto);
    		}
    		
    		
    		for(OrderItemDTO o : ois) {
    			orderItemService.insert(o);
    		}
    		
    		((Stage)buttonOk.getScene().getWindow()).close();
    	}catch(ParseException e) {
    		e.printStackTrace();
    	}catch(IncompleteFormException e) {
    		if(e.getControl()) {
    			labelNameError.setText(e.getMessage());
    		} else {
    			Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Erro");
    			alert.setHeaderText(null);
    			alert.setContentText(e.getMessage());
    			alert.showAndWait();
    		}
    	}
    }

    @FXML
    void onButtonRemoveProductAction(ActionEvent event) {
    	listViewProduct.getItems().removeIf(o -> o.getProduct().getId().equals(listViewSelectedObject.getProduct().getId()));
    	if(!isRegister) {
    		try {
    			OrderItemDTO dto = orderItemService.find(listViewSelectedObject.getProduct(), listViewSelectedObject.getOrder());
    			orderItemService.delete(dto);
    		}catch(NullPointerException e) {
    			System.err.println("Object not persisted");
    		}
    	}
    	setSelected(false);
    	updateTotalPrice();
    	updateContextMenuProduct();
    }
    
    private void validate() {
    	if(textFieldClient.getText().trim().replaceAll("\\s+", "").isEmpty() || client == null) {
    		throw new IncompleteFormException("Cliente inválido", true);
    	}else if (listViewProduct.getItems().isEmpty()) {
    		throw new IncompleteFormException("É necessário adicionar um produto ao pedido", false);
    	}
    }
    
    protected void updateTotalPrice() {
    	Double sum = 0.0;
    	for(OrderItemDTO dto : listViewProduct.getItems()) {
    		sum += dto.price();
    	}
    	labelTotalPrice.setText(String.format("R$%.2f", sum));
    }
    
    public void setRegister(boolean isRegister) {
    	this.isRegister = isRegister;
    }
    
    protected void updateContextMenuProduct() {
    	contextMenuProducts = new ArrayList<>();
		contextMenuProducts.addAll(
			productService.findAll().stream().filter(p -> !listViewProduct.getItems().stream().map(o -> o.getProduct().getName()).collect(Collectors.toList()).contains(p.getName())).map(c -> new MenuItem(c.getName() + " [R$" + String.format("%.2f", c.getPrice()) + "]" + " (Id: " + c.getId() + ")") {
				@Override
				public Object getUserData() {
					return c;
				};
				
			}).collect(Collectors.toList())
		);
		contextMenuProduct.getItems().clear();
		contextMenuProduct.getItems().addAll(contextMenuProducts);
    }
    
    private void setSelected(boolean isSelected) {
    	if(isSelected) {
    		listViewSelectedObject = listViewProduct.getSelectionModel().getSelectedItem();
			textFieldSelectedProduct.setText(listViewSelectedObject.getProduct().getName());
			if(listViewSelectedObject.getQuantity().intValue() > 1)
				buttonDecreaseProduct.setDisable(false);
			buttonMoreProduct.setDisable(false);
			buttonRemoveProduct.setDisable(false);
			textFieldQuantityProduct.setText(String.valueOf(listViewSelectedObject.getQuantity()));
    	} else {
    		textFieldQuantityProduct.clear();
			buttonRemoveProduct.setDisable(true);
			buttonDecreaseProduct.setDisable(true);
			buttonMoreProduct.setDisable(true);
			textFieldSelectedProduct.clear();
			listViewSelectedObject = null;
    	}
    }

	public TextField getTextFieldId() {
		return textFieldId;
	}

	public TextField getTextFieldMoment() {
		return textFieldMoment;
	}

	public ComboBox<OrderStatus> getComboBoxStatus() {
		return comboBoxStatus;
	}

	public TextField getTextFieldClient() {
		return textFieldClient;
	}

	public void setClient(ClientDTO client) {
		this.client = client;
	}

	public ListView<OrderItemDTO> getListViewProduct() {
		return listViewProduct;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//Moment textField
		Date moment = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		textFieldMoment.setText(df.format(moment));
		
		//OrderStatus && comboBox
		ObservableList<OrderStatus> obsListOrderStatus = FXCollections.observableArrayList(OrderStatus.values());
		comboBoxStatus.setItems(obsListOrderStatus);
		comboBoxStatus.getSelectionModel().clearAndSelect(0);
		
		//config clientSearchOption
		contextMenuClient = new ContextMenu();
		
		List<MenuItem> contextMenuItems = new ArrayList<>();
		contextMenuItems.addAll(
			clientService.findAll().stream().map(c -> new MenuItem(c.getName() + " (Id: " + c.getId() + ")") {
				@Override
				public Object getUserData() {
					return c;
				};
				
			}).collect(Collectors.toList())
		);
		
		contextMenuClient.setOnAction(event -> {
			client = (ClientDTO)((MenuItem)event.getTarget()).getUserData();
			textFieldClient.setText(client.getName());
		});
		contextMenuClient.getItems().addAll(contextMenuItems);
		contextMenuClient.setHeight(500.00);
		
		textFieldClient.setContextMenu(contextMenuClient);
		textFieldClient.textProperty().addListener((obs, oldVal, newVal) -> {
			contextMenuClient.show(textFieldClient.getScene().getWindow());
			updateContextMenuProduct();
			List<MenuItem> list = contextMenuClient.getItems().stream().filter(m -> 
				((ClientDTO)m.getUserData()).getName().toUpperCase().contains(newVal.toUpperCase())
			).collect(Collectors.toList());
			contextMenuClient.getItems().clear();
			contextMenuClient.getItems().addAll(list);
		});
		
		//config productSearchOption
		contextMenuProduct = new ContextMenu();
		
		//updateContextMenuProduct();
		
		contextMenuProduct.setOnAction(event -> {
			product = (ProductDTO)((MenuItem)event.getTarget()).getUserData();
			textFieldSearchProduct.setText(product.getName());
		});
		contextMenuProduct.setHeight(500.00);
		
		textFieldSearchProduct.setContextMenu(contextMenuProduct);
		textFieldSearchProduct.textProperty().addListener((obs, oldVal, newVal) -> {
			String text = newVal.trim().replaceAll("\\s+", "");
			if(!text.isEmpty())
				contextMenuProduct.show(textFieldSearchProduct.getScene().getWindow());
			contextMenuProduct.getItems().clear();
			contextMenuProduct.getItems().addAll(contextMenuProducts);
			List<MenuItem> list = contextMenuProduct.getItems().stream().filter(m -> 
				((ProductDTO)m.getUserData()).getName().toUpperCase().contains(newVal.toUpperCase())
			).collect(Collectors.toList());
			contextMenuProduct.getItems().clear();
			contextMenuProduct.getItems().addAll(list);
			
			if(list.isEmpty())
				product = null;
		});
		
		//ListView selected Object
		listViewProduct.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			try {
				setSelected(true);
			}catch(NullPointerException e) {
				setSelected(false);
			}
		});
	}

}

