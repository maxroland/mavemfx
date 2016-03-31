package br.com.sba.ctrl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sba.AppolodorusApp;
import br.com.sba.model.Usuario;


public class LoginController implements Initializable{
	@FXML
	private TextField txtSenha;
	@FXML
	private TextField txtLogin;
    @FXML
    private Label lblStatus;
	@FXML
	private Button btnEntrar;

	EntityManager em;
	Usuario usuario;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
				
	}
	
	// Event Listener on Button[#btnEntrar].onAction
	@FXML
	public void onEnter(ActionEvent event ) throws Exception{
		//validateInput();
		//validateLogon();
//		boolean acesso = false;
		String userLogin = txtLogin.getText();
		String userSenha = txtSenha.getText();
		
		em=AppolodorusApp.emf.createEntityManager();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha =:senha");
		q.setParameter("login", userLogin);
		q.setParameter("senha", userSenha);
		//try{
			usuario = (Usuario) q.getSingleResult();
			if(userLogin.equalsIgnoreCase(usuario.getLogin()) && userSenha.equals(usuario.getSenha())){
				lblStatus.setText("Status: Login realizado!");
//				return true;
			}
			else{
				lblStatus.setText("Status: Usuário ou senha incorretos!");
			};
//		}catch(Exception e){
//			return false;
//		}		
	}
	
//	public void validateInput(){
//		String errorMessage = validateForm();
//		if (!errorMessage.isEmpty()) {
//			showValidationError(errorMessage);
//			return;
//		}
//	}
//	
//	private String validateForm() {
//		StringBuilder errorMessage = new StringBuilder();
//
//		if (StringUtils.isEmpty(currentSenhaServico.getLogin())) {
//			errorMessage.append("Preencha o login").append(StringUtils.newLine());
//		}
//
//		if (StringUtils.isEmpty(currentSenhaServico.getSenha())) {
//			errorMessage.append("Preencha a senha").append(StringUtils.newLine());
//		}
//
//		return errorMessage.toString();
//	}
	
	public boolean validateLogon(){
		boolean acesso = false;
		String userLogin = txtLogin.getText();
		String userSenha = txtSenha.getText();
		
		em=AppolodorusApp.emf.createEntityManager();
		Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha =:senha");
		q.setParameter("login", userLogin);
		q.setParameter("senha", userSenha);
		try{
			usuario = (Usuario) q.getSingleResult();
			if(userLogin.equalsIgnoreCase(usuario.getLogin()) && userSenha.equals(usuario.getSenha())){
				lblStatus.setText("Status: Login realizado!");
				return true;
			}
			else{
				lblStatus.setText("Status: Usuário ou senha incorretos!");
			};
		}catch(Exception e){
			return false;
		}
		
		return acesso;
		
	}

}
