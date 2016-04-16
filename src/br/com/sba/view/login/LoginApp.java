package br.com.sba.view.login;
	
import java.io.IOException;

import br.com.sba.AppolodorusApp;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

//Classe específica de inicialização da aplicação
public class LoginApp extends Application {
	
    public static Stage palco;
    private static Scene cena;
    private static AnchorPane page;
	
	@Override
	public void start(Stage stage) throws IOException{
		palco=stage;
        page = FXMLLoader.load(getClass().getResource("loginapp.fxml"));//como arquivos no mesmo package informar apenas o nome do arquivo
        cena = new Scene(page);
		//Icone no palco principal
		stage.getIcons().add(new Image("/br/com/sba/img/icon/library-icon.png"));
		//Titulo do palco
		stage.setTitle("SBA - Sistema de Bibliotecas Appolodorus");
		//Desabilita alteração de tamanho da tela
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
