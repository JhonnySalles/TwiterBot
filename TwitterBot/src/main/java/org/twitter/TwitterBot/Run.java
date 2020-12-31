package org.twitter.TwitterBot;

import org.twitter.TwitterBot.controller.DashboardController;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class Run extends Application {

	private static Scene MAIN_SCENE;
	private static DashboardController MAIN_CONTROLLER;
	private static Stage PRIMARY_STAGE;

	public void start(Stage primaryStage) {
		try {
			PRIMARY_STAGE = primaryStage;
			// Classe inicial
			FXMLLoader loader = new FXMLLoader(DashboardController.getFxmlLocate());
			AnchorPane scPnTelaPrincipal = loader.load();
			MAIN_CONTROLLER = loader.getController();

			MAIN_SCENE = new Scene(scPnTelaPrincipal); // Carrega a scena
			MAIN_SCENE.setFill(Color.BLACK);

			primaryStage.setScene(MAIN_SCENE); // Seta a cena principal
			primaryStage.setTitle("Twitter bot");
			primaryStage.getIcons()
					.add(new Image(getClass().getResourceAsStream(DashboardController.getIconLocate())));
			primaryStage.initStyle(StageStyle.DECORATED);
			// primaryStage.setMaximized(true);
			primaryStage.setMinWidth(630);
			primaryStage.setMinHeight(490);

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent arg0) {
					System.exit(0);
				}
			});

			primaryStage.show(); // Mostra a tela.

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static DashboardController getMainController() {
		return MAIN_CONTROLLER;
	}

	public static Scene getMainScene() {
		return MAIN_SCENE;
	}
	
	public static Stage getPrimaryStage() {
		return PRIMARY_STAGE;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
