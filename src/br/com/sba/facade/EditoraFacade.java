package br.com.sba.facade;

import java.io.Serializable;
import java.util.List;

import br.com.sba.dao.EditoraDAO;
import br.com.sba.model.Editora;

public class EditoraFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private EditoraDAO editoraDAO = new EditoraDAO();

	public void createEditora(Editora editora) {
		editoraDAO.beginTransaction();
		editoraDAO.save(editora);
		editoraDAO.commitAndCloseTransaction();
	}

	public void updateEditora(Editora editora) {
		editoraDAO.beginTransaction();
		Editora persistedEditora = editoraDAO.find(editora.getId());
		persistedEditora.setNome(editora.getNome());
		editoraDAO.update(persistedEditora);
		editoraDAO.commitAndCloseTransaction();
	}

	public Editora findEditora(int Id) {
		editoraDAO.beginTransaction();
		Editora editora = editoraDAO.find(Id);
		editoraDAO.closeTransaction();
		return editora;
	}

	@SuppressWarnings("unchecked")
	public List<Editora> listAll() {
		editoraDAO.beginTransaction();
		List<Editora> result = editoraDAO.findAll();
		editoraDAO.closeTransaction();
		return result;
	}

	public void deleteEditora(Editora editora) {
		editoraDAO.beginTransaction();
		Editora persistedEditora = editoraDAO.findReferenceOnly(editora.getId());
		editoraDAO.delete(persistedEditora);
		editoraDAO.commitAndCloseTransaction();
	}
	
	public boolean hasEditora(String nome){
		editoraDAO.beginTransaction();
		Editora editora = editoraDAO.findByName(nome);
		editoraDAO.closeTransaction();
		if(editora==null){
			return false;
		}
		return true; 
	}
	
}