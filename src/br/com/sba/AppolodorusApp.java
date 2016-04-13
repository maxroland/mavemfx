package br.com.sba;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;


public class AppolodorusApp extends Application{
	
    public static Stage palco;
    private static Scene cena;
    private static AnchorPane page;

    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();
	
	@Override
	public void start(Stage stage) throws IOException{
        palco = stage;
        page = FXMLLoader.load(AppolodorusApp.class.getResource("/br/com/sba/view/app/App.fxml"));
		//adiciona a view fxml(page) na cena
        cena = new Scene(page);        
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(windows.getMinX());
        stage.setY(windows.getMinY());
        stage.setWidth(windows.getWidth());
        stage.setHeight(windows.getHeight());
		//Icone no palco principal
		stage.getIcons().add(new Image("br/com/sba/img/icon/library-icon.png"));
		//Titulo do palco
		stage.setTitle("SBA - Sistema de Bibliotecas Appolodorus");
		//vincula a cena ao palco
		stage.setScene(cena);
		//apresenta a cena
		stage.show();

	}
	
//	public static void main(String[] args) {
//		launch(args);
//	}
}
