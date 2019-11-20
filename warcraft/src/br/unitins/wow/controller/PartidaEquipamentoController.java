package br.unitins.wow.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.application.Session;
import br.unitins.wow.application.Util;
import br.unitins.wow.dao.EquipamentoDAO;
import br.unitins.wow.model.ItemEquipamento;
import br.unitins.wow.model.Usuario;
import br.unitins.wow.model.Equipamento;

@Named
@ViewScoped
public class PartidaEquipamentoController implements Serializable {

	private static final long serialVersionUID = 780477496476930858L;

	private Equipamento equipamento;

	public Equipamento getEquipamento() {
		if (equipamento == null)
			equipamento = new Equipamento();

		// obtendo o partidaequipamento da sessao
		List<ItemEquipamento> partidaequipamento = (ArrayList<ItemEquipamento>) Session.getInstance().getAttribute("partidaequipamento");

		// adicionando os itens do partidaequipamento na equipamento
		if (partidaequipamento == null)
			partidaequipamento = new ArrayList<ItemEquipamento>();
		equipamento.setListaItemEquipamento(partidaequipamento);

		return equipamento;
	}

	public void remover(int idProduto) {
		// alunos vao implementar
	}

	public void finalizar() {
		Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
		if (usuario == null) {
			Util.addMessageWarn("Eh preciso estar logado para realizar uma equipamento. Faca o Login!!");
			return;
		}
		// montar a equipamento
		Equipamento equipamento = new Equipamento();
		equipamento.setData(LocalDate.now());
		equipamento.setUsuario(usuario);
		List<ItemEquipamento> partidaequipamento = (ArrayList<ItemEquipamento>) Session.getInstance().getAttribute("partidaequipamento");
		equipamento.setListaItemEquipamento(partidaequipamento);
		// salvar no banco
		EquipamentoDAO dao = new EquipamentoDAO();
		try {
			dao.create(equipamento);
			dao.getConnection().commit();
			Util.addMessageInfo("Equipamento realizada com sucesso.");
			// limpando o partidaequipamento
			Session.getInstance().setAttribute("partidaequipamento", null);
		} catch (SQLException e) {
			dao.rollbackConnection();
			dao.closeConnection();
			Util.addMessageInfo("Erro ao finalizar a Equipamento.");
			e.printStackTrace();
		}

	}

	public void setEquipamento(Equipamento equipamento) {
		this.equipamento = equipamento;
	}

}
