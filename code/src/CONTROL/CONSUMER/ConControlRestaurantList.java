/**
 * 테이블리스트 선택안하고 예약하기 누르면 경고메세지 뜨게해야함
 */
package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.BusinessDao;
import VO.BusinessVO;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class ConControlRestaurantList implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(ConControlRestaurantList.class);

	private BusinessDao dao = new BusinessDao();

	@FXML
	private Button moveToConMain;
	@FXML
	private Button moveToReseRest;
	@FXML
	private TableView<BusinessVO> tableView;

	private ObservableList<BusinessVO> list;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ------------------------------------------
		// 테이블 뷰에 레스토랑 리스트 출력
		SearchVO search = new SearchVO();
		search.setSearchDiv(10);
		search.setSearchWord("");
		List<BusinessVO> restList = dao.doSelectList(search);
		list = FXCollections.observableArrayList(restList);
		TableColumn restName = tableView.getColumns().get(0);
		restName.setCellValueFactory(new PropertyValueFactory("busName"));
		restName.setStyle("-fx-alignment:CENTER");
		tableView.setItems(list);
		
		// ------------------------------------------
		//값 초기화
		EStringUtil.param.setReseBusId("");
		EStringUtil.param.setReseMenu("");
		EStringUtil.param.setReseNum("");
		EStringUtil.param.setReseTable("");
		EStringUtil.param.setReseTime("");

		// ------------------------------------------
		// 이벤트 감지
		moveToConMain.setOnAction(event -> handlerMoveToConMain(event));
		moveToReseRest.setOnAction(event -> handlerMoveToReseRest(event));
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClick(event));
	}

	// =======================================================
	// 뒤로가서 다시 소비자 메인페이지로 (화면이동)
	public void handlerMoveToConMain(ActionEvent event) {
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

		Stage primaryStage = (Stage) moveToConMain.getScene().getWindow();
		primaryStage.close();

		Scene scene = new Scene(parent);

		primaryStage.setTitle("ConMain");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	// =======================================================
	// 레스토랑 선택해서 예약하러가기 -> 버튼 화면이동 //널포인트 처리가 안됨
	public void handlerMoveToReseRest(ActionEvent event) {
		
		// ------------------------------------------
		// 선택한 리스트값 뽑아내기
		BusinessVO selectedData= tableView.getSelectionModel().getSelectedItem();

		URL url = getClass().getResource("/VIEW/CONSUMER/ReseRestFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
		
		// ------------------------------------------
		// 선택한 사업자 id 값 저장
		EStringUtil.param.setReseBusId(selectedData.getBusId());
		

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
	// 레스토랑 선택해서 예약하러가기 -> 더블클릭 화면이동
	public void handlerTableViewMouseClick(MouseEvent event) {
		if (event.getClickCount() != 2)
			return;

		//handlerMoveToReseRest 복붙
		BusinessVO selectedData= tableView.getSelectionModel().getSelectedItem();
		URL url = getClass().getResource("/VIEW/CONSUMER/ReseRestFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
		EStringUtil.param.setReseBusId(selectedData.getBusId());
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
}
