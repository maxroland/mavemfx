package br.com.sba.ctrl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sba.model.Editora;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author 
 */
public class EditoraListController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button btnNew;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private TableView<Editora> tbl;
    
    @FXML
    private TableColumn<Editora, Number> id;

    @FXML
    private TableColumn<Editora, String> nameEditora;
	
	ObservableList<Editora> data;
	
    EntityManager em;
	
	
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	//carrega dados do BD para tabela
    	loadData();
    	
    }    


	@FXML
    void onDelete() {
    	int row = tbl.getSelectionModel().getSelectedIndex();
    	if(row<0)return;
    	
    	//em = AppolodorusApp.emf.createEntityManager();
    	Query q = em.createQuery("SELECT e FROM Editora e WHERE e.ideditora = :ideditora");
    	q.setParameter("ideditora",data.get(row).getIdeditora());
    	Editora c = (Editora) q.getSingleResult();
    	em.getTransaction().begin();
    	em.remove(c);
    	em.getTransaction().commit();
    	em.close();
    	loadData();
    }

    @FXML
    void onEdit() throws IOException {
    	int row = tbl.getSelectionModel().getSelectedIndex();
    	if(row < 0 ) return;
    	Stage stage =  new Stage();
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/br/com/sba/view/EditoraFrm.fxml" ) );
        VBox mainPane = (VBox) loader.load();
        // add main form into the scene
        Scene scene = new Scene(mainPane);
        
        stage.setTitle("Atualizar Editora");
        stage.setScene(scene);   	
        stage.initStyle(StageStyle.UNDECORATED);
        disablemenu(true);
        EditoraController ctrl =  loader.getController();
        ctrl.setEditora(data.get(row).getIdeditora());
        fadeAnimate(mainPane);
        stage.showAndWait();
        disablemenu(false);
        loadData();
    }
    
    @FXML
    void onNew() throws IOException {
    	Stage stage =  new Stage();
        VBox mainPane = (VBox) FXMLLoader.load( getClass().getResource("/br/com/sba/view/EditoraFrm.fxml" ) );
        // add main form into the scene
        Scene scene = new Scene(mainPane);
        
        stage.setTitle("Adicionar Nova Editora");
        stage.setScene(scene);   	
        stage.initStyle(StageStyle.UNDECORATED);
        disablemenu(true);
        fadeAnimate(mainPane);
        stage.showAndWait();
        disablemenu(false);
        loadData();
    }
    
    private void loadData(){
        //em = AppolodorusApp.emf.createEntityManager();
		@SuppressWarnings("unchecked")
		List<Editora> lst = em.createQuery("SELECT e FROM Editora e").getResultList();
        data = FXCollections.observableArrayList();
        		
        
        for(Editora lst1 : lst){
        	data.add(new Editora(lst1.getIdeditora(),lst1.getNomeeditora()));
        }
        
        em.close();
        tbl.setItems(data);
        tbl.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        
        id.setCellValueFactory(cell -> cell.getValue().ideditoraProperty());
        nameEditora.setCellValueFactory(cell -> cell.getValue().nomeeditoraProperty());

    }
    
	private void disablemenu(boolean b) {
		btnNew.setDisable(b);
		btnEdit.setDisable(b);
		btnDelete.setDisable(b);
		tbl.setVisible(!b);
		
	}
	
    public VBox fadeAnimate(VBox v)  {
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return v;
    }
        
}
