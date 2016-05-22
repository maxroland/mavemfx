package br.com.sba.ctrl;

import java.io.IOException;
import java.util.List;

import br.com.sba.facade.OrganizacaoFacade;
import br.com.sba.model.Organizacao;
import br.com.sba.util.Campo;
import br.com.sba.util.Dialogo;
import br.com.sba.util.Filtro;
import br.com.sba.util.Grupo;
import br.com.sba.util.Mensagem;
import br.com.sba.util.Modulo;
import br.com.sba.util.ValidationFields;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class OrganizacaoController extends AnchorPane {

	private Organizacao organizacao;
    private List<Organizacao> listaOrganizacoes;
    private OrganizacaoFacade organizacaoFacade;
    private int idOrganizacao = 0;

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
    private TextField txtCnpj;

    @FXML
    private TextField txtRazaoSocial;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtCep;

    @FXML
    private AnchorPane telaEdicao;

    @FXML
    private TableView<Organizacao> tbOrganizacao;

    @FXML
    private TableColumn<Organizacao, Number> colId;

    @FXML
    private TableColumn<Organizacao, String> colNome;

    @FXML
    private TableColumn<Organizacao, String> colCnpj;

    @FXML
    private TableColumn<Organizacao, String> colRazaoSocial;

    @FXML
    private TableColumn<Organizacao, String> colEndereco;

    @FXML
    private TableColumn<Organizacao, String> colCep;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btEditar;

    @FXML
    private Button btExcluir;

    @FXML
    private Label legenda;
    
    public OrganizacaoController() throws IOException{
//        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/br/com/sba/view/organizacao.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

//        } catch (IOException ex) {
//            Mensagem.erro("Erro ao carregar tela pesquisar organizacao da organização! \n" + ex);
//        }
    }

    @FXML
    public void initialize() {
        telaCadastro(null);

        Grupo.notEmpty(menu);
        loadData();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaOrganizacoes));
        });
    }

    @FXML
    void telaCadastro(ActionEvent event) {
        config("Cadastrar Organização", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        limpar();
    }

    @FXML
    void telaEdicao(ActionEvent event) {
        config("Editar Organização", "Quantidade de organizações encontrados", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        tabela();
    }

    @FXML
    void telaExcluir(ActionEvent event) {
        config("Excluir Organização", "Quantidade de organizações encontrados", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        tabela();
    }

    @FXML
    void salvar(ActionEvent event) {
        boolean vazio = ValidationFields.checkEmptyFields(txtNome, txtRazaoSocial,txtEndereco, txtCnpj,txtCep);
        resetOrganizacao();
        organizacao.setNome(txtNome.getText());
        organizacao.setRazaoSocial(txtRazaoSocial.getText());
        organizacao.setCnpj(txtCnpj.getText());
        organizacao.setEndereco(txtEndereco.getText());
        organizacao.setCep(txtCep.getText());

        if (vazio) {
            Mensagem.alerta("Preencher campos vazios!");
        } else {
            setOrganizacao(
            		new Organizacao(idOrganizacao, organizacao.getNome(), organizacao.getRazaoSocial(), 
            						organizacao.getCnpj(),organizacao.getEndereco(), organizacao.getCep()));
            if (idOrganizacao == 0) {
                getOrganizacaoFacade().createOrganizacao(getOrganizacao());
                Mensagem.info("Organização cadastrada com sucesso!");
            } else {
            	getOrganizacaoFacade().updateOrganizacao(getOrganizacao());
                Mensagem.info("Organização atualizada com sucesso!");
            }
            setOrganizacao(null);
            telaCadastro(null);
            loadData();
            limpar();
        }
    }

    @FXML
    void editar(ActionEvent event) {
        try {
        	resetOrganizacao();
            setOrganizacao(tbOrganizacao.getSelectionModel().getSelectedItem());

            telaCadastro(null);

            txtNome.setText(organizacao.getNome());
            txtCnpj.setText(organizacao.getCnpj());
            txtRazaoSocial.setText(organizacao.getRazaoSocial());
            txtEndereco.setText(organizacao.getEndereco());
            txtCep.setText(organizacao.getCep());
            

            lbTitulo.setText("Editar Organização");
            menu.selectToggle(menu.getToggles().get(1));

            idOrganizacao = organizacao.getId();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione um organização na tabela para edição!");
        }
    }

    @FXML
    void excluir(ActionEvent event) {
        try {
        	resetOrganizacao();
            setOrganizacao(tbOrganizacao.getSelectionModel().getSelectedItem());

            Dialogo.Resposta response = Mensagem.confirmar("Excluir organização " + organizacao.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                getOrganizacaoFacade().deleteOrganizacao(getOrganizacao());
                loadData();
                tabela();
            }

            tbOrganizacao.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione organização na tabela para exclusão!");
        }
    }


    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbOrganizacao.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idOrganizacao = 0;
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void tabela() {
        ObservableList<Organizacao> data = FXCollections.observableArrayList(listaOrganizacoes);

        colId.setCellValueFactory(cell-> cell.getValue().idProperty());
        colNome.setCellValueFactory(cell-> cell.getValue().nomeProperty());
        colCnpj.setCellValueFactory(cell-> cell.getValue().cnpjProperty());
        colEndereco.setCellValueFactory(cell-> cell.getValue().enderecoProperty());        
        colCep.setCellValueFactory(cell-> cell.getValue().cepProperty());
        colRazaoSocial.setCellValueFactory(cell-> cell.getValue().razaoSocialProperty());        


        tbOrganizacao.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Organizacao> listaOrganizacao) {

        FilteredList<Organizacao> dadosFiltrados = new FilteredList<>(listaOrganizacao, organizacao -> true);
        dadosFiltrados.setPredicate(organizacao -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (organizacao.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (organizacao.getRazaoSocial().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            } else if (organizacao.getCnpj().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }
            return false;
        });

        SortedList<Organizacao> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbOrganizacao.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de organizações encontradas");

        tbOrganizacao.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void limpar() {
        Campo.limpar(txtNome, txtCnpj, txtCep, txtRazaoSocial,  txtEndereco);
    }
    
	public OrganizacaoFacade getOrganizacaoFacade() {
		if(organizacaoFacade ==null){
			organizacaoFacade = new OrganizacaoFacade();
		}
		return organizacaoFacade;
	}

	public Organizacao getOrganizacao() {
		if (organizacao == null) {
			organizacao = new Organizacao();
		}
		return organizacao;
	}

	public void setOrganizacao(Organizacao organizacao) {
		this.organizacao = organizacao;
	}

	public List<Organizacao> loadData() {
//		if (organizacao == null) {
			listaOrganizacoes = getOrganizacaoFacade().listAll();
//		}
		return listaOrganizacoes ;
	}


	public void resetOrganizacao() {
		organizacao = new Organizacao();
	}

}
