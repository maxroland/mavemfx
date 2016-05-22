package br.com.sba.facade;

import java.io.Serializable;
import java.util.List;

import br.com.sba.dao.OrganizacaoDAO;
import br.com.sba.model.Organizacao;


public class OrganizacaoFacade implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private OrganizacaoDAO organizacaoDAO = new OrganizacaoDAO();

	public void createOrganizacao(Organizacao organizacao) {
		organizacaoDAO.beginTransaction();
		organizacaoDAO.save(organizacao);
		organizacaoDAO.commitAndCloseTransaction();
	}

	public void updateOrganizacao(Organizacao organizacao) {
		organizacaoDAO.beginTransaction();
		Organizacao persistedOrganizacao = organizacaoDAO.find(organizacao.getId());
		persistedOrganizacao.setNome(organizacao.getNome());
		persistedOrganizacao.setRazaoSocial(organizacao.getRazaoSocial());
		persistedOrganizacao.setCnpj(organizacao.getCnpj());
		persistedOrganizacao.setEndereco(organizacao.getEndereco());
		persistedOrganizacao.setCep(organizacao.getCep());
		organizacaoDAO.update(persistedOrganizacao);
		organizacaoDAO.commitAndCloseTransaction();
	}

	public Organizacao findOrganizacao(int Id) {
		organizacaoDAO.beginTransaction();
		Organizacao organizacao = organizacaoDAO.find(Id);
		organizacaoDAO.closeTransaction();
		return organizacao;
	}

	@SuppressWarnings("unchecked")
	public List<Organizacao> listAll() {
		organizacaoDAO.beginTransaction();
		List<Organizacao> result = organizacaoDAO.findAll();
		organizacaoDAO.closeTransaction();
		return result;
	}

	public void deleteOrganizacao(Organizacao organizacao) {
		organizacaoDAO.beginTransaction();
		Organizacao persistedOrganizacao = organizacaoDAO.findReferenceOnly(organizacao.getId());
		organizacaoDAO.delete(persistedOrganizacao);
		organizacaoDAO.commitAndCloseTransaction();
	}
	
	public boolean hasOrganizacao(String cnpj){
		organizacaoDAO.beginTransaction();
		Organizacao organizacao = organizacaoDAO.findByCnpj(cnpj);
		organizacaoDAO.closeTransaction();
		if(organizacao==null){
			return false;
		}
		return true; 
	}
	
}