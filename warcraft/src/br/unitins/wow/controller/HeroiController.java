package br.unitins.wow.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.model.Classe;
import br.unitins.wow.model.Origem;
import br.unitins.wow.application.Util;
import br.unitins.wow.dao.DAO;
import br.unitins.wow.dao.HeroiDAO;
import br.unitins.wow.model.Perfil;
import br.unitins.wow.model.Heroi;

@Named
@ViewScoped
public class HeroiController implements Serializable {

	private static final long serialVersionUID = -6521198943457165212L;

	private Heroi heroi;

	public HeroiController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("heroiFlash");
		heroi = (Heroi) flash.get("heroiFlash");
	}

	public void incluir() {
		DAO<Heroi> dao = new HeroiDAO();
		// faz a inclusao no banco de dados
		try {
			dao.create(getHeroi());
			dao.getConnection().commit();
			Util.addMessageInfo("Inclusão realizada com sucesso.");
			limpar();
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao incluir o heroi no Banco de Dados.");
			e.printStackTrace();
		}
	}

	public void alterar() {
		DAO<Heroi> dao = new HeroiDAO();
		// faz a alteracao no banco de dados
		try {
			dao.update(getHeroi());
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
		DAO<Heroi> dao = new HeroiDAO();
		// faz a exclusao no banco de dados
		try {
			dao.delete(getHeroi().getId());
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
		return "consultaheroi.xhtml?faces-redirect=true";
	}

	public Heroi getHeroi() {
		if (heroi == null) {
			heroi = new Heroi();
		}
		return heroi;
	}

	public void setHeroi(Heroi heroi) {
		this.heroi = heroi;
	}

	public void limpar() {
		heroi = null;
	}

	public Classe[] getListaClasse() {
		return Classe.values();
	}

	public Origem[] getListaOrigem() {
		return Origem.values();
	}

}
