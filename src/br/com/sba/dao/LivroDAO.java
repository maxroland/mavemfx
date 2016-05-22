package br.com.sba.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.sba.model.Livro;


public class LivroDAO extends GenericDAO<Livro> {

	private static final long serialVersionUID = 1L;

	public LivroDAO() {
		super(Livro.class);
	}
    
    public Livro findLivroByIsbn(String isbn) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("isbn", isbn);     
 
        return super.findOneResult(Livro.FIND_BY_ISBN, parameters);
    }
    
	public void delete(Livro livro) {
		super.delete(livro.getId());
	}


}