package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import br.unitins.wow.model.Mes;
import br.unitins.wow.model.Nascimento;

public class NascimentoDAO extends DAO<Nascimento> {

	public NascimentoDAO(Connection conn) {
		super(conn);
	}

	@Override
	public void create(Nascimento entity) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "public.nascimento " + " (id, dia, mes, ano) " + "VALUES " + " (?, ?, ?, ?) ");
		stat.setInt(1, entity.getId());
		stat.setString(2, entity.getDia());
		stat.setInt(3, entity.getMes().getValue());
		stat.setString(4, entity.getAno());

		stat.execute();
		stat.close();

	}

	@Override
	public void update(Nascimento entity) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.nascimento WHERE id =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	@Override
	public List<Nascimento> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Nascimento findById(Integer id) {
		Connection conn = getConnection();

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  dia, " + "  mes, " + "  ano "
					+ "FROM " + "  public.nascimento " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Nascimento nascimento = null;

			if (rs.next()) {
				nascimento = new Nascimento();
				nascimento.setId(rs.getInt("id"));
				nascimento.setDia(rs.getString("dia"));
				nascimento.setMes(Mes.valueOf(rs.getInt("mes")));
				nascimento.setAno(rs.getString("ano"));
			}

			return nascimento;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
