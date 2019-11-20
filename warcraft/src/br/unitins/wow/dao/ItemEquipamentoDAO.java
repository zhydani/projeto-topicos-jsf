package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.wow.model.ItemEquipamento;
import br.unitins.wow.model.Origem;
import br.unitins.wow.model.Item;
import br.unitins.wow.model.Classe;
import br.unitins.wow.model.Equipamento;

public class ItemEquipamentoDAO extends DAO<ItemEquipamento> {

	public ItemEquipamentoDAO(Connection conn) {
		super(conn);
	}

	public ItemEquipamentoDAO() {
		super(null);
	}

	@Override
	public void create(ItemEquipamento itemEquipamento) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.itemequipamento "
				+ " (valor, idequipamento, iditem) " + "VALUES " + " (?, ?, ?) ");
		stat.setDouble(1, itemEquipamento.getValor());
		stat.setInt(2, itemEquipamento.getEquipamento().getId());
		stat.setInt(3, itemEquipamento.getItem().getId());

		stat.execute();
	}

	@Override
	public void update(ItemEquipamento itemEquipamento) throws SQLException {
	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.itemcomposicao WHERE idheroi =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	@Override
	public List<ItemEquipamento> findAll() {
		return null;
	}

	public List<ItemEquipamento> findByEquipamento(Equipamento equipamento) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  e.id, " + "  e.valor, " + "  e.iditem, "
					+ "  i.nome, " + "  i.tipo, " + "  i.engaste, " + "  i.adicional, " + "  i.custo " + "FROM "
					+ "  public.itemequipamento e, " + "  public.item i " + "WHERE " + "  e.iditem = u.id AND "
					+ "  e.idequipamento = ? ");

			stat.setInt(1, equipamento.getId());
			ResultSet rs = stat.executeQuery();

			List<ItemEquipamento> listaItemEquipamento = new ArrayList<ItemEquipamento>();

			while (rs.next()) {
				ItemEquipamento ie = new ItemEquipamento();
				ie.setId(rs.getInt("id"));
				ie.setValor(rs.getDouble("valor"));

				Item item = new Item();
				item.setId(rs.getInt("idproduto"));
				item.setNome(rs.getString("nome"));
				item.setTipo(rs.getString("tipo"));
				item.setEngaste(rs.getString("engaste"));
				item.setAdicional(rs.getDouble("adicional"));
				item.setCusto(rs.getDouble("custo"));

				ie.setItem(item);

				ie.setEquipamento(equipamento);

				listaItemEquipamento.add(ie);
			}

			if (listaItemEquipamento.isEmpty())
				return null;
			return listaItemEquipamento;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
