package com.accenture.gestaofornecimento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import com.accenture.gestaofornecimento.model.Empresa;
import com.accenture.gestaofornecimento.model.Fornecedor;
import com.accenture.gestaofornecimento.repository.EmpresaRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
    
	@PostMapping(path="/salvar")
	public @ResponseBody ResponseEntity criarEmpresa(@RequestParam String cnpj, @RequestParam String nomeFantasia,
											 @RequestParam String cep) {
		if (empresaRepository.findById(cnpj).isPresent()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		
		Empresa empresa = new Empresa();
		empresa.setCnpj(cnpj);
		
		empresa.setNomeFantasia(nomeFantasia);
		if (validarCEP(cep)) {
			empresa.setCep(cep);
		}else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
			
		empresaRepository.save(empresa);
		
		return new ResponseEntity(HttpStatus.OK);
	  }
	

	@PostMapping(path="/atualizar")
	public @ResponseBody String atualizarEmpresa(@RequestParam String cnpj, @RequestParam String nomeFantasia,
												 @RequestParam String cep) {

		if (!empresaRepository.findById(cnpj).isPresent()) {
			return "Erro ao atualizar, empresa não encontrada!";
		}
		Empresa empresa = new Empresa();
		empresa.setCnpj(cnpj);
		empresa.setNomeFantasia(nomeFantasia);

		if (validarCEP(cep)) {
			empresa.setCep(cep);
		}else {
			return "Erro ao salvar empresa, CEP inválido.";
		}

		empresaRepository.save(empresa);
		
		return "Dados da empresa atualizados com sucesso!";
	  }


	@GetMapping(path="/listar")
	public @ResponseBody List<Empresa> listarEmpresas(@RequestParam String nome)  {
		return empresaRepository.findByNomeFantasiaContaining(nome);
	}
	
	@GetMapping(path="/listarTodos")
	public @ResponseBody Iterable<Empresa> listarTodos() {

		return empresaRepository.findAll();
	}
	
	@PostMapping(path="/apagar")
	public @ResponseBody ResponseEntity deletar(String cnpj) {
	
		Empresa empresa = new Empresa();
		empresa.setCnpj(cnpj);
		
		try {
			empresaRepository.delete(empresa);
		} catch (DataIntegrityViolationException e){
			return new ResponseEntity<String>("Erro ao deletar, verifique se existe uma parceria existente para essa empresa!", HttpStatus.CONFLICT);
		}
		
		

		return new ResponseEntity<String>("Empresa deletada com sucesso!", HttpStatus.OK);
	  }
	
	private Boolean validarCEP(String cep) {
	    String uri = "http://cep.la/" + cep;

	    HttpHeaders headers = new HttpHeaders();
	    headers.set("Accept", "application/json");

	    HttpEntity<Void> requestEntity = new HttpEntity<>(headers);
	    RestTemplate restTemplate = new RestTemplate();
		
	    // make an HTTP GET request with headers
	    try {
			ResponseEntity<List> response = restTemplate.exchange(
			         uri,
			         HttpMethod.GET,
			         requestEntity,
			         List.class,
			         1
			 );
	    }catch (RestClientException e) {
	    	// Não recebeu uma lista vazia, no caso '[]', e portanto não conseguiu converter para List
	    	return true;
	    }

		return false;
	}
}
