package com.maxroland.mavenfx;

import com.maxroland.mavenfx.controller.PersonaJpaController;
import com.maxroland.mavenfx.modelo.Persona;
import com.maxroland.mavenfx.utils.EntityMan;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        listarPersona();
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public void listarPersona(){
        PersonaJpaController perso;
        List<Persona>listado;
        try {
            perso=new PersonaJpaController(EntityMan.getInstance());
            listado=perso.findPersonaEntities();
            for(Persona p:listado){
            
                System.out.println(""+p.getApellido());
            }
            
        } catch (Exception e) {
            System.out.println("Error en el modulo listar personas");
        }
    
    }

}
