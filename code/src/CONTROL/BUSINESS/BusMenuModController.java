package CONTROL.BUSINESS;

import java.io.IOException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


public class BusMenuModController implements Initializable {

	private final Logger LOG = Logger.getLogger(BusMenuModController.class);

	@FXML
	Button moveToMenuList; // 취소 버튼
	@FXML
	Button doDeleteMenu; // 삭제 버튼
	@FXML
	Button doModifyMenu; // 수정 버튼

	@FXML
	TextField menuName; // 메뉴 이름 필드
	@FXML
	TextField menuPrice; // 메뉴 가격 필드

	// 경고 메시지
	private Alert alert = new Alert(AlertType.WARNING);

	// MunuDao 불러오기
	MenuDao dao = new MenuDao();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//LOG.debug("param : " + EStringUtil.param02);
		MenuVO outVO = (MenuVO) dao.doSelectOne(EStringUtil.param02);
		
		menuName.setText(outVO.getMenuName());
		menuPrice.setText(Integer.toString(outVO.getMenuPrice()));
		
		// Event 감지
		// -----------------------------------------------------------------------------------------------------------------------
		moveToMenuList.setOnAction(event -> handleBtnMoveToMenuList(event)); // 취소 버튼 클릭
		doDeleteMenu.setOnAction(event -> handleBtnDoDeleteMenu(event)); // 삭제 버튼 클릭
		doModifyMenu.setOnAction(event -> handleBtnDoModifyMenu(event)); // 수정 버튼 클릭
		// Event 감지
		// -----------------------------------------------------------------------------------------------------------------------
	} // --- initialize

	// Event 처리
	// -----------------------------------------------------------------------------------------------------------------------

	// BusMenuList화면으로 이동
	public void handleBtnMoveToMenuList(ActionEvent event) {
		handlerMoveToList();
	}

	// 취소 버튼 클릭
	public void handlerMoveToList() {
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

		Stage primaryStage = (Stage) moveToMenuList.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessStart");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
	} // --- handleBtnMoveToMenuList

	// 삭제 버튼 클릭
	public void handleBtnDoDeleteMenu(ActionEvent event) {
		MenuVO inVO = (MenuVO) dao.doSelectOne(EStringUtil.param02);
		
		// Confirm
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonTypeOk = new ButtonType("네");
		ButtonType buttonTypeNo = new ButtonType("아니오");
		confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

		confirm.setTitle("삭제");
		confirm.setContentText("삭제하시겠습니까?");
		confirm.show();

		// confirm : "네" 버튼 눌렀을 때만 삭제 되게
		confirm.setOnCloseRequest(ev -> {
			// "네" 버튼 누르면
			if (confirm.getResult() == buttonTypeOk) {
				confirm.close(); // confirm창 close

				int flag = dao.doDelete(inVO);

				// 삭제 성공
				if (flag == 1) {
					alert.setAlertType(AlertType.INFORMATION);
					alert.setTitle("삭제 성공");
					alert.setContentText("삭제되었습니다");
					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.OK) {
						alert.close();
						handlerMoveToList();
					}

				}

				// "아니오" 버튼 누르면
			} else if (confirm.getResult() == buttonTypeNo) {
				confirm.close();
			}
		});

	} // --- handleBtnDoDeleteMenu

	public void handleBtnDoModifyMenu(ActionEvent event) {
		MenuVO inVO = (MenuVO) dao.doSelectOne(EStringUtil.param02);
		
		inVO.setMenuName(menuName.getText());
		inVO.setMenuPrice(Integer.parseInt(menuPrice.getText()));
		inVO.setMenuDate(EStringUtil.formatDate("yyyy/MM/dd HH:mm:ss"));

		// 입력 필수 처리
		if (Objects.equals(inVO.getMenuName(), "") || inVO.getMenuName().length() == 0) {
			alert.setTitle("busMenuName Error");
			alert.setContentText("메뉴를 입력하세요");
			alert.show();
			return;
		}

		if (Objects.equals(inVO.getMenuPrice(), 0)) {
			alert.setTitle("busMenuPrice Error");
			alert.setContentText("가격을 입력하세요");
			alert.show();
			return;
		}

		// Confirm
		Alert confirm = new Alert(AlertType.CONFIRMATION);
		ButtonType buttonTypeOk = new ButtonType("네");
		ButtonType buttonTypeNo = new ButtonType("아니오");
		confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

		confirm.setTitle("수정");
		confirm.setContentText("수정하시겠습니까?");
		confirm.show();

		// confirm : "네" 버튼 눌렀을 때만 수정 되게
		confirm.setOnCloseRequest(ev -> {
			// "네" 버튼 누르면
			if (confirm.getResult() == buttonTypeOk) {
				confirm.close(); // confirm창 close
				int flag = dao.doUpdate(inVO);

				// 수정 성공
				if (flag == 2) {
					alert.setAlertType(AlertType.INFORMATION);
					alert.setTitle("수정 성공");
					alert.setContentText("수정되었습니다");
					Optional<ButtonType> result = alert.showAndWait();

					if (result.get() == ButtonType.OK) {
						alert.close();
						handlerMoveToList();
					}

				}

				// "아니오" 버튼 누르면
			} else if (confirm.getResult() == buttonTypeNo) {
				confirm.close();
			}
		});

	} // --- handleBtnDoModifyMenu

	// Event 처리
	// -----------------------------------------------------------------------------------------------------------------------

} // --- Class
