package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.BusinessDao;
import DAO.MenuDao;
import DAO.ReservationDao;
import VO.BusinessVO;
import VO.MenuVO;
import VO.SearchVO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConControlReseMenu implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(ConControlReseMenu.class);

	@FXML private Button moveToReseRest; //뒤로가기(레스토랑 시간날짜 예약하는페이지로_
	@FXML private Button doReservation; // 예약완료 (dosave & conMain페이지로 이동)
	@FXML private TableView<MenuVO> tableView;
	@FXML private Text price;

	private ObservableList<MenuVO> list;
	
	private int sumPrice = 0;
	private List menuList = new ArrayList();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ------------------------------------------
		// 테이블 뷰에 레스토랑 리스트 출력
		MenuDao menuDao = new MenuDao();
		SearchVO search = new SearchVO();
		search.setSearchDiv(20);
		search.setSearchWord(EStringUtil.param.getReseBusId());
		List<MenuVO> menuList = menuDao.doSelectList(search);
		list = FXCollections.observableArrayList(menuList);
		TableColumn menuName = tableView.getColumns().get(0);
		menuName.setCellValueFactory(new PropertyValueFactory("menuName"));
		menuName.setStyle("-fx-alignment:CENTER");
		TableColumn menuPrice = tableView.getColumns().get(1);
		menuPrice.setCellValueFactory(new PropertyValueFactory("menuPrice"));
		menuPrice.setStyle("-fx-alignment:CENTER");
		tableView.setItems(list);
		
		// ------------------------------------------
		// 이벤트 감지
		moveToReseRest.setOnAction(event -> handlerMoveToReseRestset(event));
		doReservation.setOnAction(event -> handlerDoReservation(event));
		tableView.setOnKeyPressed(event -> handleTxtUpBtn(event));
	}
	
	// =======================================================
	// 버튼 눌러서 메뉴 추가삭제
	public void handleTxtUpBtn(KeyEvent event) {
		MenuVO selectData = tableView.getSelectionModel().getSelectedItem();
		
		// ------------------------------------------
		// A를 눌러서 메뉴 추가
		if (event.getCode().equals(KeyCode.A)) {
			sumPrice += selectData.getMenuPrice();
			price.setText( Integer.toString(sumPrice));
			menuList.add(selectData.getMenuNum());
		
		// ------------------------------------------
		// S를 눌러서 메뉴 삭제
		}else if (event.getCode().equals(KeyCode.S)) {
			if(sumPrice <= 0 || sumPrice < selectData.getMenuPrice()) {
				alert.setTitle("Price Error");
				alert.setContentText("잘못된 요청입니다.");
				alert.show();
				return;
			}
			if(menuList.contains(selectData.getMenuNum()) == false) {
				alert.setTitle("Price Error");
				alert.setContentText("잘못된 요청입니다.");
				alert.show();
				return;
			}
			sumPrice -= selectData.getMenuPrice();
			price.setText( Integer.toString(sumPrice));
			menuList.remove(selectData.getMenuNum());
		}
	}
	
	
	// =======================================================
	// 뒤로가서 레스토랑 예약화면으로 시간날짜선택하는곳 (화면이동)
	public void handlerMoveToReseRestset(ActionEvent event) {
		URL url = getClass().getResource("/VIEW/CONSUMER/ReseRestFX.fxml");
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

		Stage primaryStage = (Stage) moveToReseRest.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("Reservation Restaurant");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	// =======================================================
	//예약완료버튼 //dosave & conMain페이지로 이동
	public void handlerDoReservation(ActionEvent event) {
		
		// ------------------------------------------
		// 추가한 메뉴가 없으면 경고메세지
		if(menuList.size() == 0) {
			alert.setTitle("Menu Error");
			alert.setContentText("잘못된 요청입니다.");
			alert.show();
			return;
		}
		
		// ------------------------------------------
		// 메뉴 이름을 +로 해서 한줄로 만들어 저장
		String Menu = "";
		for(int i = 0; i < menuList.size(); i++ ) {
			if(i +1 == menuList.size()) {
				Menu = Menu + menuList.get(i);
			}else {
				Menu = Menu + menuList.get(i) + "+";
			}
		}
		EStringUtil.param.setReseMenu(Menu);
		
		EStringUtil.param.setReseNum(EStringUtil.getPK("yyyyMMdd"));
		
		// ------------------------------------------
		// dosave
		ReservationDao reDao = new ReservationDao();
		int flag = 0;
		flag = reDao.doSave(EStringUtil.param);
		
		// ------------------------------------------
		// dosave
		if(flag == 1) {
			URL url = getClass().getResource("/VIEW/CONSUMER/ConMainFX.fxml");
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
			
			Stage primaryStage = (Stage) doReservation.getScene().getWindow();
			primaryStage.close();

			Scene scene = new Scene(parent);

			primaryStage.setTitle("ConMain");
			primaryStage.setScene(scene);
			primaryStage.show();
		}else{
			alert.setTitle("Reservation Error");
			alert.setContentText("예약에 필요한 정보가 부족합니다.");
			alert.show();
			return;
		}
	}

}
