package com.accenture.gestaofornecimento.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.accenture.gestaofornecimento.model.Empresa;
import com.accenture.gestaofornecimento.model.Fornecedor;

public interface EmpresaRepository extends CrudRepository<Empresa, String>{
	Optional<Empresa> findByCnpj(String id);
	List<Empresa> findByNomeFantasiaContaining(String nomeFantasia);
}
