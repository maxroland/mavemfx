package br.com.sba.ctrl;

import java.io.IOException;
import java.util.List;

import br.com.sba.facade.EditoraFacade;
import br.com.sba.model.Editora;
import br.com.sba.util.Campo;
import br.com.sba.util.Dialogo;
import br.com.sba.util.Filtro;
import br.com.sba.util.Grupo;
import br.com.sba.util.Mensagem;
import br.com.sba.util.Modulo;
import br.com.sba.util.Nota;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class EditoraController extends AnchorPane {

	private Editora editora;
    private List<Editora> listaEditoras;
    private EditoraFacade editoraFacade;
    private int idEditora = 0;

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
    private TextArea txtDescricao;

    @FXML
    private AnchorPane telaEdicao;

    @FXML
    private TableView<Editora> tbEditora;

    @FXML
    private TableColumn<Editora, Number> colId;

    @FXML
    private TableColumn<Editora, String> colNome;

    @FXML
    private Button btSalvar;

    @FXML
    private Button btEditar;

    @FXML
    private Button btExcluir;

    @FXML
    private Label legenda;

    public EditoraController() throws IOException{
        try {
            FXMLLoader fxml = new FXMLLoader(getClass().getResource("/br/com/sba/view/editora.fxml"));

            fxml.setRoot(this);
            fxml.setController(this);
            fxml.load();

        } catch (IOException ex) {
            Mensagem.erro("Erro ao carregar tela Editoras! \n" + ex);
        }
    }
    
    @FXML
    public void initialize() {
        screenRecords(null);

        Grupo.notEmpty(menu);
        loadData();

        txtPesquisar.textProperty().addListener((obs, old, novo) -> {
            filtro(novo, FXCollections.observableArrayList(listaEditoras));
        });
    }
    
    @FXML
    void screenRecords(ActionEvent event) {
        config("Cadastrar Editora", "Campos obrigatórios", 0);
        Modulo.visualizacao(true, telaCadastro, btSalvar);
        toClean();
    }

    @FXML
    void screenEdition(ActionEvent event) {
        config("Editar Editora", "Quantidade de editoras encontradas", 1);
        Modulo.visualizacao(true, telaEdicao, btEditar, txtPesquisar);
        table();
    }

    @FXML
    void screenDeletion(ActionEvent event) {
        config("Excluir Editora", "Quantidade de editoras encontradas", 2);
        Modulo.visualizacao(true, telaEdicao, btExcluir, txtPesquisar);
        table();
    }

    @FXML
    void onSave(ActionEvent event) {
        boolean vazio = ValidationFields.checkEmptyFields(txtNome);
        resetEditora();
        editora.setNome(txtNome.getText());

        if (vazio) {
            Mensagem.alerta("Preencher campos vazios!");
        }else if(idEditora ==0 && getEditoraFacade().hasEditora(editora.getNome())){
            Mensagem.alerta("Editora já cadastrada!");
        } else {
            setEditora(new Editora(idEditora, editora.getNome()));
            if (idEditora == 0) {
               getEditoraFacade().createEditora(getEditora());
                Mensagem.info("Editora cadastrada com sucesso!");
            } else {
                getEditoraFacade().updateEditora(getEditora());
                Mensagem.info("Editora atualizada com sucesso!");
            }
            setEditora(null);
            loadData();
            screenRecords(null);

            toClean();
        }
    }

    @FXML
    void onEdit(ActionEvent event) {
        try {
        	resetEditora();
            setEditora(tbEditora.getSelectionModel().getSelectedItem());
            screenRecords(null);

            txtNome.setText(editora.getNome());
            lbTitulo.setText("Atualizar Editora ");
            menu.selectToggle(menu.getToggles().get(1));
            idEditora = editora.getId();

        } catch (NullPointerException ex) {
            Nota.alerta("Selecione uma Editora na tabela para edição!");
        }
    }

    @FXML
    void onDelete(ActionEvent event) {
        try {
        	resetEditora();
            setEditora(tbEditora.getSelectionModel().getSelectedItem());
            Dialogo.Resposta response = Mensagem.confirmar("Excluir Editora " +editora.getNome() + " ?");

            if (response == Dialogo.Resposta.YES) {
                getEditoraFacade().deleteEditora(editora);
                loadData();
                table();
            }

            tbEditora.getSelectionModel().clearSelection();

        } catch (NullPointerException ex) {
            Mensagem.alerta("Selecione Editora na tabela para exclusão!");
        }
    }



    /**
     * Configurações de tela, titulos e exibição de telas e menus
     */
    private void config(String tituloTela, String msg, int grupoMenu) {
        lbTitulo.setText(tituloTela);
        Modulo.visualizacao(false, btExcluir, btSalvar, btEditar, telaCadastro, telaEdicao, txtPesquisar);

        legenda.setText(msg);//mensagem legenda
        tbEditora.getSelectionModel().clearSelection();
        menu.selectToggle(menu.getToggles().get(grupoMenu));

        idEditora = 0;
    }

    /**
     * Mapear dados objetos para inserção dos dados na tabela
     */
    private void table() {
        ObservableList<Editora> data = FXCollections.observableArrayList(listaEditoras);

        colId.setCellValueFactory(new PropertyValueFactory<>("id")); //para análise de estudo essa classe tera mixed mode
        colNome.setCellValueFactory(cell -> cell.getValue().nomeProperty());

        tbEditora.setItems(data);
    }

    /**
     * Campo de pesquisar para filtrar dados na tabela
     */
    private void filtro(String valor, ObservableList<Editora> listaEditora) {

        FilteredList<Editora> dadosFiltrados = new FilteredList<>(listaEditora, editora -> true);
        dadosFiltrados.setPredicate(editora -> {

            if (valor == null || valor.isEmpty()) {
                return true;
            } else if (editora.getNome().toLowerCase().startsWith(valor.toLowerCase())) {
                return true;
            }

            return false;
        });

        SortedList<Editora> dadosOrdenados = new SortedList<>(dadosFiltrados);
        dadosOrdenados.comparatorProperty().bind(tbEditora.comparatorProperty());
        Filtro.mensagem(legenda, dadosOrdenados.size(), "Quantidade de editoras encontradas");

        tbEditora.setItems(dadosOrdenados);
    }

    /**
     * Limpar campos textfield cadastro de coleções
     */
    private void toClean() {
        Campo.limpar(txtNome);
    }
    
	public EditoraFacade getEditoraFacade() {
		if(editoraFacade ==null){
			editoraFacade = new EditoraFacade();
		}
		return editoraFacade;
	}

	public Editora getEditora() {
		if (editora == null) {
			editora = new Editora();
		}
		return editora;
	}

	public void setEditora(Editora editora) {
		this.editora = editora;
	}

	public List<Editora> loadData() {
			listaEditoras = getEditoraFacade().listAll();
		return listaEditoras ;
	}

	public void resetEditora() {
		editora = new Editora();
	}    

}
