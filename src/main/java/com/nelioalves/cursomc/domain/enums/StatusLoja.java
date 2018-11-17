package com.nelioalves.cursomc.domain.enums;

public enum StatusLoja {

	ATIVO(1, "Ativo"), INATIVO(2, "Inativo");
	
	private int cod;
	private String descricao;
	
	private StatusLoja(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static StatusLoja toEnum(Integer cod) {
		if(cod == null)
			return null;
		
		for(StatusLoja x: StatusLoja.values()) {
			if(cod.equals(x.getCod()))
				return x;
		}
		
		throw new IllegalArgumentException("ID inválido: "+cod);
	}
	
	
}
