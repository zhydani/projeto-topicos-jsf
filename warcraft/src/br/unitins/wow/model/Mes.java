package br.unitins.wow.model;

public enum Mes {
	JANEIRO(1, "Janeiro"), 
	FEVEREIRO(2, "Fevereiro"),
	MARCO(3, "Março"),
	ABRIL(4, "Abril"),
	MAIO(5, "Maio"),
	JUNHO(6, "Junho"),
	JULHO(7, "Julho"),
	AGOSTO(8, "Agosto"),
	SETEMBRO(9, "Setembro"),
	OUTUBRO(10, "Outubro"),
	NOVEMBRO(11, "Novembro"),
	DEZEMBRO(12, "Dezembro");
	
	private int value;
	private String label;
	
	Mes(int value, String label) {
		this.value = value;
		this.label = label;
	}

	public int getValue() {
		return value;
	}

	public String getLabel() {
		return label;
	}
	
	public static Mes valueOf(int value) {
		
		for (Mes mes : Mes.values()) {
			if (mes.getValue() == value) 
				return mes;
		}
		return null;
	}
	

}
