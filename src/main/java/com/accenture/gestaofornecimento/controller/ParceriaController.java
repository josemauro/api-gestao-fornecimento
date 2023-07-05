package com.accenture.gestaofornecimento.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.gestaofornecimento.model.Empresa;
import com.accenture.gestaofornecimento.model.Fornecedor;
import com.accenture.gestaofornecimento.model.Parceria;
import com.accenture.gestaofornecimento.model.ParceriaKey;
import com.accenture.gestaofornecimento.repository.EmpresaRepository;
import com.accenture.gestaofornecimento.repository.FornecedorRepository;
import com.accenture.gestaofornecimento.repository.ParceriaRepository;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/parcerias")
public class ParceriaController {

	@Autowired
	private ParceriaRepository parceriaRepository;

	@Autowired
	private FornecedorRepository fornecedorRepository;
	
	@Autowired
	private EmpresaRepository empresaRepository;

	@PostMapping(path="/salvar")
	public @ResponseBody String criarFornecedor(@RequestParam String empresaId, @RequestParam String fornecedorId) {
		
		ParceriaKey parceriaKey = new ParceriaKey();


		parceriaKey.setFornecedorId(fornecedorId);
		parceriaKey.setEmpresaId(empresaId);

		Parceria parceria = new Parceria();
		parceria.setParceria(parceriaKey);
		
		parceriaRepository.save(parceria);
		
		
		return "Salvo com sucesso!";
	  }
	

	@PostMapping(path="/atualizar")
	public @ResponseBody String atualizarFornecedor(@RequestParam String empresa, @RequestParam String fornecedor) {

		Parceria parceria = new Parceria();
		
		parceriaRepository.save(parceria);
		
		return "Parceria atualizada com sucesso!";
	  }


	@GetMapping(path="/listar/empresas")
	public @ResponseBody List<Empresa> listarFornecedores(@RequestParam String fornecedorId) {
		Optional<Fornecedor> fornecedor = fornecedorRepository.findById(fornecedorId);
	    // recupera as parcerias do fornecedor informado
	    List<Parceria> parcerias = parceriaRepository.findByFornecedor(fornecedor.get());
	    
	    List<Empresa> empresasParceiras = new ArrayList<Empresa>();
	    // gera uma lista contendo cada empresa parceira e suas respectivas informações
	    for (int i=0; i < parcerias.size(); i++) {
	    	Parceria parceria = parcerias.get(i);
	    	
	    	Optional<Empresa> empresa = empresaRepository.findById(parceria.getParceria().getEmpresaId());
	    	empresasParceiras.add(empresa.get());
	    }
		return empresasParceiras;
	}

	@GetMapping(path="/listar/fornecedores")
	public @ResponseBody List<Fornecedor> listarEmpresas(@RequestParam String empresaId) {
		Optional<Empresa> empresa = empresaRepository.findById(empresaId);
	    // recupera as parcerias do empresa informado
	    List<Parceria> parcerias = parceriaRepository.findByEmpresa(empresa.get());
	    
	    List<Fornecedor> fornecedores = new ArrayList<Fornecedor>();
	    // gera uma lista contendo cada empresa parceira e suas respectivas informações
	    for (int i=0; i < parcerias.size(); i++) {
	    	Parceria parceria = parcerias.get(i);
	    	
	    	Optional<Fornecedor	> fornecedor = fornecedorRepository.findById(parceria.getParceria().getFornecedorId());
	    	fornecedores.add(fornecedor.get());
	    }
		return fornecedores;
	}

	@PostMapping(path="/apagar")
	public @ResponseBody String deletar(@RequestParam String empresaId, @RequestParam String fornecedorId) {
		ParceriaKey parceriaKey = new ParceriaKey();


		parceriaKey.setFornecedorId(fornecedorId);
		parceriaKey.setEmpresaId(empresaId);

		Parceria parceria = new Parceria();
		parceria.setParceria(parceriaKey);
				
		parceriaRepository.delete(parceria);
		
		return "Parceria removida com sucesso!";
	  }
}
