package CONTROL.BUSINESS;

import java.io.IOException;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

import CMN.EStringUtil;
import DAO.MenuDao;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class BusMenuListController implements Initializable {

	private final Logger LOG = Logger.getLogger(BusMenuListController.class);

	private ObservableList<MenuVO> list;
	
	@FXML
	private Button busAddMenu;				// 메뉴 등록 버튼
	@FXML private Button moveToBusMain;		// 이전으로 버튼
	
	@FXML
	TableView<MenuVO> tableView;			// 테이블 뷰

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Menu에 있는 Data 읽기
		// 검색 : doSelectList()
		SearchVO search = new SearchVO();
		search.setSearchDiv(20);			// 사업자 로그인 ID
		search.setSearchWord(EStringUtil.param02.getMenuBusId());
		
		// Dao 불러오기
		MenuDao dao = new MenuDao();
		List<MenuVO> menuList = dao.doSelectList(search);
		
		list = FXCollections.observableArrayList(menuList);
		
		// Column에 Data 맵핑
		TableColumn menuNum = tableView.getColumns().get(0);
		menuNum.setCellValueFactory(new PropertyValueFactory("menuNum"));
		menuNum.setStyle("-fx-alignment:CENTER_LEFT");
		menuNum.setVisible(false);
	
		TableColumn menuName = tableView.getColumns().get(1);
		menuName.setCellValueFactory(new PropertyValueFactory("menuName"));
		menuName.setStyle("-fx-alignment:CENTER");
		
		TableColumn menuPrice = tableView.getColumns().get(2);
		menuPrice.setCellValueFactory(new PropertyValueFactory("menuPrice"));
		menuPrice.setStyle("-fx-alignment:CENTER");
		
		// 데이터 넣기
		tableView.setItems(list);
	
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
		busAddMenu.setOnAction(event -> handleBtnBusAddMenu(event));					// 메뉴 등록 버튼 클릭
		moveToBusMain.setOnAction(event -> handleBtnMoveToBusMain(event));				// 이전으로 버튼 클릭	
		tableView.setOnMouseClicked(event -> handlerTableViewMouseClick(event));		// 테이블 Column 클릭
		// Event 감지 -----------------------------------------------------------------------------------------------------------------------
	
	} // --- initialize

	// Event 처리 -----------------------------------------------------------------------------------------------------------------------
	// 사업자 메뉴 등록 화면으로 이동
	public void handleBtnBusAddMenu(ActionEvent event) {
		// 화면 Load
		URL url = getClass().getResource("/VIEW/BUSINESS/BusMenuAddFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) busAddMenu.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessMenuAdd");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
	} // --- handleBtnBusAddMenu
	
	// 이전으로 버튼 클릭
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
	
	// 테이블 Column 더블 클릭 시
	public void handlerTableViewMouseClick(MouseEvent event) {
		if (event.getClickCount() != 2) {
			return;
		}	
		LOG.debug("= handlerTableViewMouseClick = ");
		
		MenuVO selectedData = tableView.getSelectionModel().getSelectedItem();
		
		// 화면 Load
		URL url = getClass().getResource("/VIEW/BUSINESS/BusMenuModFX.fxml");
		FXMLLoader load = new FXMLLoader(url);
		Parent parent = null;
		BusMenuModController modController = load.getController();
		
		// param 전달
		EStringUtil.param02.setMenuNum(selectedData.getMenuNum());

		try {
			parent = load.load();
		} catch (IOException e) {
			LOG.debug("= IOException = " + e.getMessage());
			e.printStackTrace();
		}

		Stage primaryStage = (Stage) busAddMenu.getScene().getWindow();

		// 기존에 떠있던 화면 숨김 : view hide
		primaryStage.close();

		Scene scene = new Scene(parent);

		// 윈도우 제목
		primaryStage.setTitle("BusinessMenuMod");
		// 윈도우 화면에 Scene 추가
		primaryStage.setScene(scene);
		// 윈도우 화면에 보이게
		primaryStage.show();
		
	} // --- handlerTableViewMouseClick
	
	// Event 처리 -----------------------------------------------------------------------------------------------------------------------

} // --- Class
