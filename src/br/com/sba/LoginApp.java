package br.com.sba;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

//Classe especafica de inicializaaao da aplicaaao
public class LoginApp extends Application {
	
    public static Stage palco;
    private static Scene cena;
    private static AnchorPane page;
	
	@Override
	public void start(Stage stage) throws IOException{
		palco=stage;
        page = FXMLLoader.load(getClass().getResource("/br/com/sba/view/login/loginapp.fxml"));//como arquivos no mesmo package informar apenas o nome do arquivo
        cena = new Scene(page);
		//Icone no palco principal
		stage.getIcons().add(new Image("/br/com/sba/img/icon/library-icon.png"));
		//Titulo do palco
		stage.setTitle("SBA - Sistema de Bibliotecas Appolodorus");
		//Desabilita alteraaao de tamanho da tela
		stage.setResizable(false);
		//vincula a cena ao palco
		stage.setScene(cena);
		//apresenta a cena
		stage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
