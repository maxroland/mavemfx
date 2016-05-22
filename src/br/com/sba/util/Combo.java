package br.com.sba.util;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.util.List;

/**
 * Classe auxiliar na população de combobox
 *
 * @author Angelica Leite
 */
public class Combo {

    private Combo() {
    }

    /**
     * Popular combos generico atravÃªs de uma coleção do tipo List
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public static void popular(ComboBox combo, List lista) {
        dados(combo, FXCollections.observableArrayList(lista));
    }

    /**
     * Popular combos generico atravÃªs um array de strings passados
     */
    @SuppressWarnings("rawtypes")
	public static void popular(ComboBox combo, String... itens) {
        dados(combo, FXCollections.observableArrayList(itens));
    }

    /**
     * Popular combos com dados informados ao combo
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private static void dados(ComboBox combo, ObservableList dados) {
        if (dados.isEmpty() || dados == null) {
            limpar(combo);
        } else {
            combo.setItems(dados);
            combo.getSelectionModel().selectFirst();
        }
    }

    /**
     * Limpar combo informado
     */
    private static void limpar(ComboBox<Object> combo) {
        combo.getItems().clear();
        combo.setPromptText("-- Registros nao encontrados --");
    }
}