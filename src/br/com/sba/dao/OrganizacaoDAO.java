package br.com.sba.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.sba.model.Organizacao;


public class OrganizacaoDAO extends GenericDAO<Organizacao> {

	private static final long serialVersionUID = 1L;

	public OrganizacaoDAO() {
		super(Organizacao.class);
	}

    public Organizacao findByCnpj(String cnpj){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("cnpj", cnpj);     
 
        return super.findOneResult(Organizacao.FIND_BY_CNPJ, parameters);
    }
    
	public void delete(Organizacao organizacao) {
		super.delete(organizacao.getId());
	}


}