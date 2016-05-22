package br.com.sba.view.login;

import java.io.IOException;

import br.com.sba.AppolodorusApp;
import br.com.sba.facade.UsuarioFacade;
import br.com.sba.model.Usuario;
import br.com.sba.util.Campo;
import br.com.sba.util.StringUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginAppController{
	@FXML
	private TextField pwdSenha;
	@FXML
	private TextField txtLogin;
    @FXML
    private Label lblErroLogin;
	@FXML
	private Button btnEntrar;
	
	private Usuario usuario;
	
	private UsuarioFacade usuarioFacade;
	
	public static String login;
	
	@FXML
	private void initialize(){
		txtLogin.textProperty().addListener((observable, oldValue, newValue) -> {
		    lblErroLogin.setText("");
		});
		pwdSenha.textProperty().addListener((observable, oldValue, newValue) -> {
		    lblErroLogin.setText("");
		});
	}
	
	@FXML
	public void doLogin(ActionEvent event ) throws IOException {
		setUsuario(new Usuario(txtLogin.getText(), pwdSenha.getText()));
		
		if(validateInput()){
//			try{
				Usuario user = getUsuarioFacade().isValidLogin(usuario.getLogin(), usuario.getSenha());
				if(user!=null){
					login = usuario.getLogin();
					//Passa a responsabilidade do Palco através do objeto Parent a classe AppolodorusApp					
					((Node) (event.getSource())).getScene().getWindow().hide();
					Parent parent = FXMLLoader.load(getClass().getResource("/br/com/sba/view/app/app.fxml"));
					new AppolodorusApp().startByParent(parent);
		        }				
				lblErroLogin.setText("Login inválido!");
				Campo.erroLogin(txtLogin);
//			}catch(Exception e){
//				lblErroLogin.setText("Falha na Conexão com Banco de Dados!");
//			}
		}
	}

	
	public boolean validateInput(){
		lblErroLogin.setText("");
		String errorMessage = validateForm();
		if (!errorMessage.isEmpty()) {
			showValidationErrorAccess(errorMessage);
			return false;
		}
		return true;
	}
	
	private String validateForm() {
		StringBuilder errorMessage = new StringBuilder();

		if (StringUtils.isEmpty(usuario.getLogin())) {
			errorMessage.append("Preencha o login").append(StringUtils.newLine());
		}

		if (StringUtils.isEmpty(usuario.getSenha())) {
			errorMessage.append("Preencha a senha").append(StringUtils.newLine());
		}

		return errorMessage.toString();
	}
	
	private void showValidationErrorAccess(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Erro de Acesso");
		alert.setHeaderText("Campos obrigatórios não preenchidos!");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public UsuarioFacade getUsuarioFacade() {
		if(usuarioFacade ==null){
			usuarioFacade = new UsuarioFacade();
		}
		return usuarioFacade;
	}
	
	public Usuario getUsuario() {
		if (usuario == null) {
			usuario = new Usuario();
		}
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void resetUsuario() {
		usuario = new Usuario();
	}
	

}
