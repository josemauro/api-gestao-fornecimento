package com.accenture.gestaofornecimento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.http.ResponseEntity;

import com.accenture.gestaofornecimento.model.Empresa;
import com.accenture.gestaofornecimento.repository.EmpresaRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
    
	@PostMapping(path="/salvar")
	public @ResponseBody String criarEmpresa(@RequestParam String cnpj, @RequestParam String nomeFantasia,
											 @RequestParam String cep) {
		if (empresaRepository.findById(cnpj).isPresent()) {
			return "Erro ao salvar, empresa já existe!";
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
		
		return "Salvo com sucesso!";
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
	public @ResponseBody Iterable<Empresa> listarEmpresas() {
		return empresaRepository.findAll();
	}
	
	@PostMapping(path="/apagar")
	public @ResponseBody String deletar(String cnpj) {
	
		Empresa empresa = new Empresa();
		empresa.setCnpj(cnpj);
		
		empresaRepository.delete(empresa);
		
		return "Empresa deletada com sucesso!";
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
