package com.example.hotel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.hotel.model.Checkin;

public interface CheckinRepository extends JpaRepository<Checkin, Long>{
	
	@Query(value = "select * from checkin ch where ch.data_saida IS NULL", nativeQuery = true)
	public List<Checkin> filtroNoHotel();
	
	@Query(value = "select * from checkin ch where ch.data_saida IS NOT NULL", nativeQuery = true)
	public List<Checkin> filtroForaHotel();
	
}
