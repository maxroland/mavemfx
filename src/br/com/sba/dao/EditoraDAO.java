package br.com.sba.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.sba.model.Editora;


public class EditoraDAO extends GenericDAO<Editora> {

	private static final long serialVersionUID = 1L;

	public EditoraDAO() {
		super(Editora.class);
	}

    public Editora findByName(String nome){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("nome", nome);     
 
        return super.findOneResult(Editora.FIND_BY_NAME, parameters);
    }
    
	public void delete(Editora editora) {
		super.delete(editora.getId());
	}


}