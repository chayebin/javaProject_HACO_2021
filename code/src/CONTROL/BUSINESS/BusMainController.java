package CONTROL.BUSINESS;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BusMainController implements Initializable {

	private final Logger LOG = Logger.getLogger(BusMainController.class);

	@FXML
	Button busCheckRese; // 예약 내역 확인 버튼
	@FXML
	Button busCheckMenu; // 메뉴 정보 확인 버튼
	@FXML
	Button busLogout; // 로그아웃 버튼

	// 경고 메시지
	private Alert alert = new Alert(AlertType.WARNING);

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
		busCheckRese.setOnAction(event -> handleBtnBusCheckRese(event)); // 예약 내역 확인 버튼 클릭
		busCheckMenu.setOnAction(event -> handleBtnBusCheckMenu(event)); // 메뉴 정보 확인 버튼 클릭
		busLogout.setOnAction(event -> handleBtnBusLogout(event)); // 로그아웃 버튼 클릭
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------

	} // --- initialize

	// Event 처리 -----------------------------------------------------------------------------------------------------------------------

	// 예약 리스트로 이동
	public void handleBtnBusCheckRese(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/BUSINESS/BusReseListFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) busLogout.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessRese");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
	} // --- handleBtnBusCheckRese

	// 메뉴 리스트로 이동
	public void handleBtnBusCheckMenu(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/BUSINESS/BusMenuListFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) busLogout.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessMenuList");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
	} // --- handleBtnBusCheckMenu

	// 로그아웃 -> 시작 화면으로 이동
	public void handleBtnBusLogout(ActionEvent event) {
		// 화면 Load
		// Confirm
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonTypeOk = new ButtonType("네");
		ButtonType buttonTypeNo = new ButtonType("아니오");
		confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
		confirm.setTitle("로그아웃");
		confirm.setContentText("로그아웃 하시겠습니까?");
		confirm.show();

		// Confirm : "네" 버튼 눌렀을 때 and "아니오" 버튼 눌렀을 때
		confirm.setOnCloseRequest(ev -> {
			// "네" 버튼 누르면
			if (confirm.getResult() == buttonTypeOk) {
				confirm.close(); // confirm창 close
				URL url = getClass().getResource("/VIEW/START_ADMIN/StartFX.fxml");
				FXMLLoader load = new FXMLLoader(url);
				Parent parent = null;

				try {
					parent = load.load();
				} catch (IOException e) {
					LOG.debug("= IOException = " + e.getMessage());
					e.printStackTrace();
				}

				Stage primaryStage = (Stage) busLogout.getScene().getWindow();

				// 기존에 떠있던 화면 숨김 : view hide
				primaryStage.close();

				Scene scene = new Scene(parent);

				// 윈도우 제목
				primaryStage.setTitle("BusinessMenuList");
				// 윈도우 화면에 Scene 추가
				primaryStage.setScene(scene);
				// 윈도우 화면에 보이게
				primaryStage.show();

				// "아니오" 버튼 누르면
			} else if (confirm.getResult() == buttonTypeNo) {
				confirm.close();
			}
		});

	} // --- handleBtnBusLogout

	// 종료 버튼 누를 시 프로그램 종료
	public void handlerExitAction(ActionEvent event) {
		// Confirm
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonTypeOk = new ButtonType("네");
		ButtonType buttonTypeNo = new ButtonType("아니오");
		confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);
		confirm.setTitle("프로그램 종료");
		confirm.setContentText("종료하시겠습니까?");
		confirm.show();

		// Confirm : "네" 버튼 눌렀을 때 and "아니오" 버튼 눌렀을 때
		confirm.setOnCloseRequest(ev -> {
			// "네" 버튼 누르면
			if (confirm.getResult() == buttonTypeOk) {
				confirm.close(); // confirm창 close
				Platform.exit();
				
			// "아니오" 버튼 누르면
			} else if (confirm.getResult() == buttonTypeNo) {
				confirm.close();
			}
		});
	} // --- handlerExitAction

	// Event 처리 -----------------------------------------------------------------------------------------------------------------------

} // --- Class
