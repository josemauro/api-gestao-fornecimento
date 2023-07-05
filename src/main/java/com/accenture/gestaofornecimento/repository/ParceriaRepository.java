package com.accenture.gestaofornecimento.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.accenture.gestaofornecimento.model.Empresa;
import com.accenture.gestaofornecimento.model.Fornecedor;
import com.accenture.gestaofornecimento.model.Parceria;

public interface ParceriaRepository  extends CrudRepository<Parceria, String>{
	List<Parceria> findByFornecedor(Fornecedor fornecedor);
	List<Parceria> findByEmpresa(Empresa empresa);

}
