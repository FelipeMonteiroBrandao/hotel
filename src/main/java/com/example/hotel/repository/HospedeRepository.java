package com.example.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hotel.model.Hospede;

public interface HospedeRepository extends JpaRepository<Hospede, Long>{
	
	@Query(value = "select * from hospede h where h.nome = ?1", nativeQuery = true)
	public List<Hospede> findByName(String nome);
	
	@Query(value = "select * from hospede h where h.documento = ?1", nativeQuery = true)
	public List<Hospede> findByDocumento(String documento);
	
	@Query(value = "select * from hospede h where h.telefone = ?1", nativeQuery = true)
	public List<Hospede> findByTelefone(String telefone);
		
}
