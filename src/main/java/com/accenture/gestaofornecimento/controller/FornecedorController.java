package com.accenture.gestaofornecimento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.gestaofornecimento.model.Fornecedor;
import com.accenture.gestaofornecimento.repository.FornecedorRepository;

@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@PostMapping(path="/salvar")
	public @ResponseBody String criarFornecedor(@RequestParam String id, @RequestParam String nome,
												@RequestParam String email) {
		// @ResponseBody String retornada é própria response e não o nome de uma view
		// @RequestParam parâmetro da requisição
		if (fornecedorRepository.findById(id).isPresent()) {
			return "Erro ao salvar, fornecedor já existe!";
		}
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
		
		fornecedor.setNome(nome);
		fornecedor.setEmail(email);
		
		fornecedorRepository.save(fornecedor);
		
		return "Salvo com sucesso!";
	  }
	

	@PostMapping(path="/atualizar")
	public @ResponseBody String atualizarFornecedor(@RequestParam String id, @RequestParam String nome,
													@RequestParam String email) {

		if (!fornecedorRepository.findById(id).isPresent()) {
			return "Erro ao atualizar, fornecedor não encontrado!";
		}
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
		fornecedor.setNome(nome);
		fornecedor.setEmail(email);
		
		fornecedorRepository.save(fornecedor);
		
		return "Fornecedor atualizado com sucesso!";
	  }


	@GetMapping(path="/listar")
	public @ResponseBody Iterable<Fornecedor> listarFornecedores() {
		return fornecedorRepository.findAll();
	}
	
	@PostMapping(path="/apagar")
	public @ResponseBody String deletar(@RequestParam String id) {
		// @ResponseBody String retornada é própria response e não o nome de uma view
		// @RequestParam parâmetro da requisição
	
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
		
		fornecedorRepository.delete(fornecedor);
		
		return "Fornecedor deletado com sucesso!";
	  }
	
}
