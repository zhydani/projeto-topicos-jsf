package br.unitins.wow.model;

public enum Classe {
	GUERREIRO(1, "Guerreiro"), PALADINO(2, "Paladino"), CACADOR(3, "Caçador"),
	LADINO(4, "Ladino"), SACERDOTE(5, "Sacerdote"), CAVALEIRODAMORTE(6, "Cavaleiro da Morte"), XAMA(7, "Xamã"),
	MAGO(8, "Mago"), BRUXO(9, "Bruxo"), MONGE(10, "Monge"), DRUIDA(10, "Druida"), CACADORDEDEMONIOS(10, "Caçador de Demônios");

	private int value;
	private String label;

	Classe(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static Classe valueOf(int value) {

		for (Classe classe : Classe.values()) {
			if (classe.getValue() == value)
				return classe;
		}
		return null;
	}

}
