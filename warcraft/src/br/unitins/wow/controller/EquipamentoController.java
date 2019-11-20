package br.unitins.wow.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.application.Session;
import br.unitins.wow.application.Util;
import br.unitins.wow.dao.ItemDAO;
import br.unitins.wow.model.ItemEquipamento;
import br.unitins.wow.model.Item;

@Named
@ViewScoped
public class EquipamentoController implements Serializable {

	private static final long serialVersionUID = -8413311224021825448L;

	private String nome;
	private List<Item> listaItem = null;

	public void pesquisar() {
		listaItem = null;
	}

	public void adicionar(int idItem) {
		ItemDAO dao = new ItemDAO();
		Item item = dao.findById(idItem);
		// verifica se existe um partidaequipamento na sessao
		if (Session.getInstance().getAttribute("partidaequipamento") == null) {
			// adiciona um partidaequipamento (de itens de venda) na sessao
			Session.getInstance().setAttribute("partidaequipamento", new ArrayList<ItemEquipamento>());
		}

		// obtendo o partidaequipamento da sessao
		List<ItemEquipamento> partidaequipamento = (ArrayList<ItemEquipamento>) Session.getInstance().getAttribute("partidaequipamento");

		// criando um item de venda para adicionar no partidaequipamento
		ItemEquipamento c = new ItemEquipamento();
		c.setItem(item);
		c.setValor(item.getCusto());

		// adicionando o item no partidaequipamento (variavel local)
		partidaequipamento.add(c);

		// atualizando o partidaequipamento na sessao
		Session.getInstance().setAttribute("partidaequipamento", partidaequipamento);

		Util.addMessageInfo("Item adicionado no partidaequipamento. " + "Quantidade: " + partidaequipamento.size());

	}

	public List<Item> getListaItem() {
		if (listaItem == null) {
			ItemDAO dao = new ItemDAO();
			listaItem = dao.findByNome(getNome());
			if (listaItem == null)
				listaItem = new ArrayList<Item>();
			dao.closeConnection();
		}
		return listaItem;
	}

	public String getNome() {
		return nome;
	}

	public void setNomeH(String nome) {
		this.nome = nome;
	}

}
