package com.accenture.gestaofornecimento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.accenture.gestaofornecimento.model.Parceria;
import com.accenture.gestaofornecimento.repository.ParceriaRepository;

public class ParceriaController {

	@Autowired
	private ParceriaRepository parceriaRepository;
	
	@PostMapping(path="/salvar")
	public @ResponseBody String criarFornecedor(@RequestParam String empresa, @RequestParam String fornecedor) {
//		if (parceriaRepository.findById(id).isPresent()) {
//			return "Erro ao salvar, parceria já existe!";
//		}
		
		Parceria parceria = new Parceria();
		
		parceria.setEmpresa(empresa);
		parceria.setFornecedor(fornecedor);
		
		parceriaRepository.save(parceria);
		
		return "Salvo com sucesso!";
	  }
	

	@PostMapping(path="/atualizar")
	public @ResponseBody String atualizarFornecedor(@RequestParam String empresa, @RequestParam String fornecedor) {

//		if (!parceriaRepository.findById(id).isPresent()) {
//			return "Erro ao atualizar, parceria não encontrado!";
//		}
		Parceria parceria = new Parceria();
		parceria.setEmpresa(empresa);
		parceria.setFornecedor(fornecedor);
		
		parceriaRepository.save(parceria);
		
		return "Parceria atualizada com sucesso!";
	  }


	@GetMapping(path="/listar")
	public @ResponseBody Iterable<Parceria> listarFornecedores() {
		return parceriaRepository.findAll();
	}
	
	@PostMapping(path="/apagar")
	public @ResponseBody String deletar(@RequestParam String empresa, @RequestParam String fornecedor) {
	
		Parceria parceria = new Parceria();
		parceria.setEmpresa(empresa);
		parceria.setFornecedor(fornecedor);
		
		parceriaRepository.delete(parceria);
		
		return "Parceria removida com sucesso!";
	  }
}
