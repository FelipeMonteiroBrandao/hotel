package com.example.hotel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.hotel.model.Hospede;
import com.example.hotel.repository.HospedeRepository;

@RestController
public class HospedeController {

	@Autowired
	HospedeRepository hospedeRepository;
	
	@GetMapping("/hospedesListar")
	public List<Hospede> listar(){
		
		return hospedeRepository.findAll();
		
	}
	
	@PostMapping("/hospedesInserir")
	@ResponseStatus(HttpStatus.CREATED)
	public Hospede adicionar(@RequestBody Hospede hospede) {
		
		return hospedeRepository.save(hospede);
		
	}
	
	@PostMapping("/hospedesAlterar")
	public String alterar(@RequestBody Hospede hospede) {
		
		String result = "";
		Long id = hospede.getId();
				
		if(!(id.equals(null))) {
			
			hospedeRepository.save(hospede);
			result = "Hospede "+hospede.getNome()+" alterado!";
		}else {
			result = "Hospede não alterado!";
		}
		return result;
	}
	
	@DeleteMapping("hospedesExcluir/{id}")
	public String deletar(@PathVariable Long id) {
		
		String result = "";
		
		try {
			
			hospedeRepository.deleteById(id);
			
			result = "Hospede excluido com sucesso!";
			
		}catch(Exception ex){
			
			result = "Erro ao excluir hospede: "+ex;
		}
		
		return result;
	}
}
