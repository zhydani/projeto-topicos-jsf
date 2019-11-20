package br.unitins.wow.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.application.Util;
import br.unitins.wow.dao.DAO;
import br.unitins.wow.dao.ItemDAO;
import br.unitins.wow.model.Item;

@Named
@ViewScoped
public class ItemController implements Serializable {

	private static final long serialVersionUID = -6521198943457165212L;

	private Item item;

	public ItemController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("itemFlash");
		item = (Item) flash.get("itemFlash");
	}

	public void incluir() {
		DAO<Item> dao = new ItemDAO();
		// faz a inclusao no banco de dados
		try {
			dao.create(getItem());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o item no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void alterar() {
		DAO<Item> dao = new ItemDAO();
		// faz a alteracao no banco de dados
		try {
			dao.update(getItem());
			dao.getConnection().commit();
			Util.addMessageInfo("Alteração realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao alterar o Usuário no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void excluir() {
		DAO<Item> dao = new ItemDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getItem().getId());
			dao.getConnection().commit();
			Util.addMessageInfo("Exclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			Util.addMessageInfo("Erro ao excluir o Produto no Banco de Dados.");
			e.printStackTrace();
		} finally {
			dao.closeConnection();
		}
	}

	public String goConsultaPage() {
		return "consultaitem.xhtml?faces-redirect=true";
	}

	public Item getItem() {
		if (item == null) {
			item = new Item();
		}
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public void limpar() {
		item = null;
	}

}
