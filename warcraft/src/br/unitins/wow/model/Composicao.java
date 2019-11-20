package br.unitins.wow.model;

import java.time.LocalDate;
import java.util.List;

import br.unitins.wow.model.ItemComposicao;

public class Composicao {

	private Integer id;
	private LocalDate data;
	private Usuario usuario;
	private List<ItemComposicao> listaItemComposicao;

	// campo calculado
	private Float totalComposicao = 0.0f;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ItemComposicao> getListaItemComposicao() {
		return listaItemComposicao;
	}

	public void setListaItemComposicao(List<ItemComposicao> listaItemComposicao) {
		this.listaItemComposicao = listaItemComposicao;
	}

	public Float getTotalComposicao() {
		if (listaItemComposicao != null)
			for (ItemComposicao itemComposicao : listaItemComposicao)
				totalComposicao += itemComposicao.getValor();
		
		return totalComposicao;
	}

	public void setTotalComposicao(Float totalComposicao) {
		this.totalComposicao = totalComposicao;
	}

}
