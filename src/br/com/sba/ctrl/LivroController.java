package br.com.sba.ctrl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import br.com.sba.facade.LivroFacade;
import br.com.sba.model.Editora;
import br.com.sba.model.Livro;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class LivroController extends AnchorPane {

	private Livro livro;
	private List<Livro> listaLivros;
	private LivroFacade livroFacade;
	private int idLivro = 0;

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
    private TextField txtAutor;

    @FXML
    private TextField txtIsbn;

    @FXML
    private ComboBox<Editora> cbEditora;

    @FXML
    private DatePicker dtpDataPublicacao;

    @FXML
    private AnchorPane telaEdicao;

    @FXML
    private TableView<Livro> tbLivro;

    @FXML
    private TableColumn<Livro, Number> colId;

    @FXML
    private TableColumn<Livro, String> colIsbn;

    @FXML
    private TableColumn<Livro, String> colNome;

    @FXML
    private TableColumn<Livro, String> colAutor;

    @FXML
    private TableColumn<Livro, Editora> colEditora;

    @FXML
    private TableColumn<Livro, Date> colDtPublicacao;    

    @FXML
    private Button btSalvar;

    @FXML
    private Button btEditar;

    @FXML
    private Button btExcluir;

    @FXML
    private Label legenda;

	public LivroController() throws IOException{
		try {
			FXMLLoader fxml = new FXMLLoader(getClass().getResource("/br/com/sba/view/livro.fxml"));

			fxml.setRoot(this);
			fxml.setController(this);
			fxml.load();

		} catch (IOException ex) {
			Mensagem.erro("Erro ao carregar tela Livros! \n" + ex);
		}
	}

	@FXML
	public void initialize() {
		screenRecords(null);

		Grupo.notEmpty(menu);
		loadData();
		combos();
		txtPesquisar.textProperty().addListener((obs, old, novo) -> {
			filtro(novo, FXCollections.observableArrayList(listaLivros));
		});
	}

	@FXML
	void screenRecords(ActionEvent event) {
		config("Cadastrar Livro", "Campos obrigatórios", 0);
		Modulo.visualizacao(true, telaCadastro, btSalvar);
		toClean();
	}

	@FXML
	void screenEdition(ActionEvent event) {
		config("Editar Livro", "Quantidade de livros encontradas", 1);
		Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
		table();
	}

	@FXML
	void screenDeletion(ActionEvent event) {
		config("Excluir Livro", "Quantidade de livros encontradas", 2);
		Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
		table();
	}

	@FXML
	void onSave(ActionEvent event) {
		boolean vazio = ValidationFields.checkEmptyFields(txtNome,txtAutor,txtIsbn,cbEditora);
		resetLivro();
		livro.setDataPublicacao(dtpDataPublicacao.getValue());
		livro.setIsbn(txtIsbn.getText());
		livro.setAutor(txtAutor.getText());
		livro.setNome(txtNome.getText());
		livro.setEditora(cbEditora.getValue());

		if (vazio) {
			Mensagem.alerta("Preencher campos vazios!");
		}else if(idLivro ==0 && getLivroFacade().hasLivro(livro.getIsbn())){
			Mensagem.alerta("Livro com ISBN já cadastrado!");
		} else {
			setLivro(new Livro(idLivro, livro.getNome(),livro.getAutor(),
					livro.getIsbn(),livro.getDataPublicacao(),livro.getEditora()));
			if (idLivro == 0) {
				getLivroFacade().createLivro(getLivro());
				Mensagem.info("Livro cadastrado com sucesso!");
			} else {
				getLivroFacade().updateLivro(getLivro());
				Mensagem.info("Livro atualizado com sucesso!");
			}
			setLivro(null);
			loadData();
			screenRecords(null);
			loadData();
			toClean();
		}
	}

	@FXML
	void onEdit(ActionEvent event) {
		try {
			resetLivro();
			setLivro(tbLivro.getSelectionModel().getSelectedItem());
			screenRecords(null);

			txtNome.setText(livro.getNome());
			txtAutor.setText(livro.getAutor());
			txtIsbn.setText(livro.getIsbn());
			cbEditora.setValue(livro.getEditora()); //return because Livro override tostring() method
			dtpDataPublicacao.setValue(livro.getDataPublicacao());
			lbTitulo.setText("Atualizar Livro ");
			menu.selectToggle(menu.getToggles().get(1));
			idLivro = livro.getId();

		} catch (NullPointerException ex) {
			Mensagem.alerta("Selecione um Livro na tabela para edição!");
		}
	}

	@FXML
	void onDelete(ActionEvent event) {
		try {
			resetLivro();
			setLivro(tbLivro.getSelectionModel().getSelectedItem());
			Dialogo.Resposta response = Mensagem.confirmar("Excluir Livro " + livro.getNome() + " ?");

			if (response == Dialogo.Resposta.YES) {
				getLivroFacade().deleteLivro(livro);
				setLivro(null);
				loadData();
				table();
			}

			tbLivro.getSelectionModel().clearSelection();

		} catch (NullPointerException ex) {
			Mensagem.alerta("Selecione Livro na tabela para exclusão!");
		}
	}


	/**
	 * Preencher combos tela
	 */
	private void combos() {
		cbEditora.setPromptText("--selecione--");
		cbEditora.setItems(FXCollections.observableArrayList(getLivroFacade().listAllEditoras()));
	}
	
	/**
	 * Configurações de tela, titulos e exibição de telas e menus
	 */
	private void config(String tituloTela, String msg, int grupoMenu) {
		lbTitulo.setText(tituloTela);
		Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

		legenda.setText(msg);//mensagem legenda
		tbLivro.getSelectionModel().clearSelection();
		menu.selectToggle(menu.getToggles().get(grupoMenu));

		idLivro = 0;
	}

	/**
	 * Mapear dados objetos para inserção dos dados na tabela
	 */
	private void table() {
		ObservableList<Livro> data = FXCollections.observableArrayList(listaLivros);

		colId.setCellValueFactory(cell -> cell.getValue().idProperty());
		colIsbn.setCellValueFactory(cell -> cell.getValue().isbnProperty());
		colNome.setCellValueFactory(cell -> cell.getValue().nomeProperty());
		colAutor.setCellValueFactory(cell -> cell.getValue().autorProperty());
		colEditora.setCellValueFactory(new PropertyValueFactory<Livro,Editora>("editora"));
		colDtPublicacao.setCellValueFactory(new PropertyValueFactory<Livro,Date>("dataPublicacao"));
		tbLivro.setItems(data);
	}

	/**
	 * Campo de pesquisar para filtrar dados na tabela
	 */
	private void filtro(String valor, ObservableList<Livro> listaLivro) {

		FilteredList<Livro> dadosFiltrados = new FilteredList<>(listaLivro, livro -> true);
		dadosFiltrados.setPredicate(livro -> {

			if (valor == null || valor.isEmpty()) {
				return true;
			} else if (livro.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
				return true;
			}else if (livro.getAutor().toLowerCase().startsWith(valor.toLowerCase())) {
				return true;
			}else if (livro.getIsbn().toLowerCase().startsWith(valor.toLowerCase())) {
				return true;
			}
			return false;
		});

		SortedList<Livro> dadosOrdenados = new SortedList<>(dadosFiltrados);
		dadosOrdenados.comparatorProperty().bind(tbLivro.comparatorProperty());
		Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de livros encontrados");

		tbLivro.setItems(dadosOrdenados);
	}

	/**
	 * Limpar campos textfield cadastro de coleções
	 */
	private void toClean() {
		Campo.limpar(txtNome,txtAutor,txtIsbn);
		cbEditora.setPromptText("--selecione--");
	}

	public LivroFacade getLivroFacade() {
		if(livroFacade ==null){
			livroFacade = new LivroFacade();
		}
		return livroFacade;
	}

	public Livro getLivro() {
		if (livro == null) {
			livro = new Livro();
		}
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Livro> loadData() {
		listaLivros = getLivroFacade().listAll();
		return listaLivros ;
	}
	

	public void resetLivro() {
		livro = new Livro();
	}    

}
