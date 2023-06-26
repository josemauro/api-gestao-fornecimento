package com.accenture.gestaofornecimento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.gestaofornecimento.model.Empresa;
import com.accenture.gestaofornecimento.repository.EmpresaRepository;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@PostMapping(path="/salvar")
	public @ResponseBody String criarEmpresa(@RequestParam String cpnj, @RequestParam String nomeFantasia,
											 @RequestParam String cep) {
		if (empresaRepository.findById(cpnj).isPresent()) {
			return "Erro ao salvar, empresa já existe!";
		}
		
		Empresa empresa = new Empresa();
		empresa.setCnpj(cpnj);
		
		empresa.setNomeFantasia(nomeFantasia);
		empresa.setCep(cep);
		
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
		empresa.setCep(cep);
		
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
	
}
