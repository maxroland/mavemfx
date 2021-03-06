package br.com.sba.view.app;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import br.com.sba.AppolodorusApp;
import br.com.sba.LoginApp;
import br.com.sba.util.Animacao;
import br.com.sba.util.Grupo;
import br.com.sba.util.Modulo;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AppController implements Initializable{

    private static AppController instance;

    @FXML
    private AnchorPane boxConteudo;
    @FXML
    private VBox boxEmprestimo;
    @FXML
    private VBox boxVisitas;
    @FXML
    private VBox boxLocalizacao;
    @FXML
    private VBox boxGerenciamento;
    @FXML
    private VBox boxNotas;
    @FXML
    private VBox boxCatalogacao;
    @FXML
    private Label lbUser;
    @FXML
    private Label lbMensagem;
    @FXML
    private ToggleButton btSetor;
    @FXML
    private ToggleButton btVisitas;
    @FXML
    private ToggleButton btLocal;
    @FXML
    private ToggleButton btInstituicao;
    @FXML
    private ToggleButton btExcursao;
    @FXML
    private ToggleButton btCatalogar;
    @FXML
    private ToggleButton btLocalizacao;
    @FXML
    private ToggleButton btLocalizar;
    @FXML
    private ToggleButton btColecao;
    @FXML
    private ToggleButton btEmprestimos;
    @FXML
    private ToggleButton btEmprestimo;
    @FXML
    private ToggleButton btHistorico;
    @FXML
    private ToggleButton btItens;
    @FXML
    private ToggleButton btUtilitarios;
    @FXML
    private ToggleButton btOrganizacao;
    @FXML
    private ToggleButton btDesginacao;
    @FXML
    private ToggleButton btUsuarios;
    @FXML
    private ToggleButton btDevolucao;
    @FXML
    private ToggleButton btCatalogacao;
    @FXML
    private ToggleButton btEditora;
    @FXML
    private ToggleButton btLivro;    
    @FXML
    private ToggleButton btVisitantes;
    @FXML
    private ToggleButton btMovimentacao;
    @FXML
    private ToggleButton btIdentificacao;
    @FXML
    private ToggleButton btRelatorios;
    @FXML
    private ToggleButton btPesquisa;
    @FXML
    private ToggleButton btSeguranca;
    @FXML
    private ToggleGroup grupoLocalizacao;
    @FXML
    private ToggleGroup grupoGerenciamento;
    @FXML
    private ToggleGroup grupoMenus;
    @FXML
    private ToggleGroup grupoEmprestimo;
    @FXML
    private ToggleGroup grupoCatalogacao;
    @FXML
    private ToggleGroup grupoVisitantes;


    /**
     * Obter instancia do controler
     */
    public static AppController getInstance() {
        return instance;
    }

    public void setDataPane(Node node) {
        // Atualiza AnchorPane form(FXML) dependendo do submenu escolhido
        boxConteudo.getChildren().setAll(node);
    }

    @FXML
    void menuDashboard(MouseEvent event) {
        //Modulo.getDashboard(boxConteudo);
    }

    @FXML
    void menuVisitas(ActionEvent event) {
        submenus(btVisitas, boxVisitas, btVisitantes, btInstituicao, btExcursao);
    }

    @FXML
    void menuCatalogacao(ActionEvent event) {
        submenus(btCatalogacao, boxCatalogacao,  btEditora, btLivro);
    }

    @FXML
    void menuLocalizacao(ActionEvent event) {
        submenus(btLocalizacao, boxLocalizacao, btLocalizar, btOrganizacao, btSetor, btLocal);
    }

    @FXML
    void menuGerenciamento(ActionEvent event) {
        submenus(btUtilitarios, boxGerenciamento, btUsuarios);
    }

    @FXML
    void menuEmprestimo(ActionEvent event) {
        submenus(btEmprestimos, boxEmprestimo, btEmprestimo, btItens, btDevolucao, btHistorico);
    }

    @FXML
    void menuIdentificacao(ActionEvent event) {
        //Modulo.getIdentificacao(boxConteudo);
    }

    @FXML
    void menuMovimentacao(ActionEvent event) {
        //Modulo.getMovimentacao(boxConteudo);
    }

    @FXML
    void menuSeguranca(ActionEvent event) {
        //Modulo.getValidacao(boxConteudo);
    }

    @FXML
    void menuPesquisa(ActionEvent event) {
        //Modulo.getPesquisar(boxConteudo);
    }

    @FXML
    void menuRelatorios(ActionEvent event) {
        //Modulo.getRelatorio(boxConteudo);
    }

    @FXML
    void subVisitantes(ActionEvent event) {
        //Modulo.getVisitante(boxConteudo);
    }

    @FXML
    void subInstituicao(ActionEvent event) {
        //Modulo.getInstituicao(boxConteudo);
    }

    @FXML
    void subExcursao(ActionEvent event) {
        //Modulo.getExcursao(boxConteudo);
    }


    @FXML
    void subDesignacao(ActionEvent event) {
//        Modulo.getDesignacao(boxConteudo);
    }

    @FXML
    void subEditora(ActionEvent event) throws IOException {
    	Modulo.getEditora(boxConteudo);
    }

    @FXML
    void subLivro(ActionEvent event) throws IOException {
    	Modulo.getLivro(boxConteudo);
    }
    
    @FXML
    void subEmprestimo(ActionEvent event) {
//        Modulo.getEmprestimo(boxConteudo);
    }

    @FXML
    void subItensEmprestimo(ActionEvent event) {
//        Modulo.getItensEmprestimo(boxConteudo);
    }

    @FXML
    void subDevolucaoEmprestimo(ActionEvent event) {
//        Modulo.getDevolucao(boxConteudo);
    }

    @FXML
    void subHistoricoEmprestimo(ActionEvent event) {
//        Modulo.getHistorico(boxConteudo);
    }

    @FXML
    void subOrganizacao(ActionEvent event) throws IOException {
        Modulo.getOrganizacao(boxConteudo);
    }

    @FXML
    void subSetor(ActionEvent event) {
//        Modulo.getSetor(boxConteudo);
    }

    @FXML
    void subLocal(ActionEvent event) {
//        Modulo.getLocal(boxConteudo);
    }

    @FXML
    void subLocalizar(ActionEvent event) {
//        Modulo.getLocalizacao(boxConteudo);
    }

    @FXML
    void subUsuarios(ActionEvent event) throws IOException {
        Modulo.getUsuario(boxConteudo);
    }

    @FXML
    void siteMuseu(ActionEvent event) {
//        Link.endereco("http://museuid.com.br");
    }

    @FXML
    void menuSair(ActionEvent event) throws IOException {
        AppolodorusApp.palco.close();
        new LoginApp().start(new Stage());
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        Grupo.notEmpty(grupoMenus, grupoCatalogacao,grupoGerenciamento,grupoLocalizacao);//, grupoEmprestimo,   grupoVisitantes);//n�o permite grupos de menus com menus deselecionados
//        menuDashboard(null);
//          lbUser.setText("Ola, "+LoginAppController.login.toString());
	}
    public AnchorPane fadeAnimate(String url) throws IOException {
        AnchorPane v = (AnchorPane) FXMLLoader.load(getClass().getResource(url));
        FadeTransition ft = new FadeTransition(Duration.millis(1000));
        ft.setNode(v);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
        return v;
    }
    /**
     * Obter componente para exbição das notas
     */
    public VBox boxNotas() {
        return boxNotas;
    }

    /**
     * Obter componente para exibição dos modulos da aplicação
     */
    public AnchorPane getBoxConteudo() {
        return boxConteudo;
    }

    /**
     * Exibir e ocultar submenus
     */
    public void submenus(ToggleButton menu, VBox box, ToggleButton... submenus) {
        if (box.getChildren().isEmpty()) {
            box.getChildren().addAll(submenus);
            Animacao.fade(box);
            estilo(menu, "menu-grupo");
        } else {
            desativarSubmenus(box);
            estilo(menu, "menu-grupo-inativo");
        }
    }

    /**
     * Desativar e esconder todos submenus
     */
    public void desativarSubmenus(VBox... boxes) {
        for (VBox box : boxes) {
            box.getChildren().clear();
        }
    }

    /**
     * Aplicar estilo para mostrar/ocultar submenus
     */
    public void estilo(Node no, String estilo) {
        no.getStyleClass().remove(3);
        no.getStyleClass().add(estilo);
    }



}
