package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ConControlLogin implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(ConControlLogin.class);

	private ConsumerDao dao = new ConsumerDao();

	@FXML private Button moveToStart; //뒤로가기 버튼
	@FXML private Button moveToConSignup; // 소비자 회원가입 페이지로 이동
	@FXML private Button doLogin; // 로그인 완료
	@FXML private TextField inputId; // 아이디 입력칸
	@FXML private PasswordField inputPw; // 비밀번호 입력칸

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		moveToStart.setOnAction(event -> handlerMoveToStart(event));
		moveToConSignup.setOnAction(event -> handleBtnMoveToConSignup(event));
		doLogin.setOnAction(event -> handlerDoLogin(event));
		inputPw.setOnKeyPressed(event -> handleTxtDoLogin(event));
	}

	// =======================================================
	// login -> 버튼클릭
	public void handlerDoLogin(ActionEvent event) {
		ConsumerVO inVO = new ConsumerVO();

		// ------------------------------------------
		// 데이터 미입력시 경고메세지
		String inputConId = inputId.getText();
		if (Objects.equals(inputConId, "") || inputConId.length() == 0) {
			alert.setTitle("ID Error");
			alert.setContentText("아이디를 입력하세요 ");
			alert.show();
			return;
		}

		String inputConPw = inputPw.getText();
		if (Objects.equals(inputConPw, "") || inputConPw.length() == 0) {
			alert.setTitle("PW Error");
			alert.setContentText("비밀번호를 입력하세요 ");
			alert.show();
			return;
		}

		// ------------------------------------------
		// 로그인
		int flag = 0;
		inVO.setConId(inputConId);
		inVO.setConPw(inputConPw);
		flag = dao.doLogIn(inVO);
		
		// ------------------------------------------
		// id와 pw가 모두 일치한다면 소비자 메인페이지로 이동
		if (flag == 2) {
			URL url = getClass().getResource("/VIEW/CONSUMER/ConMainFX.fxml");
			FXMLLoader load = new FXMLLoader(url);
			Parent parent = null;

			// ------------------------------------------
			// 로그인아이디저장
			EStringUtil.param.setReseConId(inputConId);
			//LOG.debug("화면이동전" + EStringUtil.param);
			
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

			primaryStage.setTitle("ConMain");
			primaryStage.setScene(scene);
			primaryStage.show();

			// id 또는 pw가 일치하지 않는다면 경고메세지
		} else {
			alert.setTitle("Login Error");
			alert.setContentText("아이디나 비밀번호가 잘못되었습니다.");
			alert.show();
			return;
		}
	}

	// =======================================================
	// login -> pw 칸에서 엔터
	public void handleTxtDoLogin(KeyEvent event) {
		if (event.getCode().equals(KeyCode.ENTER)) {
			
			//handlerDoLogin 그대로 복붙
			ConsumerVO inVO = new ConsumerVO();
			String inputConId = inputId.getText();
			if (Objects.equals(inputConId, "") || inputConId.length() == 0) {
				alert.setTitle("ID Error");
				alert.setContentText("아이디를 입력하세요 ");
				alert.show();
				return;
			}
			String inputConPw = inputPw.getText();
			if (Objects.equals(inputConPw, "") || inputConPw.length() == 0) {
				alert.setTitle("PW Error");
				alert.setContentText("비밀번호를 입력하세요 ");
				alert.show();
				return;
			}
			int flag = 0;
			inVO.setConId(inputConId);
			inVO.setConPw(inputConPw);
			flag = dao.doLogIn(inVO);
			if (flag == 2) {
				URL url = getClass().getResource("/VIEW/CONSUMER/ConMainFX.fxml");
				FXMLLoader load = new FXMLLoader(url);
				Parent parent = null;
				EStringUtil.param.setReseConId(inputConId);
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
				primaryStage.setTitle("ConMain");
				primaryStage.setScene(scene);
				primaryStage.show();
			} else {
				alert.setTitle("Login Error");
				alert.setContentText("아이디나 비밀번호가 잘못되었습니다.");
				alert.show();
				return;
			}
		}
	}

	// =======================================================
	// signup (화면이동)
	public void handleBtnMoveToConSignup(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/CONSUMER/ConSignupFX.fxml");
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

		Stage primaryStage = (Stage) moveToConSignup.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("Sign up");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// =======================================================
	// ←버튼 (화면이동)
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
}
