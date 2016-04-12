package br.com.sba.dao;

import java.util.HashMap;
import java.util.Map;


import br.com.sba.model.Usuario;

public class UsuarioDAO extends GenericDAO<Usuario> {

	private static final long serialVersionUID = 1L;

	public UsuarioDAO() {
		super(Usuario.class);
	}


    public Usuario findUserByLogin(String login){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("login", login);     
 
        return super.findOneResult(Usuario.FIND_BY_LOGIN, parameters);
    }
    
	public void delete(Usuario usuario) {
		super.delete(usuario.getIdusuario(), Usuario.class);
	}


}