package br.unitins.wow.model;

public enum Origem {
	HUMANO(1, "Humano"), ANAO(2, "Anão"), ELFONOTURNO(3, "Elfo Noturno"), GNOMO(4, "Gnomo"), DRAENEI(5, "Draenei"),
	WORGEN(6, "Worgen"), PANDAREN(7, "Pandaren"), ORC(8, "Orc"), MORTOVIVO(9, "Morto-vivo"), TAUREN(10, "Tauren"),
	TROLL(11, "Troll"), ELFOSANGRENTO(12, "Elfo Sangrento"), GOBLIN(13, "Goblin");

	private int value;
	private String label;

	Origem(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}

	public static Origem valueOf(int value) {

		for (Origem origem : Origem.values()) {
			if (origem.getValue() == value)
				return origem;
		}
		return null;
	}

}
