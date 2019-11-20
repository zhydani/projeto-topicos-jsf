package br.unitins.wow.model;

public class Heroi {
	private Integer id;
	private String nome;
	private Double vida;
	private Double mana;
	private Double habilidade;
	private Double dano;
	private Double velocidade;
	private Double esquiva;
	private Double critico;
	private Double armadura;
	private Double resistenciamagica;
	private Integer custo;
	private Classe classe;
	private Origem origem;

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

	public Double getVida() {
		return vida;
	}

	public void setVida(Double vida) {
		this.vida = vida;
	}

	public Double getMana() {
		return mana;
	}

	public void setMana(Double mana) {
		this.mana = mana;
	}

	public Double getDano() {
		return dano;
	}

	public void setDano(Double dano) {
		this.dano = dano;
	}

	public Double getVelocidade() {
		return velocidade;
	}

	public void setVelocidade(Double velocidade) {
		this.velocidade = velocidade;
	}

	public Double getEsquiva() {
		return esquiva;
	}

	public void setEsquiva(Double esquiva) {
		this.esquiva = esquiva;
	}

	public Double getCritico() {
		return critico;
	}

	public void setCritico(Double critico) {
		this.critico = critico;
	}

	public Double getArmadura() {
		return armadura;
	}

	public void setArmadura(Double armadura) {
		this.armadura = armadura;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public Origem getOrigem() {
		return origem;
	}

	public void setOrigem(Origem origem) {
		this.origem = origem;
	}

	public Integer getCusto() {
		return custo;
	}

	public void setCusto(Integer custo) {
		this.custo = custo;
	}

	public Double getHabilidade() {
		return habilidade;
	}

	public void setHabilidade(Double habilidade) {
		this.habilidade = habilidade;
	}

	public Double getResistenciamagica() {
		return resistenciamagica;
	}

	public void setResistenciamagica(Double resistenciamagica) {
		this.resistenciamagica = resistenciamagica;
	}

}
