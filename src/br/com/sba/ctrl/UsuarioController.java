package br.com.sba.ctrl;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.NoResultException;


import br.com.sba.facade.UsuarioFacade;
import br.com.sba.model.TipoUsuario;
import br.com.sba.model.Usuario;
import br.com.sba.util.Campo;
import br.com.sba.util.Combo;
import br.com.sba.util.Criptografia;
import br.com.sba.util.Dialogo;
import br.com.sba.util.Filtro;
import br.com.sba.util.Grupo;
import br.com.sba.util.Mensagem;
import br.com.sba.util.Modulo;
import br.com.sba.util.Nota;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class UsuarioController extends AnchorPane{
    
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
    private ComboBox<String> cbTipoUsuario;

    @FXML
    private AnchorPane telaEdicao;

    @FXML
    private TableView<Usuario> tbUsuario;

    @FXML
    private TableColumn<Usuario, Number> colId;

    @FXML
    private TableColumn<Usuario, String > colNome;

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
    
    public UsuarioController(){
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/br/com/sba/view/usuario.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        }
        catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela do usuário! \n" + ex); 

        }
    }
    
    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        sincronizarBase();
        combos();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaUsuarios));
        });
    }
    
    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Usuário", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Usuário", "Quantidade de usuários encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        sincronizarBase();
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Usuário", "Quantidade de usuários encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {

        boolean vazio = Campo.noEmpty(txtNome, txtLogin, txtSenha, txtConfirmarSenha);
        resetUsuario();
        usuario.setNome(txtNome.getText());
        usuario.setCpf(txtCpf.getText());
        usuario.setEmail(txtEmail.getText());
        usuario.setLogin(txtLogin.getText().replaceAll(" ", "").trim());
        String confirmar = txtConfirmarSenha.getText();
        String senha = txtSenha.getText();
        usuario.setEndereco(txtEndereco.getText());
        usuario.setCep(txtCep.getText());
        usuario.setTipo(cbTipoUsuario.getValue());
        if (vazio ) {
           Mensagem.info("Preencher campos vazios!");
//        } else if (!senha.equals(confirmar)) {
//            Mensagem.info("Senha inválida, verifique se senhas são iguais!");
//        } else if (usuarioFacade.hasLogin(usuario.getLogin())) {
//            Nota.alerta("Login já cadastrado na base de dados!");
        } else {
            Usuario user = new Usuario(idUsuario, usuario.getNome(), usuario.getCpf(),usuario.getEmail(),usuario.getLogin(), senha,//Criptografia.converter(senha), 
            							usuario.getEndereco(), usuario.getCep(),usuario.getTipo());

            if (idUsuario == 0) {
                usuarioFacade.createUsuario(user);
                Mensagem.info("Usuário cadastrado com sucesso!");
            } else {
                usuarioFacade.updateUsuario(user);
                Mensagem.info("Usuário atualizado com sucesso!");
            }
            resetUsuario();
            telaCadastro(null);
            sincronizarBase();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
            Usuario user = tbUsuario.getSelectionModel().getSelectedItem();
            user.getClass();

            telaCadastro(null);

            txtNome.setText(user.getNome());
            txtCpf.setText(user.getCpf());
            txtLogin.setText(user.getLogin());
            txtEmail.setText(user.getEmail());
            txtSenha.setText("");
            txtEndereco.setText(user.getEndereco());
            cbTipoUsuario.setValue(user.getTipo());
            txtConfirmarSenha.setText("");
            lbTitulo.setText("Editar Usuário");
            menu.selectToggle(menu.getToggles().get(1));

            idUsuario = user.getIdusuario();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione um usuário na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
            Usuario usuario = tbUsuario.getSelectionModel().getSelectedItem();

            Dialogo.Resposta response = Mensagem.confirmar("Excluir usuário " + usuario.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
            	getUsuarioFacade().deleteUsuario(usuario);
                sincronizarBase();
                tabela();
            }

            tbUsuario.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione usuário na tabela para exclusão!");
        }
    }



    /**
     * Preencher combos tela
     */
    private void combos() {
        Combo.popular(cbStatus, "Ativo", "Inativo");
        Combo.popular(cbTipoUsuario, "Administrador", "Leitor");
//        Combo.popular(cbPermissaoUsuario, ControleDAO.getBanco().getUsuarioDAO().usuariosTipo());
    }

    /**
     * Configurações de tela, titulos e exibição de telas e menus
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
     * Sincronizar dados com banco de dados
     */
    private void sincronizarBase() {
        getAllUsuarios();
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {

        ObservableList<Usuario> data = FXCollections.observableArrayList(listaUsuarios);

        colId.setCellValueFactory(new PropertyValueFactory<>("idusuario"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        //colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuario, String> obj) {
                return new SimpleStringProperty(obj.getValue().getTipo());
            }
        });
        
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
//        colStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {
//            @Override
//            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuario, String> obj) {
//                return new SimpleStringProperty(obj.getValue().isAtivo());
//            }
//        });

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
//            } else if (usuario.getTipoUsuario().getNome().toLowerCase().startsWith(valor.toLowerCase())) {
//                return true;
            }

            return false;
        });

        SortedList<Usuario> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbUsuario.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de usuários encontradas");

        tbUsuario.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtConfirmarSenha, txtLogin, txtNome, txtSenha, txtEmail,txtCep,txtEndereco,txtConfirmarSenha,txtCpf);
        cbTipoUsuario.setValue("");
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

	public List<Usuario> getAllUsuarios() {
		if (usuario == null) {
			loadUsuarios();
		}

		return listaUsuarios ;
	}

	private void loadUsuarios() {
		listaUsuarios = getUsuarioFacade().listAll();
	}

	public void resetUsuario() {
		usuario = new Usuario();
	}


}
