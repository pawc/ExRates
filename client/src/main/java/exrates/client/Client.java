package exrates.client;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Client extends Application {

	@Override
	public void start(Stage primaryStage) throws IOException {
	    
	    BorderPane borderPane = FXMLLoader.load(Client.class.getResource("MainView.fxml"));
	    primaryStage.setTitle("ExRates PLN - exchange rates of polish currency");
	    Scene scene = new Scene(borderPane);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
