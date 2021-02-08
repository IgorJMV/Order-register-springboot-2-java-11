package com.igorjmv2000.gmail.aulajpa.gui.start;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class StartGui extends Application {
	private static boolean connectionStaticError;

	@Override
	public void start(Stage primaryStage) throws Exception {
		if (!connectionStaticError) {
			File file = new File(getClass().getResource("").getPath());
			String path = file.getParent();
			FXMLLoader loader = new FXMLLoader(new URL("file:///" + path + "/MainView.fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Registro de pedidos");
			primaryStage.show();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("Problema de conexão com o banco de dados");
			alert.setContentText("A conexão com o banco de dados não foi bem sucedida.\n"
					+ "Verifique se o Apache e o MySQL estam rodando no sistema.");
			alert.showAndWait();
		}
	}

	public static void initialize(String[] args, boolean connectionError) {
		connectionStaticError = connectionError;
		launch(args);
	}

}
