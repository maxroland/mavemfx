package br.com.sba.ctrl;

import java.io.IOException;
import java.util.List;

import br.com.sba.facade.UsuarioFacade;
import br.com.sba.model.TipoUsuario;
import br.com.sba.model.Usuario;
import br.com.sba.util.Campo;
import br.com.sba.util.Criptografia;
import br.com.sba.util.Dialogo;
import br.com.sba.util.Filtro;
import br.com.sba.util.Grupo;
import br.com.sba.util.Mensagem;
import br.com.sba.util.Modulo;
import br.com.sba.util.ValidationFields;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class UsuarioController extends AnchorPane {

	private Usuario usuario;
	private List<Usuario> listaUsuarios;
	private UsuarioFacade usuarioFacade;
	private int idUsuario = 0;

	@FXML
	private Label lbTitulo;

	@FXML
	private TextField txtPesquisar;

	@FXML
	private ToggleGroup menu;

	@FXML
	private GridPane telaCadastro;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtLogin;

	@FXML
	private TextField txtEmail;

	@FXML
	private PasswordField txtSenha;

	@FXML
	private PasswordField txtConfirmarSenha;

	@FXML
	private ComboBox<?> cbStatus;

	@FXML
	private ComboBox<?> cbPermissaoUsuario;

	@FXML
	private TextField txtEndereco;

	@FXML
	private TextField txtCep;

	@FXML
	private TextField txtCpf;

	@FXML
	private ComboBox<TipoUsuario> cbTipo;

	@FXML
	private AnchorPane telaEdicao;

	@FXML
	private TableView<Usuario> tbUsuario;

	@FXML
	private TableColumn<Usuario, Number> colId;

	@FXML
	private TableColumn<Usuario, String> colNome;

	@FXML
	private TableColumn<Usuario, String> colLogin;

	@FXML
	private TableColumn<Usuario, String> colEmail;

	@FXML
	private TableColumn<Usuario, String> colTipo;

	@FXML
	private TableColumn<Usuario, String> colEndereco;

	@FXML
	private TableColumn<Usuario, String> colCep;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btEditar;

	@FXML
	private Button btExcluir;

	@FXML
	private Label legenda;

	public UsuarioController() {
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/br/com/sba/view/usuario.fxml"));

			fxml.setRoot(this);
			fxml.setController(this);
			fxml.load();
		} catch (IOException ex) {
			Mensagem.erro("Erro ao carregar tela do usuario! \n" + ex);
		}
	}

	@FXML
	public void initialize() {
		screenRecords(null);

		Grupo.notEmpty(menu);
		loadData();
		combos();
		txtPesquisar.textProperty().addListener((obs, old, novo) -> {
			filtro(novo, FXCollections.observableArrayList(listaUsuarios));
		});
	}

	@FXML
	void screenRecords(ActionEvent event) {
		config("Cadastrar Usuario", "Campos obrigatarios", 0);
		Modulo.visualizacao(true, telaCadastro, btSalvar);
		toClean();
	}

	@FXML
	void screenEdition(ActionEvent event) {
		config("Editar Usuario", "Quantidade de usuarios encontrados", 1);
		Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
		loadData();
		table();
	}

	@FXML
	void screenDeletion(ActionEvent event) {
		config("Excluir Usuario", "Quantidade de usuarios encontrados", 2);
		Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
		table();
	}

	@FXML
	void onSave(ActionEvent event) {
		boolean vazio = ValidationFields.checkEmptyFields(txtNome, txtLogin, txtSenha, txtConfirmarSenha, cbTipo);
		resetUsuario();
		usuario.setNome(txtNome.getText());
		usuario.setCpf(txtCpf.getText());
		usuario.setEmail(txtEmail.getText());
		usuario.setLogin(txtLogin.getText().replaceAll(" ", "").trim());
		String confirmar = txtConfirmarSenha.getText();
		usuario.setSenha(txtSenha.getText());
		usuario.setEndereco(txtEndereco.getText());
		usuario.setCep(txtCep.getText());
		usuario.setTipo(cbTipo.getSelectionModel().getSelectedIndex());

		if (vazio) {
			Mensagem.alerta("Preencher campos vazios!");
		} else if (!usuario.getSenha().equals(confirmar)) {
			Mensagem.alerta("Confirmação de senha inválida, verifique se senhas informadas são iguais!");
		} else if (idUsuario == 0 && getUsuarioFacade().hasLogin(usuario.getLogin())) {
			Mensagem.alerta("Login já cadastrado na base de dados!");
		} else {
			setUsuario(new Usuario(idUsuario, usuario.getNome(), usuario.getCpf(), usuario.getEmail(),
					usuario.getLogin(), Criptografia.converter(confirmar), usuario.getEndereco(), usuario.getCep(),
					usuario.getTipo()));
			if (idUsuario == 0) {
				getUsuarioFacade().createUsuario(getUsuario());
				Mensagem.info("Usuário cadastrado com sucesso!");
			} else {
				getUsuarioFacade().updateUsuario(getUsuario());
				Mensagem.info("Usuário atualizado com sucesso!");
			}
			setUsuario(null);
			screenRecords(null);
			loadData();
			toClean();
		}
	}

	@FXML
	void onEdit(ActionEvent event) {
		try {
			resetUsuario();
			setUsuario(tbUsuario.getSelectionModel().getSelectedItem());

			screenRecords(null);

			txtNome.setText(usuario.getNome());
			txtCpf.setText(usuario.getCpf());
			txtLogin.setText(usuario.getLogin());
			txtEmail.setText(usuario.getEmail());
			txtSenha.setText("");
			txtConfirmarSenha.setText("");
			txtEndereco.setText(usuario.getEndereco());
			cbTipo.getSelectionModel().select(usuario.getTipo());

			lbTitulo.setText("Editar Usuario");
			menu.selectToggle(menu.getToggles().get(1));

			idUsuario = usuario.getId();

		} catch (NullPointerException ex) {
			Mensagem.alerta("Selecione um usuario na tabela para edição!");
		}
	}

	@FXML
	void onDelete(ActionEvent event) {
		try {
			resetUsuario();
			setUsuario(tbUsuario.getSelectionModel().getSelectedItem());
			Dialogo.Resposta response = Mensagem.confirmar("Excluir usuario " + usuario.getNome() + " ?");

			if (response == Dialogo.Resposta.YES) {
				getUsuarioFacade().deleteUsuario(getUsuario());
				resetUsuario();
				loadData();
				table();
			}
			tbUsuario.getSelectionModel().clearSelection();
		} catch (NullPointerException ex) {
			Mensagem.alerta("Selecione usuario na tabela para exclusao!");
		}
	}

	/**
	 * Preencher combos tela
	 */
	private void combos() {
		cbTipo.setPromptText("--selecione--");
		cbTipo.getItems().setAll(TipoUsuario.values());
	}

	/**
	 * Configuraaaes de tela, titulos e exibiaao de telas e menus
	 */
	private void config(String tituloTela, String mensagem, int grupoMenu) {
		lbTitulo.setText(tituloTela);
		Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

		legenda.setText(mensagem);
		tbUsuario.getSelectionModel().clearSelection();
		menu.selectToggle(menu.getToggles().get(grupoMenu));

		idUsuario = 0;
	}

	/**
	 * Mapear dados objetos para inseraao dos dados na tabela
	 */
	private void table() {

		ObservableList<Usuario> data = FXCollections.observableArrayList(listaUsuarios);
		colId.setCellValueFactory(cell -> cell.getValue().idProperty());
		colLogin.setCellValueFactory(cell -> cell.getValue().loginProperty());
		colNome.setCellValueFactory(cell -> cell.getValue().nomeProperty());
		colTipo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> obj) {
						int indexTipo = obj.getValue().getTipo();
						TipoUsuario valueTipo = TipoUsuario.values()[indexTipo];
						return new SimpleStringProperty(valueTipo.toString());
					}
				});
		colEmail.setCellValueFactory(cell -> cell.getValue().emailProperty());
		colEndereco.setCellValueFactory(cell -> cell.getValue().enderecoProperty());

		tbUsuario.setItems(data);
	}

	/**
	 * Campo de pesquisar para filtrar dados na tabela
	 */
	private void filtro(String valor, ObservableList<Usuario> listaUsuario) {

		FilteredList<Usuario> dadosFiltrados = new FilteredList<>(listaUsuario, usuario -> true);
		dadosFiltrados.setPredicate(usuario -> {

			if (valor == null || valor.isEmpty()) {
				return true;
			} else if (usuario.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
				return true;
			} else if (usuario.getEmail().toLowerCase().startsWith(valor.toLowerCase())) {
				return true;
			} else if (usuario.getLogin().toLowerCase().startsWith(valor.toLowerCase())) {
				return true;
			}

			return false;
		});

		SortedList<Usuario> dadosOrdenados = new SortedList<>(dadosFiltrados);
		dadosOrdenados.comparatorProperty().bind(tbUsuario.comparatorProperty());
		Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de usuarios encontradas");

		tbUsuario.setItems(dadosOrdenados);
	}

	/**
	 * Limpar campos textfield cadastro de coleaaes
	 */
	private void toClean() {
		Campo.limpar(txtLogin, txtNome, txtSenha, txtEmail, txtCep, txtEndereco, txtConfirmarSenha, txtCpf);
		cbTipo.setPromptText("--selecione--");
	}

	public UsuarioFacade getUsuarioFacade() {
		if (usuarioFacade == null) {
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

	public List<Usuario> loadData() {
		// if (usuario == null) {
		listaUsuarios = getUsuarioFacade().listAll();
		// }
		return listaUsuarios;
	}

	public void resetUsuario() {
		usuario = new Usuario();
	}

}
