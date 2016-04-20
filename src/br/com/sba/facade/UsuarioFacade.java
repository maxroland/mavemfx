package br.com.sba.facade;

import java.io.Serializable;
import java.util.List;

import br.com.sba.dao.UsuarioDAO;
import br.com.sba.model.Usuario;

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
		Usuario persistedUsuario = usuarioDAO.find(usuario.getIdusuario());
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

	public Usuario findUsuario(int usuarioId) {
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.find(usuarioId);
		usuarioDAO.closeTransaction();
		return usuario;
	}

	public List<Usuario> listAll() {
		usuarioDAO.beginTransaction();
		List<Usuario> result = usuarioDAO.findAll();
		usuarioDAO.closeTransaction();
		return result;
	}

	public void deleteUsuario(Usuario usuario) {
		usuarioDAO.beginTransaction();
		Usuario persistedUsuario = usuarioDAO.findReferenceOnly(usuario.getIdusuario());
		usuarioDAO.delete(persistedUsuario);
		usuarioDAO.commitAndCloseTransaction();
	}
	
	public Usuario isValidLogin(String login, String senha) {
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.findUserByLogin(login);

		if (usuario == null || !usuario.getSenha().equals(senha)) {
			return null;
		}

		return usuario;
	}
	
	public boolean hasLogin(String login){
		usuarioDAO.beginTransaction();
		Usuario usuario = usuarioDAO.findUserByLogin(login);
		if(usuario.getLogin().equals(login) ){
			return true;
		}
		return false;
	}
	
}