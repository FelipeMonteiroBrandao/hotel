package com.example.hotel.model;

public class CheckinValores {
	
	Long id;
	
	String nome;
	
	String documento;
	
	double valor;
	
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

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Boolean getCarro() {
		return carro;
	}

	public void setCarro(Boolean carro) {
		this.carro = carro;
	}
	
	
}
