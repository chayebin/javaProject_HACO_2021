package CONTROL.BUSINESS;

import java.io.IOException;

import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.MenuDao;
import VO.MenuVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BusMenuAddController implements Initializable {

	private final Logger LOG = Logger.getLogger(BusMenuAddController.class);

	@FXML
	Button menuAdd; // 메뉴 등록 버튼
	@FXML
	Button moveToBusMenuList; // 취소 버튼

	@FXML
	TextField addMenuName; // 메뉴 이름 필드
	@FXML
	TextField addMenuPrice; // 메뉴 가격 필드

	// 경고 메시지
	private Alert alert = new Alert(AlertType.WARNING);

	// MenuDao 불러오기
	MenuDao dao = new MenuDao();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
		menuAdd.setOnAction(event -> handleBtnMenuAdd(event));
		moveToBusMenuList.setOnAction(event -> handleBtnmMveToBusMenuList(event));
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------

	} // --- initialize

	// Event 처리 -----------------------------------------------------------------------------------------------------------------------
	public void handleBtnMenuAdd(ActionEvent event) {

		MenuVO inVO = new MenuVO();

		// menuNum 생성 : yyyyMMdd hhmmss_32bit 난수 = 40bit(날짜 + 32bit 난수)
		// 메뉴 번호
		inVO.setMenuNum(EStringUtil.getPK("yyyyMMdd"));

		// EStringUtil 을 통해 로그인 할 때 입력했던 사업자 ID 받기
		// 메뉴 등록자 ID
		inVO.setMenuBusId(EStringUtil.param02.getMenuBusId());

		// TextField 데이터 -> inVO
		// 메뉴 이름
		inVO.setMenuName(addMenuName.getText());

		if (Objects.equals(inVO.getMenuName(), "") || inVO.getMenuName().length() == 0) {
			alert.setTitle("menuName Error");
			alert.setContentText("메뉴를 입력하세요");
			alert.show();
			return;
		}

		// TextField 데이터 -> inVO
		// 메뉴 가격
		inVO.setMenuPrice(Integer.parseInt(addMenuPrice.getText()));

		if (Objects.equals(inVO.getMenuPrice(), 0)) {
			alert.setTitle("menuPrice Error");
			alert.setContentText("가격을 입력하세요");
			alert.show();
			return;
		}

		// 등록일
		inVO.setMenuDate(EStringUtil.formatDate(""));

		// Confirm
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonTypeOk = new ButtonType("네");
		ButtonType buttonTypeNo = new ButtonType("아니오");
		confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

		confirm.setTitle("메뉴 등록");
		confirm.setContentText("등록하시겠습니까?");
		confirm.show();

		// confirm : "네" 버튼 눌렀을 때만 등록되게
		confirm.setOnCloseRequest(ev -> {
			// "네" 버튼 누르면
			if (confirm.getResult() == buttonTypeOk) {
				confirm.close(); // confirm 창 close

				int flag = dao.doSave(inVO);

				// 메뉴 등록 성공
				if (flag == 1) {
					alert.setAlertType(AlertType.INFORMATION);
					alert.setTitle("메뉴 등록 성공");
					alert.setContentText("메뉴 등록이 완료되었습니다");
					Optional<ButtonType> result = alert.showAndWait();

					// 화면 Load
					URL url = getClass().getResource("/VIEW/BUSINESS/BusMenuListFX.fxml");
					FXMLLoader load = new FXMLLoader(url);
					Parent parent = null;

					try {
						parent = load.load();
					} catch (IOException e) {
						LOG.debug("= IOException = " + e.getMessage());
						e.printStackTrace();
					}

					Stage primaryStage = (Stage) moveToBusMenuList.getScene().getWindow();

					// 기존에 떠있던 화면 숨김 : view hide
					primaryStage.close();

					Scene scene = new Scene(parent);

					// 윈도우 제목
					primaryStage.setTitle("BusinessLogin");
					// 윈도우 화면에 Scene 추가
					primaryStage.setScene(scene);
					// 윈도우 화면에 보이게
					primaryStage.show();
				}

			} else if (confirm.getResult() == buttonTypeNo) {
				confirm.close();
			}
		});

	} // --- handleBtnMenuAdd

	// 취소 버튼 클릭
	public void handleBtnmMveToBusMenuList(ActionEvent evnet) {
		// 화면 Load
		URL url = getClass().getResource("/VIEW/BUSINESS/BusMenuListFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) moveToBusMenuList.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessMenuList");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();

	} // --- handleBtnmMveToBusMenuList
	
	// Event 처리 -----------------------------------------------------------------------------------------------------------------------

} // --- Class
