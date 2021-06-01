package CONTROL.BUSINESS;

import java.io.IOException;


import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.ReservationDao;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class BusReseController implements Initializable {
	Alert alert = new Alert(AlertType.WARNING);
	private final Logger LOG = Logger.getLogger(BusReseController.class);

	private ObservableList<ReservationVO> list;
	
	@FXML
	private Button doReseDelete;						// 예약 취소 버튼
	@FXML
	private Button moveToBusMain;						// 이전으로 버튼

	@FXML
	private TableView<ReservationVO> tableView;			// 테이블 뷰 필드

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// Data 읽기
		SearchVO search = new SearchVO();
		search.setSearchDiv(20);
		search.setSearchWord(EStringUtil.param.getReseBusId());
		LOG.debug("searchWord : " + search.toString());
		
		// Dao 불러오기
		ReservationDao dao = new ReservationDao();
		List<ReservationVO> reservationList = dao.doSelectList(search);
		
		list = FXCollections.observableArrayList(reservationList);
		LOG.debug("list : " + list);
		
		// Column에 Data 맵핑
		TableColumn reseNum = tableView.getColumns().get(0);
		reseNum.setCellValueFactory(new PropertyValueFactory("reseNum"));
		reseNum.setStyle("-fx-alignment:CENTER_LEFT");
		
		TableColumn reseConId = tableView.getColumns().get(1);
		reseConId.setCellValueFactory(new PropertyValueFactory("reseConId"));
		reseConId.setStyle("-fx-alignment:CENTER");
		
		TableColumn reseMenu = tableView.getColumns().get(2);
		reseMenu.setCellValueFactory(new PropertyValueFactory("reseMenu"));
		reseMenu.setStyle("-fx-alignment:CENTER_LEFT");
		
		TableColumn reseTime = tableView.getColumns().get(3);
		reseTime.setCellValueFactory(new PropertyValueFactory("reseTime"));
		reseTime.setStyle("-fx-alignment:CENTER_LEFT");
		
		TableColumn reseTable = tableView.getColumns().get(4);
		reseTable.setCellValueFactory(new PropertyValueFactory("reseTable"));
		reseTable.setStyle("-fx-alignment:CENTER_LEFT");
		
		// 데이터 넣기
		tableView.setItems(list);
		
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
		doReseDelete.setOnAction(event -> handleBtnDoReseDelete(event));			// 예약 취소 버튼 클릭
		moveToBusMain.setOnAction(event -> handleBtnMoveToBusMain(event));			// 이전으로 버튼 클릭
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------

	} // --- initialize

	// Event 처리
	// -----------------------------------------------------------------------------------------------------------------------

	// 예약 취소
	public void handleBtnDoReseDelete(ActionEvent event) {
	      ReservationVO selectedData = tableView.getSelectionModel().getSelectedItem();
	      ReservationDao resDao = new ReservationDao();
	      
	      // Confirm
	      Alert confirm = new Alert(AlertType.CONFIRMATION);
	      ButtonType buttonTypeOk = new ButtonType("네");
	      ButtonType buttonTypeNo = new ButtonType("아니오");
	      confirm.getButtonTypes().setAll(buttonTypeOk, buttonTypeNo);

	      confirm.setTitle("취소");
	      confirm.setContentText("취소하시겠습니까?");
	      confirm.show();

	      // confirm : "네" 버튼 눌렀을 때만 취소 되게
	      confirm.setOnCloseRequest(ev -> {
	         // "네" 버튼 누르면
	         if (confirm.getResult() == buttonTypeOk) {
	            confirm.close(); // confirm창 close
	            
	            int flag = resDao.doDelete(selectedData);
	            
	            // 삭제 성공
	            if (flag == 1) {
	               alert.setAlertType(AlertType.INFORMATION);
	               alert.setTitle("삭제 성공");
	               alert.setContentText("삭제되었습니다");
	               Optional<ButtonType> result = alert.showAndWait();
	               
	               ObservableList productSelected, allProducts;
	               allProducts = tableView.getItems();
	               productSelected = tableView.getSelectionModel().getSelectedItems();
	               
	               productSelected.forEach(allProducts::remove);

	               if (result.get() == ButtonType.OK) {
	                  alert.close();
	               }
	            }

	            // "아니오" 버튼 누르면
	         } else if (confirm.getResult() == buttonTypeNo) {
	            confirm.close();
	         }
	      });

	   } // --- handleBtnDoReseDelete

	// 사업자 메인 화면으로 이동
	public void handleBtnMoveToBusMain(ActionEvent event) {
		// 화면 Load
		URL url = getClass().getResource("/VIEW/BUSINESS/BusMainFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) moveToBusMain.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessMain");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
	} // --- handleBtnMoveToBusMain
	

	// Event 처리
	// -----------------------------------------------------------------------------------------------------------------------

} // --- Class
