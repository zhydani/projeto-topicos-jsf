package br.unitins.wow.model;

public class Item {
	private Integer id;
	private String nome;
	private String tipo;
	private String engaste;
	private Double adicional;
	private Double custo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEngaste() {
		return engaste;
	}

	public void setEngaste(String engaste) {
		this.engaste = engaste;
	}

	public Double getAdicional() {
		return adicional;
	}

	public void setAdicional(Double adicional) {
		this.adicional = adicional;
	}

	public Double getCusto() {
		return custo;
	}

	public void setCusto(Double custo) {
		this.custo = custo;
	}

}
