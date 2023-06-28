package com.accenture.gestaofornecimento.repository;

import java.util.Optional;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.gestaofornecimento.model.Fornecedor;
import com.accenture.gestaofornecimento.model.PessoaFisica;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, String>{
	Optional<Fornecedor> findById(String id);
	List<Fornecedor> findByNomeContaining(String nome);
}
