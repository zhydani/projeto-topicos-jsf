package br.unitins.wow.model;

import java.time.LocalDate;
import java.util.List;

public class Equipamento {

	private Integer id;
	private LocalDate data;
	private Usuario usuario;
	private List<ItemEquipamento> listaItemEquipamento;

	// campo calculado
	private Float totalVenda = 0.0f;

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

	public List<ItemEquipamento> getListaItemEquipamento() {
		return listaItemEquipamento;
	}

	public void setListaItemEquipamento(List<ItemEquipamento> listaItemEquipamento) {
		this.listaItemEquipamento = listaItemEquipamento;
	}

	public Float getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(Float totalVenda) {
		this.totalVenda = totalVenda;
	}

}
