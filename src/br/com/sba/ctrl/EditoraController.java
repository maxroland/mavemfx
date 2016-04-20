package br.com.sba.ctrl;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import br.com.sba.model.Editora;
import javafx.event.ActionEvent;

public class EditoraController implements Initializable {
	@FXML
	private Button btnDone;

	@FXML
	private Button btnCancel;

	@FXML
    private TextField txtEditoraName;
    
	EntityManager em;
	
	Editora  editora;
	
	boolean editmode = false;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		  
		
	}	
	
	public void setEditora(int id){
		Query q= em.createQuery("SELECT e FROM Editora e where e.ideditora = :ideditora");
		q.setParameter("ideditora", new Integer(id));
		editora = (Editora) q.getSingleResult();
		txtEditoraName.setText(editora.getNomeeditora());
		editmode = true;
	}
	
	// Event Listener on Button[#onDone].onAction

	@FXML
	public void onDone(ActionEvent event) {
		if(!editmode){
			editora = new Editora();
		}
		editora.setNomeeditora(txtEditoraName.getText());
		em.getTransaction().begin();
		em.persist(editora);
		em.getTransaction().commit();
		em.close();
		onCancel();
	}
	
	// Event Listener on Button[#onCancel].onAction
	@FXML
	public void onCancel() {
		Stage s = (Stage) btnCancel.getScene().getWindow();
		s.close();
	}

}
