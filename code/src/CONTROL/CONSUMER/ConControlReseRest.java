/**
 * 테이블중복
 * 경고메세지
 */

package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.BusinessDao;
import DAO.ReservationDao;
import VO.BusinessVO;
import VO.ReservationVO;
import VO.SearchVO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ConControlReseRest implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(ConControlReseRest.class);

	private BusinessDao dao = new BusinessDao();
	
	@FXML private Button moveToReseMenu; //레스토랑 시간 자리 예약 후 메뉴선택하러 이동
	@FXML private Button moveToRestList; // 뒤로가기 (레스토랑 리스트)
	@FXML private Text RestName;
	@FXML private ComboBox<String> reseTime; //예약시간
	@FXML private DatePicker reseDate; //예약날짜
	@FXML private ToggleGroup group; //식당테이블용 radio
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// 선택한 가게로 이름 변경해주기 //id값을 받아와서 name을 뽑아내야함
		BusinessVO getName = new BusinessVO();
		getName.setBusId(EStringUtil.param.getReseBusId());
		getName = (BusinessVO) dao.doSelectOne(getName);
		RestName.setText(getName.getBusName());
		
		moveToReseMenu.setOnAction(event -> handlerMoveToReseMenu(event));
		moveToRestList.setOnAction(event -> handlerMoveToRestList(event));
	}

	// =======================================================
	// 메뉴 예약하러가기 (화면이동)
	public void handlerMoveToReseMenu(ActionEvent event) {
		// ------------------------------------------
		// 현재시간보다 이전이면 경고메세지
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String nowDate = sdf.format(date);
		String reseDateAndTime = reseDate.getValue() + " " + reseTime.getValue();
		Date reseDateAndTimeFormat = null;
		Date nowDateFormat = null;
		try {
			reseDateAndTimeFormat = sdf.parse(reseDateAndTime);
			nowDateFormat = sdf.parse(nowDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if(reseDateAndTimeFormat.getTime() <= nowDateFormat.getTime() ) {
			alert.setTitle("Date Error");
			alert.setContentText("날짜가 잘못되었습니다. ");
			alert.show();
			return;
		}
		
//		RadioButton radio = (RadioButton) group.getSelectedToggle();
		// ------------------------------------------
		// 테이블 중복
//		ReservationDao reDao = new ReservationDao();
//		SearchVO search = new SearchVO();
//		search.setSearchDiv(20);
//		search.setSearchWord(EStringUtil.param.getReseBusId());
//		ReservationVO invo = (ReservationVO) reDao.doSelectList(search);
//		if(invo.getReseTime().equals(reseDateAndTime) == 
//		}
//		
		
		
		
		URL url = getClass().getResource("/VIEW/CONSUMER/ReseMenuFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
		
		// ------------------------------------------
		//radio버튼
		RadioButton radio = (RadioButton) group.getSelectedToggle();
		EStringUtil.param.setReseTable(radio.getText());
		EStringUtil.param.setReseTime(reseDate.getValue() + " " + reseTime.getValue());
		
		
		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("===================");
			LOG.debug("=IOException=" + e.getMessage());
			LOG.debug("===================");
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) moveToReseMenu.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("Reservation Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void ValueCheck(String param) {
		if (Objects.equals(param, "") || param.length() == 0) {
			alert.setTitle("Value Error");
			alert.setContentText("회원가입에 필요한 데이터가 부족합니다.");
			alert.show();
		}
	}
	
	// =======================================================
	// 뒤로가기 -> 레스토랑 리스트로 (화면이동)
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
	
}
