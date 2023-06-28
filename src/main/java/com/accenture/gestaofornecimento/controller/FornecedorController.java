package com.accenture.gestaofornecimento.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.accenture.gestaofornecimento.model.Fornecedor;
import com.accenture.gestaofornecimento.model.PessoaFisica;
import com.accenture.gestaofornecimento.repository.FornecedorRepository;
import com.accenture.gestaofornecimento.repository.PessoaFisicaRepository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/fornecedores")
public class FornecedorController {
	
	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private PessoaFisicaRepository pessoaRepository;
	
	@PostMapping(path="/salvar")
	public @ResponseBody ResponseEntity criarFornecedor(@RequestParam String id, @RequestParam String nome, @RequestParam String email,
												@RequestParam(required = false) String rg, 
												@RequestParam(required = false) String dataNascimento) {
		// @ResponseBody String retornada é própria response e não o nome de uma view
		// @RequestParam parâmetro da requisição
		 
		if (fornecedorRepository.findById(id).isPresent()) {
			return new ResponseEntity(HttpStatus.CONFLICT);
		}
		
		Fornecedor fornecedor = new Fornecedor();
		fornecedor.setId(id);
		
		fornecedor.setNome(nome);
		fornecedor.setEmail(email);
		
		fornecedorRepository.save(fornecedor);

		if (rg != null && dataNascimento!= null) {
			PessoaFisica pessoa = new PessoaFisica();
			pessoa.setIdFornecedor(id);
			pessoa.setRg(rg);
			
			Date data;
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
				data = new Date(formato.parse(dataNascimento).getTime());
				pessoa.setDataNascimento(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			pessoaRepository.save(pessoa);
		}
		return new ResponseEntity<String>("Salvo com sucesso!", HttpStatus.OK);

	  }
	

	@PostMapping(path="/atualizar")
	public @ResponseBody String atualizarFornecedor(@RequestParam(required = false) String id, @RequestParam String nome,
													@RequestParam(required = false) String email,
													@RequestParam(required = false) String rg, 
													@RequestParam(required = false) String dataNascimento) {

		
		if (!fornecedorRepository.findById(id).isPresent()) {
			return "Erro ao atualizar, fornecedor não encontrado!";
		}

		Fornecedor fornecedor = fornecedorRepository.findById(id).get();

		if (nome != null) {
			fornecedor.setNome(nome);
		}

		if (email != null) {
			fornecedor.setEmail(email);
		}

		PessoaFisica pessoa = pessoaRepository.findByIdFornecedor(id);

		if (rg != null) {
			pessoa.setRg(rg);			
		}	
		if (dataNascimento!= null) {
			Date data;
			try {
				SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
				data = new Date(formato.parse(dataNascimento).getTime());
				pessoa.setDataNascimento(data);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		fornecedorRepository.save(fornecedor);
		pessoaRepository.save(pessoa);

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

		PessoaFisica pessoa = pessoaRepository.findByIdFornecedor(id);

		pessoaRepository.delete(pessoa);
		fornecedorRepository.delete(fornecedor);

		return "Fornecedor deletado com sucesso!";
	  }
	

	
}
