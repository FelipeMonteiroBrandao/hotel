package com.example.hotel.model;

public class CheckinDias {
	
	Long id;
	
	String nome;
	
	String documento;
	
	int diaSemana;
	
	int diaFSemana;
	
	Boolean carro;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public int getDiaSemana() {
		return diaSemana;
	}

	public void setDiaSemana(int diaSemana) {
		this.diaSemana = diaSemana;
	}

	public int getDiaFSemana() {
		return diaFSemana;
	}

	public void setDiaFSemana(int diaFSemana) {
		this.diaFSemana = diaFSemana;
	}

	public Boolean getCarro() {
		return carro;
	}

	public void setCarro(Boolean carro) {
		this.carro = carro;
	}
	
	

}
