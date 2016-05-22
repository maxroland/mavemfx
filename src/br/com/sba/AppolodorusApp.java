package br.com.sba;
	
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

//A inicialização da Aplicação e telas se dá somente pelo LoginApp 
public class AppolodorusApp extends LoginApp{
    private static Scene cena;
    private static AnchorPane page;

    private Screen screen = Screen.getPrimary();
    private Rectangle2D windows = screen.getVisualBounds();
	
  //Construtor parametros alteram o tamanho da tela de acordo com a configuração do terminal
    public AppolodorusApp(){
    	palco = new Stage(); //será removido quando retirado main
        palco.setX(windows.getMinX());
        palco.setY(windows.getMinY());
        palco.setWidth(windows.getWidth());
        palco.setHeight(windows.getHeight());
        palco.setResizable(true);
    };
    
    //Recebe cena da tela de login
	public void startByParent(Parent parent) {
		cena = new Scene(parent);
        palco.setScene(cena);
        palco.show();
	}
	
  
    @Override 
	public void start(Stage stage) throws IOException{
        page = FXMLLoader.load(getClass().getResource("/br/com/sba/view/app/app.fxml"));
		//adiciona a view fxml(page) na cena
        cena = new Scene(page);        
		//Icone no palco principal
        palco.getIcons().add(new Image("br/com/sba/img/icon/library-icon.png"));
		//Titulo do palco
        palco.setTitle("SBA - Sistema de Bibliotecas Appolodorus");
		//vincula a cena ao palco
        palco.setScene(cena);
		//apresenta a cena
        palco.show();
	}
	
	
	//Instruçãoo será retirada posteriormente aplicação terá apenas 1 modo de acesso start
	public static void main(String[] args) {
		launch(args);
	}
	
		
}