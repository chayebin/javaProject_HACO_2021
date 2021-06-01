package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ConControlMain implements Initializable {
	private final Logger LOG = Logger.getLogger(ConControlMain.class);

	@FXML private Button moveToRestList; //레스토랑 리스트 보러가기
	@FXML private Button moveToMyReseList; //예약확인
	@FXML private Button moveToStart; //로그아웃 후 시작페이지로 이동
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		moveToRestList.setOnAction(event -> handlerMoveToRestList(event));
		moveToMyReseList.setOnAction(event -> handlerMoveToMyReseList(event));
		moveToStart.setOnAction(event -> handlerMoveToStart(event));
	}

	// =======================================================
	// 레스토랑 리스트 보러가기 (화면이동)
	public void handlerMoveToRestList(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/CONSUMER/RestaurantListFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
		
		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("===================");
			LOG.debug("=IOException=" + e.getMessage());
			LOG.debug("===================");
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) moveToRestList.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("Restaurant List");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// =======================================================
	// 예약내역 확인하기 (화면이동)
	public void handlerMoveToMyReseList(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/CONSUMER/ReseCheckConFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("===================");
			LOG.debug("=IOException=" + e.getMessage());
			LOG.debug("===================");
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) moveToRestList.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("Reservation List");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// =======================================================
	// 로그아웃 -> 시작화면으로! (화면이동)
	public void handlerMoveToStart(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/START_ADMIN/StartFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("===================");
			LOG.debug("=IOException=" + e.getMessage());
			LOG.debug("===================");
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) moveToStart.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("StartMain");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// =======================================================
	// 종료버튼 누를시 onAction으로 종료
	public void handlerExitAction(ActionEvent event) {
		Platform.exit();
	}
	
}
