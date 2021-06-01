package CONTROL.BUSINESS;

import java.io.IOException;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.BusinessDao;
import VO.BusinessVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class BusLoginController implements Initializable {

	private final Logger LOG = Logger.getLogger(BusLoginController.class);

	@FXML
	private Button doLogin; // 로그인 버튼
	@FXML
	private Button moveToStart; // 뒤로가기 버튼
	@FXML
	private Button moveToBusSignup; // 회원가입 버튼
	@FXML
	private TextField inputId; // 사업자 ID 입력 필드
	@FXML
	private PasswordField inputPw; // 사업자 PW 입력 필드

	// 경고 메시지
	private Alert alert = new Alert(AlertType.WARNING);

	// BusinessDao 불러오기
	BusinessDao dao = new BusinessDao();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Event 감지
		// -----------------------------------------------------------------------------------------------------------------------
		doLogin.setOnAction(event -> handleBtnDoLogin(event)); // 로그인 버튼 클릭
		moveToStart.setOnAction(event -> handleMoveToStart(event)); // 뒤로가기 버튼 클릭
		moveToBusSignup.setOnAction(event -> handleMoveToBusSignup(event)); // 회원가입 버튼 클릭
		inputPw.setOnKeyPressed(event -> handlePwDoLogin(event)); // PW 텍스트필드에서 엔터 입력
		// Event 감지
		// -----------------------------------------------------------------------------------------------------------------------

	} // --- initialize

	// Event 처리
	// -----------------------------------------------------------------------------------------------------------------------
	// 사업자 회원 로그인 -> 사업자 메인 화면으로 이동
	public void handleBtnDoLogin(ActionEvent event) {
		
		BusinessVO inVO = new BusinessVO();
		
		String inputBusId = inputId.getText();
		String inputBusPw = inputPw.getText();
		
		// ID 미입력 시 경고 메시지
		if (Objects.equals(inputBusId, "") || inputBusId.length() == 0) {
			alert.setTitle("ID Error");
			alert.setContentText("아이디를 입력하세요");
			alert.show();
			return;
		}
		
		// PW 미입력 시 경고 메시지
		if (Objects.equals(inputBusPw, "") || inputBusPw.length() == 0) {
			alert.setTitle("PW Error");
			alert.setContentText("비밀번호를 입력하세요");
			alert.show();
			return;
		}
		
		// 로그인
		int flag = 0;
		inVO.setBusId(inputBusId);
		inVO.setBusPw(inputBusPw);
		LOG.info("입력값 확인 : " + inVO);
		flag = dao.doLogIn(inVO);
		
		// ID, PW 모두 일치 하면 사업자 메인 페이지로 이동
		if (flag == 2) {
			
			// 화면 Load
			URL url = getClass().getResource("/VIEW/BUSINESS/BusMainFX.fxml");
			FXMLLoader load = new FXMLLoader(url);
			Parent parent = null;
			
			// 로그인 ID 저장 ----------------------------------------------------------
			EStringUtil.param02.setMenuBusId(inputBusId);
	              EStringUtil.param.setReseBusId(inputBusId);
	              
	              
	              LOG.debug("로그인후" + EStringUtil.param02 + EStringUtil.param +  inputBusId);
	              
			try {
				parent = load.load();
			} catch (IOException e) {
				LOG.debug("= IOException = " + e.getMessage());
				e.printStackTrace();
			}

			Stage primaryStage = (Stage) doLogin.getScene().getWindow();

			// 기존에 떠있던 화면 숨김 : view hide
			primaryStage.close();

			Scene scene = new Scene(parent);

			// 윈도우 제목
			primaryStage.setTitle("BusinessMain");
			// 윈도우 화면에 Scene 추가
			primaryStage.setScene(scene);
			// 윈도우 화면에 보이게
			primaryStage.show();
			
		} else if (flag == 0) {
			alert.setTitle("LogIn Error");
			alert.setContentText("등록되지 않은 ID 입니다");
			alert.show();
			return;
		} else {
			alert.setTitle("LogIn Error");
			alert.setContentText("비밀번호가 일치하지 않습니다");
			alert.show();
			return;
		}
		
		
	} // --- handleBtnDoLogin

	// 시작 페이지로 이동
	public void handleMoveToStart(ActionEvent event) {

		// 화면 Load
		URL url = getClass().getResource("/VIEW/START_ADMIN/StartFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		
		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) doLogin.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessStart");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();

	} // --- handleMoveToStart

	// 사업자 회원가입 페이지로 이동
	public void handleMoveToBusSignup(ActionEvent evnet) {

		// 화면 Load
		URL url = getClass().getResource("/VIEW/BUSINESS/BusSignupFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) doLogin.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessSignUp");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();

	} // --- handleMoveToBusSignup

	// PW 입력 필드에서 Enter 입력
	public void handlePwDoLogin(KeyEvent event) {

		if (event.getCode().equals(KeyCode.ENTER)) {
			BusinessVO inVO = new BusinessVO();

			String inputBusId = inputId.getText();
			String inputBusPw = inputPw.getText();

			// ID 미입력 시 경고 메시지
			if (Objects.equals(inputBusId, "") || inputBusId.length() == 0) {
				alert.setTitle("ID Error");
				alert.setContentText("아이디를 입력하세요");
				alert.show();
				return;
			}

			if (Objects.equals(inputBusPw, "") || inputBusPw.length() == 0) {
				alert.setTitle("PW Error");
				alert.setContentText("비밀번호를 입력하세요");
				alert.show();
				return;
			}

			// 로그인
			int flag = 0;
			inVO.setBusId(inputBusId);
			inVO.setBusPw(inputBusPw);
			flag = dao.doLogIn(inVO);

			// ID, PW 모두 일치 하면 사업자 메인 페이지로 이동
			if (flag == 2) {

				// 화면 Load
				URL url = getClass().getResource("/VIEW/BUSINESS/BusMainFX.fxml");
				FXMLLoader load = new FXMLLoader(url);
				Parent parent = null;

				// 로그인 ID 저장 ----------------------------------------------------------
				EStringUtil.param02.setMenuBusId(inputBusId);
		              EStringUtil.param.setReseBusId(inputBusId);
		              
				
				try {
					parent = load.load();
				} catch (IOException e) {
					LOG.debug("= IOException = " + e.getMessage());
					e.printStackTrace();
				}

				Stage primaryStage = (Stage) doLogin.getScene().getWindow();

				// 기존에 떠있던 화면 숨김 : view hide
				primaryStage.close();

				Scene scene = new Scene(parent);

				// 윈도우 제목
				primaryStage.setTitle("BusinessMain");
				// 윈도우 화면에 Scene 추가
				primaryStage.setScene(scene);
				// 윈도우 화면에 보이게
				primaryStage.show();

			} else if (flag == 0) {
				alert.setTitle("LogIn Error");
				alert.setContentText("등록되지 않은 ID 입니다");
				alert.show();
				return;
			} else {
				alert.setTitle("LogIn Error");
				alert.setContentText("비밀번호가 일치하지 않습니다");
				alert.show();
				return;
			}
		}
	} // --- handlePwDoLogin

	// Event 처리
	// -----------------------------------------------------------------------------------------------------------------------

} // --- Class
