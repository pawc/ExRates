package pawc.exrates.client;

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
	    
	    BorderPane borderPane = (BorderPane) FXMLLoader.load(ClassLoader.getSystemResource("exrates/client/MainView.fxml"));
	    primaryStage.setTitle("ExRates - exchange rates for PLN, EUR and USD");
	    Scene scene = new Scene(borderPane);
	    primaryStage.setScene(scene);
	    primaryStage.show();
	    
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
