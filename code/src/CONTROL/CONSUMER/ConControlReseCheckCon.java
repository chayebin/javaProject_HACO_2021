package CONTROL.CONSUMER;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.DTO;
import CMN.EStringUtil;
import DAO.BusinessDao;
import DAO.ConsumerDao;
import DAO.MenuDao;
import DAO.ReservationDao;
import VO.BusinessVO;
import VO.ReservationVO;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class ConControlReseCheckCon implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(ConControlReseCheckCon.class);
	
	@FXML private Button moveToConMain; //뒤로가기 버튼
	@FXML private TableView<ReservationVO> tableView; //예약리스트
	
	private ObservableList<ReservationVO> list;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ------------------------------------------
		// 테이블 뷰에 레스토랑 리스트 출력
		ReservationDao dao = new ReservationDao();
		SearchVO search = new SearchVO();
		
		search.setSearchDiv(20);
		search.setSearchWord(EStringUtil.param.getReseConId());
		List<ReservationVO> reseList = dao.doSelectList(search);
		list = FXCollections.observableArrayList(reseList);
		
		TableColumn reseBusId = tableView.getColumns().get(0);
		reseBusId.setCellValueFactory(new PropertyValueFactory("reseBusId"));
		reseBusId.setStyle("-fx-alignment:CENTER");
		TableColumn reseMenu = tableView.getColumns().get(1);
		reseMenu.setCellValueFactory(new PropertyValueFactory("reseMenu"));
		reseMenu.setStyle("-fx-alignment:CENTER");
		TableColumn reseTime = tableView.getColumns().get(2);
		reseTime.setCellValueFactory(new PropertyValueFactory("reseTime"));
		reseTime.setStyle("-fx-alignment:CENTER");
		TableColumn reseTable = tableView.getColumns().get(3);
		reseTable.setCellValueFactory(new PropertyValueFactory("reseTable"));
		reseTable.setStyle("-fx-alignment:CENTER");
		
		tableView.setItems(list);
		
		// ------------------------------------------
		// 이벤트 감지
		moveToConMain.setOnAction(event -> handlerMoveToConMain(event));
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
	
}
