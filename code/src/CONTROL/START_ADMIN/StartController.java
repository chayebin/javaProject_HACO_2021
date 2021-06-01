package CONTROL.START_ADMIN;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StartController implements Initializable {
	private final Logger LOG = Logger.getLogger(StartController.class);
	
	@FXML private Button moveToConLogin;//소비자 로그인 이동
	@FXML private Button moveToBusLogin;//사업자 로그인 이동


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// start
		moveToConLogin.setOnAction(event->handleBtnMoveToConLogin(event));
		moveToBusLogin.setOnAction(event->handleBtnMoveToBusLogin(event));


	}

	//----------------------------------------------------------------------------------------------------
	// start -> 각각의 login (화면이동)
	public void handleBtnMoveToConLogin(ActionEvent event) {
		URL url=getClass().getResource("/VIEW/CONSUMER/ConLoginFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
	
		try {
			parent=load.load();
		}catch(IOException e) {
			LOG.debug("===================");
			LOG.debug("=IOException="+e.getMessage());
			LOG.debug("===================");
			e.printStackTrace();
		}
		
		Stage primaryStage = (Stage) moveToConLogin.getScene().getWindow();
		primaryStage.close();
		
		Scene scene=new Scene(parent);
		
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public void handleBtnMoveToBusLogin(ActionEvent event) {
		URL url=getClass().getResource("/VIEW/BUSINESS/BusLoginFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
	
		try {
			parent=load.load();
		}catch(IOException e) {
			LOG.debug("===================");
			LOG.debug("=IOException="+e.getMessage());
			LOG.debug("===================");
			e.printStackTrace();
		}
		
		Stage primaryStage = (Stage) moveToBusLogin.getScene().getWindow();
		primaryStage.close();
		
		Scene scene=new Scene(parent);
		
		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	
	public void handlerExitAction(ActionEvent event) {
		Platform.exit();
	}
}
