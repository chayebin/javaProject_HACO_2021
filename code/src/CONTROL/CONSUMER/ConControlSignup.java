package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.ConsumerDao;
import VO.ConsumerVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConControlSignup implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(ConControlSignup.class);
	private ConsumerDao dao = new ConsumerDao();
	
	@FXML private TextField inputId; // 아이디 입력칸
	@FXML private PasswordField inputPw; // 비밀번호 입력칸
	@FXML private TextField inputName; // 이름 입력칸
	@FXML private TextField inputPhone; // 010-1234-5678
	
	@FXML private Button idCheck; //아이디 중복 체크
	@FXML private Button doSignup; // 회원가입 완료 버튼
	@FXML private Button moveToLogin; // 뒤로가기 버튼

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		idCheck.setOnAction(event -> handleBtnButtonIdCheck(event));
		doSignup.setOnAction(event -> handlerDoSignup(event));
		moveToLogin.setOnAction(event -> handlerMoveToLogin(event));
	}	
	
	// =======================================================
	// id 중복 체크하기
	public void handleBtnButtonIdCheck(ActionEvent evnet) {
		ConsumerVO inVO = new ConsumerVO();

		String inputConid = inputId.getText();

		// ------------------------------------------
		// 데이터 미입력시 경고메세지
		if (Objects.equals(inputConid, "") || inputConid.length() == 0) {
			alert.setTitle("ID Error");
			alert.setContentText("아이디를 입력하세요");
			alert.show();
			return;
		}

		// ------------------------------------------
		// ID 중복체크
		int flag = 0;
		inVO.setConId(inputConid);
		flag = dao.checkConsumerId(inVO);

		// ------------------------------------------
		// ID 중복 : flag = 1, ID 중복 x : flag = 0
		if (flag == 1) {
			alert.setTitle("ID Check Error");
			alert.setContentText("이미 등록된 ID 입니다");
			alert.show();
			inputId.setText("");
			return;
		} else {
			Alert confirm = new Alert(AlertType.CONFIRMATION);
			ButtonType buttonTypeOk = new ButtonType("네");
			ButtonType buttonTypeNo = new ButtonType("아니오");
			confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

			confirm.setTitle("ID 중복 확인");
			confirm.setContentText("등록되지 않은 ID 입니다. 사용하시겠습니까?");
			confirm.show();

			// Confirm : "네" 버튼 눌렀을 때 and "아니오" 버튼 눌렀을 때
			confirm.setOnCloseRequest(ev -> {
				// "네" 버튼 누르면
				if (confirm.getResult() == buttonTypeOk) {
					confirm.close(); // confirm창 close

					// "아니오" 버튼 누르면
				} else if (confirm.getResult() == buttonTypeNo) {
					confirm.close();
					inputId.setText("");
				}
			});

		}

	}
	
	
	// =======================================================
	// signup 버튼 클릭 (화면이동 및 정보 저장)
	public void handlerDoSignup(ActionEvent event) {
		ConsumerVO inVO = new ConsumerVO();

	      // TextField 데이터 -> inVO
	      inVO.setConId(inputId.getText());
	      inVO.setConPw(inputPw.getText());
	      inVO.setConName(inputName.getText());
	      inVO.setConPhone(inputPhone.getText());
	      // 등록일 : 현재시간
	      inVO.setConDate(EStringUtil.formatDate("yyyy/MM/dd HH:mm:ss"));
	      
	      // Confirm
	      Alert confirm = new Alert(AlertType.CONFIRMATION);
	      ButtonType buttonTypeOk = new ButtonType("네");
	      ButtonType buttonTypeNo = new ButtonType("아니오");
	      confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

	      confirm.setTitle("회원가입");
	      confirm.setContentText("가입하시겠습니까?");
	      confirm.show();
	      
	      // confirm : "네" 버튼 눌렀을 때만 가입 되게
	      confirm.setOnCloseRequest(ev -> {
	         // "네" 버튼 누르면
	         if (confirm.getResult() == buttonTypeOk) {
	            confirm.close();   // confirm 창 close
	            
	            int flag = dao.doSave(inVO);
	            
	            // 가입 성공
	            if (flag == 1) {
	               alert.setAlertType(AlertType.INFORMATION);
	               alert.setTitle("회원가입 성공");
	               alert.setContentText("회원가입이 완료되었습니다");
	               Optional<ButtonType> result = alert.showAndWait();
	               
	               // 화면 Load
	               URL url = getClass().getResource("/VIEW/CONSUMER/ConLoginFX.fxml");
	               FXMLLoader load = new FXMLLoader(url);
	               Parent parent = null;

	               try {
	                  parent = load.load();
	               } catch (IOException e) {
	                  LOG.debug("= IOException = " + e.getMessage());
	                  e.printStackTrace();
	               }

	               Stage primaryStage = (Stage) doSignup.getScene().getWindow();

	               // 기존에 떠있던 화면 숨김 : view hide
	               primaryStage.close();

	               Scene scene = new Scene(parent);

	               // 윈도우 제목
	               primaryStage.setTitle("Login");
	               // 윈도우 화면에 Scene 추가
	               primaryStage.setScene(scene);
	               // 윈도우 화면에 보이게
	               primaryStage.show();
	            } else if (flag == 2) {
	               alert.setAlertType(AlertType.WARNING);
	               alert.setTitle("회원가입 실패");
	               alert.setContentText("회원가입에 실패했습니다. 이미 등록된 ID 입니다");
	               alert.show();
	            } else {
	               alert.setAlertType(AlertType.WARNING);
	               alert.setTitle("회원가입 실패");
	               alert.setContentText("회원가입에 실패했습니다");
	               alert.show();
	            }
	         } else if (confirm.getResult() == buttonTypeNo) {
	            confirm.close();
	         }
	      });
	         
	}
	
	
	// =======================================================
	// 뒤로가기 (화면이동)
	public void handlerMoveToLogin(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/CONSUMER/ConLoginFX.fxml");
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

		Stage primaryStage = (Stage) moveToLogin.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("Login");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
