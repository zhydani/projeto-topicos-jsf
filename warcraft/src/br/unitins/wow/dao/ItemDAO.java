package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.wow.model.Classe;
import br.unitins.wow.model.Origem;
import br.unitins.wow.model.Item;

public class ItemDAO extends DAO<Item> {

	public ItemDAO(Connection conn) {
		super(conn);
	}

	public ItemDAO() {
		super(null);
	}

	@Override
	public void create(Item item) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.item "
				+ " (nome, tipo, engaste, adicional, custo) " + "VALUES " + " (?, ?, ?, ?, ?) ");
		stat.setString(1, item.getNome());
		stat.setString(2, item.getTipo());
		stat.setString(3, item.getEngaste());
		stat.setDouble(4, item.getAdicional());
		stat.setDouble(5, item.getCusto());

		stat.execute();

	}

	@Override
	public void update(Item item) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE public.item SET " + " nome = ?, " + " tipo = ?, "
				+ " engaste = ?, " + " adicional = ?, " + " custo = ? " + "WHERE " + " id = ? ");
		stat.setString(1, item.getNome());
		stat.setString(2, item.getTipo());
		stat.setString(1, item.getEngaste());
		stat.setDouble(1, item.getAdicional());
		stat.setDouble(1, item.getCusto());

		stat.setInt(6, item.getId());

		stat.execute();

	}

	@Override
	public List<Item> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  tipo, "
					+ "  engaste, " + "  adicional, " + "  custo " + "FROM " + "  public.item ");
			ResultSet rs = stat.executeQuery();

			List<Item> listaitem = new ArrayList<Item>();

			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setNome(rs.getString("nome"));
				item.setTipo(rs.getString("tipo"));
				item.setEngaste(rs.getString("engaste"));
				item.setAdicional(rs.getDouble("adicional"));
				item.setCusto(rs.getDouble("custo"));

				listaitem.add(item);
			}

			if (listaitem.isEmpty())
				return null;
			return listaitem;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Item> findByNome(String nome) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn
					.prepareStatement("SELECT " + "  id, " + "  nome, " + "  tipo, " + "  engaste, " + "  adicional, "
							+ "  custo " + "FROM " + "  public.item " + "WHERE " + "  nome ilike ? ");

			stat.setString(1, nome == null ? "%" : "%" + nome + "%");
			ResultSet rs = stat.executeQuery();

			List<Item> listaitem = new ArrayList<Item>();

			while (rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setNome(rs.getString("nome"));
				item.setTipo(rs.getString("tipo"));
				item.setEngaste(rs.getString("engaste"));
				item.setAdicional(rs.getDouble("adicional"));
				item.setCusto(rs.getDouble("custo"));

				listaitem.add(item);
			}

			if (listaitem.isEmpty())
				return null;
			return listaitem;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Item findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  tipo, "
					+ "  engaste, " + "  adicional, " + "  custo " + "FROM " + "  public.item " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Item item = null;

			if (rs.next()) {
				item = new Item();
				item.setId(rs.getInt("id"));
				item.setNome(rs.getString("nome"));
				item.setTipo(rs.getString("tipo"));
				item.setEngaste(rs.getString("engaste"));
				item.setAdicional(rs.getDouble("adicional"));
				item.setCusto(rs.getDouble("custo"));
			}

			return item;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.item WHERE id = ?");
		stat.setInt(1, id);

		stat.execute();
	}

}
