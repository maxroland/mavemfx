package br.com.sba.facade;

import java.io.Serializable;
import java.util.List;

import br.com.sba.dao.UsuarioDAO;
import br.com.sba.model.Usuario;
import br.com.sba.util.Criptografia;

public class UsuarioFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	public void createUsuario(Usuario usuario) {
		usuarioDAO.beginTransaction();
		usuarioDAO.save(usuario);
		usuarioDAO.commitAndCloseTransaction();
	}

	public void updateUsuario(Usuario usuario) {
		usuarioDAO.beginTransaction();
		Usuario persistedUsuario = usuarioDAO.find(usuario.getId());
		persistedUsuario.setNome(usuario.getNome());
		persistedUsuario.setEndereco(usuario.getEndereco());
		persistedUsuario.setCep(usuario.getCep());
		persistedUsuario.setEmail(usuario.getEmail());
		persistedUsuario.setSenha(usuario.getSenha());
		persistedUsuario.setCpf(usuario.getCpf());
		persistedUsuario.setTipo(usuario.getTipo());
		usuarioDAO.update(persistedUsuario);
		usuarioDAO.commitAndCloseTransaction();
	}

	public Usuario findUsuario(int Id) {
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.find(Id);
		usuarioDAO.closeTransaction();
		return usuario;
	}

	@SuppressWarnings("unchecked")
	public List<Usuario> listAll() {
		usuarioDAO.beginTransaction();
		List<Usuario> result = usuarioDAO.findAll();
		usuarioDAO.closeTransaction();
		return result;
	}

	public void deleteUsuario(Usuario usuario) {
		usuarioDAO.beginTransaction();
		Usuario persistedUsuario = usuarioDAO.findReferenceOnly(usuario.getId());
		usuarioDAO.delete(persistedUsuario);
		usuarioDAO.commitAndCloseTransaction();
	}

	public boolean hasLogin(String login){
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.findUserByLogin(login);
		usuarioDAO.closeTransaction();
		if(usuario==null){
			return false;
		}
		return true; 
	}
	
	public Usuario isValidLogin(String login, String senha) {
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.findUserByLogin(login);
		usuarioDAO.closeTransaction();
		if (usuario == null || !usuario.getSenha().equals(Criptografia.converter(senha))) {
			return null;
		}

		return usuario;
	}
	
	
}