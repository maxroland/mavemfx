package br.com.sba;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class AppolodorusApp extends Application {
	
	//public static EntityManagerFactory emf;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		
		//carrega o form principal Pane (root)
		Pane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/Login.fxml"));
		//Scene scene = new Scene(root,400,400);
		//Icone no palco principal
		primaryStage.getIcons().add(new Image("br/com/sba/img/icon/library-512.png"));
		//Titulo do palco
		primaryStage.setTitle("SBA - Sistema de Bibliotecas Appolodorus");
		//adiciona o form principal na cena
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("sba.css").toExternalForm());
		//Desabilita alteração de tamanho da tela
		primaryStage.setResizable(false);
		//vincula a cena ao palco
		primaryStage.setScene(scene);
		//apresenta a cena
		primaryStage.show();

	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
