package com.igorjmv2000.gmail.aulajpa.gui.start;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartGui extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		File file = new File(getClass().getResource("").getPath());
		String path = file.getParent();
		FXMLLoader loader = new FXMLLoader(new URL("file:///" + path + "/MainView.fxml"));
		BorderPane root = loader.load();
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Registro de pedidos");
		primaryStage.show();
	}
	
	public static void initialize(String[] args) {
		launch(args);
	}

}
