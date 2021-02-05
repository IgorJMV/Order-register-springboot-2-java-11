package com.igorjmv2000.gmail.aulajpa.gui;

import java.net.URL;
import java.util.ResourceBundle;

import com.igorjmv2000.gmail.aulajpa.config.ConfigTest;
import com.igorjmv2000.gmail.aulajpa.domain.dto.CategoryDTO;
import com.igorjmv2000.gmail.aulajpa.domain.dto.ProductDTO;
import com.igorjmv2000.gmail.aulajpa.services.CategoryService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

public class CategoryViewController implements Initializable{
	private Node leadingUpNode;
	private CategoryService categoryService = ConfigTest.getStaticCategoryService();
	private CategoryDTO selectedObject;
	
    @FXML private Button buttonBack;

    @FXML private TextField textFieldSelectedObject;
    @FXML private TextField textFieldSearch;

    @FXML private Button buttonRegister;
    @FXML private Button buttonUpdate;
    @FXML private Button buttonRemove;

    @FXML private TableView<CategoryDTO> tableView;
    @FXML private TableColumn<CategoryDTO, Integer> tableColumnId;
    @FXML private TableColumn<CategoryDTO, String> tableColumnDescription;

    @FXML private ListView<ProductDTO> listView;

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
    
    public void setLeadingUpNode(Node node) {
    	leadingUpNode = node;
    }
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		String path = "file:///" + System.getProperty("user.dir") + "\\Icon";
		
		Image img = new Image(path + "\\Back.png");
		ImageView imgView = new ImageView(img);
		buttonBack.setGraphic(imgView);
		
		//table config
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
		
		//initial charge
		ObservableList<CategoryDTO> obsList = FXCollections.observableArrayList(categoryService.findAll());
		tableView.setItems(obsList);
		
		//Selected object
		tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> updateSelectedObject(event));
		tableView.addEventFilter(KeyEvent.KEY_RELEASED, event -> updateSelectedObject(event));
    }

	private void updateSelectedObject(Event event) {
		try {
			selectedObject = tableView.getSelectionModel().getSelectedItem();
			textFieldSelectedObject.setText(selectedObject.getDescription());
		}catch(NullPointerException e) {
			textFieldSelectedObject.clear();
		}
	}

}
