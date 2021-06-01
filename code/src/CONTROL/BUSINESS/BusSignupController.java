package CONTROL.BUSINESS;

import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.BusinessDao;
import VO.BusinessVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;


public class BusSignupController implements Initializable {

	private final Logger LOG = Logger.getLogger(BusSignupController.class);
	
	@FXML Button idCheck;			// ID 중복 체크 버튼
	@FXML Button signup;			// 회원가입하기 버튼
	@FXML Button moveToStartFX;		// 취소 버튼
	
	@FXML TextField busId;			// 사업자 ID 입력 필드
	@FXML PasswordField busPw;		// 사업자 PW 입력 필드
	@FXML TextField busName;		// 사업자 가게명 입력 필드
	@FXML TextField busPhone;		// 사업자 전화번호 입력 필드
	@FXML TextField busAddr;		// 사업자 가게 주소 입력 필드
	@FXML TextField busTable;		// 사업자 가게 테이블 수 입력 필드
	
	// 경고 메시지
	private Alert alert = new Alert(AlertType.WARNING);
	
	// BusinessDao 불러오기
	BusinessDao dao = new BusinessDao();
		
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
		idCheck.setOnAction(event -> handleBtnButtonIdCheck(event));
		signup.setOnAction(event -> handleBtnSignup(event));
		moveToStartFX.setOnAction(event -> handleBtnMoveToStartFX(event));		
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
	
	} // --- initialize
	
	// Event 처리 -----------------------------------------------------------------------------------------------------------------------
	// ID 중복 확인 버튼 클릭
	public void handleBtnButtonIdCheck(ActionEvent evnet) {
		
		// BusinessVO 불러오기
		BusinessVO inVO = new BusinessVO();
		
		String inputBusId = busId.getText();
		
		// ID 미입력 시 경고 메시지
		if (Objects.equals(inputBusId, "") || inputBusId.length() == 0) {
			alert.setTitle("ID Error");
			alert.setContentText("아이디를 입력하세요");
			alert.show();
			return;
		}
		
		// ID 중복 체크
		int flag = 0;
		inVO.setBusId(inputBusId);
		flag = dao.checkBusinessId(inVO);
		
		// ID 중복 : flag = 1, ID 중복 x : flag = 0
		if (flag == 1) { 
			alert.setTitle("ID Check Error");
			alert.setContentText("이미 등록된 ID 입니다");
			alert.show();
			busId.setText("");
			return;
		} else {
			// Confirm
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
				} else if (confirm.getResult() == buttonTypeNo){
					confirm.close();
					busId.setText("");
				}
			});
		}
	} // --- handleBtnButtonIdCheck
	
	// 회원가입 버튼 클릭
	public void handleBtnSignup(ActionEvent evnet) {
		
		BusinessVO inVO = new BusinessVO();

		// TextField 데이터 -> inVO
		inVO.setBusId(busId.getText());
		inVO.setBusPw(busPw.getText());
		inVO.setBusName(busName.getText());
		inVO.setBusPhone(busPhone.getText());
		inVO.setBusAddr(busAddr.getText());
		inVO.setBusTable(Integer.parseInt(busTable.getText()));
		// 등록일 : 현재시간
		inVO.setBusDate(EStringUtil.formatDate("yyyy/MM/dd HH:mm:ss"));
		
		// Confirm
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonTypeOk = new ButtonType("네");
		ButtonType buttonTypeNo = new ButtonType("아니오");
		confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

		confirm.setTitle("회원가입");
		confirm.setContentText("가입하시겠습니까?");
		confirm.show();
		
		// confirm : "네" 버튼 눌렀을 때만 가입되게
		confirm.setOnCloseRequest(ev -> {
			// "네" 버튼 누르면
			if (confirm.getResult() == buttonTypeOk) {
				confirm.close();	// confirm 창 close
				
				int flag = dao.doSave(inVO);
				
				// 가입 성공
				if (flag == 1) {
					alert.setAlertType(AlertType.INFORMATION);
					alert.setTitle("회원가입 성공");
					alert.setContentText("회원가입이 완료되었습니다");
					Optional<ButtonType> result = alert.showAndWait();
					
					// 화면 Load
					URL url = getClass().getResource("/VIEW/BUSINESS/BusLoginFX.fxml");
					FXMLLoader load = new FXMLLoader(url);
					Parent parent = null;

					try {
						parent = load.load();
					} catch (IOException e) {
						LOG.debug("= IOException = " + e.getMessage());
						e.printStackTrace();
					}

					Stage primaryStage = (Stage) moveToStartFX.getScene().getWindow();

					// 기존에 떠있던 화면 숨김 : view hide
					primaryStage.close();

					Scene scene = new Scene(parent);

					// 윈도우 제목
					primaryStage.setTitle("BusinessLogin");
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
			
	} // --- handleBtnSignup
	
	// 취소 버튼 클릭
	public void handleBtnMoveToStartFX(ActionEvent event) {
		
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

		Stage primaryStage = (Stage) moveToStartFX.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessStart");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
		
	} // --- handleBtnMoveToStartFX
	
	// Event 처리 -----------------------------------------------------------------------------------------------------------------------
} // --- CLass
