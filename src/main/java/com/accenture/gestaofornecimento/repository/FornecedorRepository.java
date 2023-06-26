package com.accenture.gestaofornecimento.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.gestaofornecimento.model.Fornecedor;

@Repository
public interface FornecedorRepository extends CrudRepository<Fornecedor, String>{

}
