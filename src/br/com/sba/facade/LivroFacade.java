package br.com.sba.facade;

import java.io.Serializable;
import java.util.List;

import br.com.sba.dao.EditoraDAO;
import br.com.sba.dao.LivroDAO;
import br.com.sba.model.Editora;
import br.com.sba.model.Livro;

public class LivroFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private LivroDAO livroDAO = new LivroDAO();
	private EditoraDAO editoraDAO = new EditoraDAO();

	public void createLivro(Livro livro) {
		livroDAO.beginTransaction();
		livroDAO.save(livro);
		livroDAO.commitAndCloseTransaction();
	}

	public void updateLivro(Livro livro) {
		livroDAO.beginTransaction();
		Livro persistedLivro = livroDAO.find(livro.getId());
		persistedLivro.setAutor(livro.getAutor());
		persistedLivro.setIsbn(livro.getIsbn());
		persistedLivro.setEditora(livro.getEditora());
		persistedLivro.setNome(livro.getNome());
		livroDAO.update(persistedLivro);
		livroDAO.commitAndCloseTransaction();
	}

	public Livro findLivro(int Id) {
		livroDAO.beginTransaction();
		Livro livro = livroDAO.find(Id);
		livroDAO.closeTransaction();
		return livro;
	}

	@SuppressWarnings("unchecked")
	public List<Livro> listAll() {
		livroDAO.beginTransaction();
		List<Livro> result = livroDAO.findAll();
		livroDAO.closeTransaction();
		return result;
	}

	public void deleteLivro(Livro livro) {
		livroDAO.beginTransaction();
		Livro persistedLivro = livroDAO.findReferenceOnly(livro.getId());
		livroDAO.delete(persistedLivro);
		livroDAO.commitAndCloseTransaction();
	}
	
	public boolean hasLivro(String isbn){
		livroDAO.beginTransaction();
		Livro livro = livroDAO.findLivroByIsbn(isbn);
		livroDAO.closeTransaction();
		if(livro==null){
			return false;
		}
		return true; 
	}
	
	@SuppressWarnings("unchecked")
	public List<Editora> listAllEditoras() {
		editoraDAO.beginTransaction();
		List<Editora> result = editoraDAO.findAll();
		editoraDAO.closeTransaction();
		return result;
	}
}