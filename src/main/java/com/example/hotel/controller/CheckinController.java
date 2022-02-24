package com.example.hotel.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.example.hotel.model.Checkin;
import com.example.hotel.model.CheckinDias;
import com.example.hotel.model.CheckinValores;
import com.example.hotel.model.Hospede;
import com.example.hotel.repository.CheckinRepository;
import com.example.hotel.repository.HospedeRepository;

@RestController
public class CheckinController {
	
	@Autowired
	CheckinRepository checkinRepository;
	
	@Autowired
	HospedeRepository hospedeRepository;
	
	@GetMapping("/checkinsListar")
	public List<Checkin> listar(){
		
		return checkinRepository.findAll(); 
	}
	
	@PostMapping("/checkinsInserir")
	@ResponseStatus(HttpStatus.CREATED)
	public Checkin adicionar(@RequestBody Checkin checkin) {
		
		return checkinRepository.save(checkin);
	}
	
	@PostMapping("/checkinsAlterar")
	public String alterar(@RequestBody Checkin checkin) {
		
		String result = "";
		Long id = checkin.getId();
		
		if(!(id.equals(null))) {
			
			checkinRepository.save(checkin);
			result = "Checkin do hospede "+checkin.getHospede().getNome()+" alterado!";
		}else {
			result = "Checkin não alterado!";
		}
		return result;
		
	}
	
	@DeleteMapping("checkinsExcluir/{id}")
	public String deletar(@PathVariable Long id) {
		
		String result = "";
		
		try {
			
			checkinRepository.deleteById(id);
			
			result = "checkin excluido com sucesso!";
			
		}catch(Exception ex){
			
			result = "Erro ao excluir checkin: "+ex;
		}
		
		return result;
	}
	
	@GetMapping("/checkinsListar/hospedesByNome/{nome}")
	public List<Hospede> hospedeByNome(@PathVariable String nome) {
		
		nome = nome.replace("_"," ");
		
		List<Hospede> hospede = hospedeRepository.findByName(nome);
		
		return hospede;
		
	}
	
	@GetMapping("/checkinsListar/hospedesByDocumento/{documento}")
	public List<Hospede> hospedeByDocumento(@PathVariable String documento) {
		
		List<Hospede> hospede = hospedeRepository.findByDocumento(documento);
		
		return hospede;
		
	}
	
	@GetMapping("/checkinsListar/hospedesByTelefone/{telefone}")
	public List<Hospede> hospedeByTelefone(@PathVariable String telefone) {
		
		List<Hospede> hospede = hospedeRepository.findByTelefone(telefone);
		
		return hospede;
		
	}

	@GetMapping("/checkinsListar/hospedesFiltro/{tipo}")
	public List<CheckinValores> hospedesFiltro(@PathVariable String tipo){
		
		List<Checkin> checkin = null;
		List<CheckinDias> check = null;
		List<CheckinValores> ch = null;
		
		if(tipo.equals("noHotel")) {
			
			checkin = checkinRepository.filtroNoHotel();
			check = diasCheckin(checkin, tipo);
			ch = geraValores(check);
			
		}else if (tipo.equals("foraHotel")) {
			
			checkin = checkinRepository.filtroForaHotel();
			check = diasCheckin(checkin, tipo);
			ch = geraValores(check);
		}
		
		
		return ch;
	}
	
	public List<CheckinDias> diasCheckin(List<Checkin> checkin, String tipo) {
		
		List<CheckinDias> lista = new ArrayList<CheckinDias>();
		CheckinDias check = new CheckinDias();
		
		if(tipo.equals("noHotel")) {
			
			for (int i = 0; i < checkin.size(); i++) {
							
				check.setId(checkin.get(i).getId());
				check.setNome(checkin.get(i).getHospede().getNome());
				check.setDocumento(checkin.get(i).getHospede().getDocumento());
				check.setCarro(checkin.get(i).getAdicionalVeiculo());
				
				Date dataInicial = checkin.get(i).getDataEntrada();
				Timestamp stamp = new Timestamp(System.currentTimeMillis());
				Date dataFinal = new Date(stamp.getTime());
				
				check.setDiaSemana(contaDiasSemana(dataInicial, dataFinal));
				check.setDiaFSemana(contaDiasFSemana(dataInicial, dataFinal));
				lista.add(check);
				
			}
		}else if(tipo.equals("foraHotel")) {
			
			for (int i = 0; i < checkin.size(); i++) {
				
				check.setId(checkin.get(i).getId());
				check.setNome(checkin.get(i).getHospede().getNome());
				check.setDocumento(checkin.get(i).getHospede().getDocumento());
				check.setCarro(checkin.get(i).getAdicionalVeiculo());
				
				Date dataInicial = checkin.get(i).getDataEntrada();
				Date dataFinal = checkin.get(i).getDataSaida();
				
				check.setDiaSemana(contaDiasSemana(dataInicial, dataFinal));
				check.setDiaFSemana(contaDiasFSemana(dataInicial, dataFinal));
				lista.add(check);
			}
		}
		
		return lista;
	}
	
	public int contaDiasSemana(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int workDays = 0;

		//Return 0 if start and end are the same
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
		return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
		startCal.setTime(endDate);
		endCal.setTime(startDate);
		}

		do {
		//excluding start date
		startCal.add(Calendar.DAY_OF_MONTH, 1);
		if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
		++workDays;
		}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

		return workDays;
	}
	
	public int contaDiasFSemana(Date startDate, Date endDate) {
		Calendar startCal = Calendar.getInstance();
		startCal.setTime(startDate);

		Calendar endCal = Calendar.getInstance();
		endCal.setTime(endDate);

		int weekDays = 0;

		//Return 0 if start and end are the same
		if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
		return 0;
		}

		if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
		startCal.setTime(endDate);
		endCal.setTime(startDate);
		}

		do {
		//excluding start date
		startCal.add(Calendar.DAY_OF_MONTH, 1);
		if (startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
		
		}else {
			weekDays++;
		}
		} while (startCal.getTimeInMillis() < endCal.getTimeInMillis()); //excluding end date

		return weekDays;
	}

	public List<CheckinValores> geraValores(List<CheckinDias> check) {
		
		List<CheckinValores> lista = new ArrayList<CheckinValores>();
		CheckinValores valores = new CheckinValores();
		double val = 0;
		double carroDiaSemana = 0, carroFinalSemana = 0;
		
		System.out.println("             tamanho do vetor:"+check.size());
		
		for(int i = 0; i < check.size(); i++) {
			
			valores.setId(check.get(i).getId());
			valores.setNome(check.get(i).getNome());
			valores.setDocumento(check.get(i).getDocumento());
			valores.setCarro(check.get(i).getCarro());
			
			carroDiaSemana = (check.get(i).getDiaSemana())*15;
			
			if(check.get(i).getDiaSemana() != 0) {
				val += ((check.get(i).getDiaSemana())*120);
				val += carroDiaSemana;
			}	
			
			carroFinalSemana = (check.get(i).getDiaFSemana())*20;
			
			if(check.get(i).getDiaFSemana() != 0) {
				val += ((check.get(i).getDiaFSemana())*150);
				val += carroFinalSemana;
			}
			
			valores.setValor(val);
			lista.add(valores);
		}
		
		return lista;
	}

}
