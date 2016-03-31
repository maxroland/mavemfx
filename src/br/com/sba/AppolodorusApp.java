package br.com.sba;
	
import java.io.IOException;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.fxml.FXMLLoader;


public class AppolodorusApp extends Application {
	
	public static EntityManagerFactory emf;
	
	@Override
	public void start(Stage primaryStage) throws IOException{
		
		emf = Persistence.createEntityManagerFactory("AppolodorusPU");
		
		//carrega o form principal Pane (root)
		Pane root = (AnchorPane)FXMLLoader.load(getClass().getResource("view/Login.fxml"));
		//Scene scene = new Scene(root,400,400);
		//adiciona o form principal na cena
		Scene scene = new Scene(root);
		//scene.getStylesheets().add(getClass().getResource("sba.css").toExternalForm());
		//Desabilita alteração de tamanho da tela
		primaryStage.setResizable(false);
		//vincula a cena ao palco
		primaryStage.setScene(scene);
		//apresenta a cena no palco
		primaryStage.show();
		
        //fecha o emf ao encerrar o programa
        primaryStage.setOnCloseRequest(e ->{
        	emf.close();
        	Platform.exit();
        	System.exit(0);
        });
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
