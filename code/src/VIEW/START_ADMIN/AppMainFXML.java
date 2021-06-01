/**
 * 
 */
package VIEW.START_ADMIN;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author user
 *
 */
public class AppMainFXML extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("StartFX.fxml"));
//		Parent root = FXMLLoader.load(getClass().getResource("/VIEW/BUSINESS/BusSignupFX.fxml"));
		
		//Scene에 추가
		Scene scene = new Scene(root);
		
//		//외부스타일 읽기
//		URL cssURL = getClass().getResource("app.css");
//		System.out.println("cssURL:"+cssURL);
//		scene.getStylesheets().add(cssURL.toString());
		
		//Stage에 scene추가
		primaryStage.setScene(scene);
		primaryStage.setTitle("StartMain");
		primaryStage.show();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
