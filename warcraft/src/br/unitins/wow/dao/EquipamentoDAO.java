package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.wow.model.ItemEquipamento;
import br.unitins.wow.model.Mes;
import br.unitins.wow.model.Nascimento;
import br.unitins.wow.model.Perfil;
import br.unitins.wow.model.Usuario;
import br.unitins.wow.model.Equipamento;

public class EquipamentoDAO extends DAO<Equipamento> {

	public EquipamentoDAO(Connection conn) {
		super(conn);
	}

	public EquipamentoDAO() {
		super(null);
	}

	@Override
	public void create(Equipamento equipamento) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "public.equipamento " + " (data, idusuario) " + "VALUES " + " (?, ?) ",
				Statement.RETURN_GENERATED_KEYS);
		stat.setDate(1, Date.valueOf(equipamento.getData()));
		stat.setInt(2, equipamento.getUsuario().getId());

		stat.execute();

		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		equipamento.setId(rs.getInt("id"));
		// inserindo os itens de equipamento
		// compartilhando a conexao para ficar na mesma transacao
		ItemEquipamentoDAO dao = new ItemEquipamentoDAO(conn);
		for (ItemEquipamento itemEquipamento : equipamento.getListaItemEquipamento()) {
			// informando quem eh o pai da crianca
			itemEquipamento.setEquipamento(equipamento);
			// salvando no banco de dados
			dao.create(itemEquipamento);
		}
	}

	@Override
	public void update(Equipamento equipamento) throws SQLException {

	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.composicao WHERE idusuario =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	public List<Equipamento> findByUsuario(int idUsuario) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement(
					"SELECT " + "  e.id, " + "  e.data, " + "  u.id as idusuario, " + "  u.nome, " + "  u.login,  "
							+ "  u.senha, " + "  u.nascimento, " + "  u.perfil " + "FROM " + "  public.equipamento e, "
							+ "  public.usuario u " + "WHERE " + "  e.idusuario = u.id AND " + "  u.id = ? ");
			stat.setInt(1, idUsuario);

			ResultSet rs = stat.executeQuery();

			List<Equipamento> listaEquipamento = new ArrayList<Equipamento>();

			while (rs.next()) {
				Equipamento equipamento = new Equipamento();
				equipamento.setId(rs.getInt("id"));
				equipamento.setData(rs.getDate("data").toLocalDate());
				equipamento.setUsuario(new Usuario());
				equipamento.getUsuario().setId(rs.getInt("idusuario"));
				equipamento.getUsuario().setNome(rs.getString("nome"));
				equipamento.getUsuario().setLogin(rs.getString("login"));
				equipamento.getUsuario().setSenha(rs.getString("senha"));
				equipamento.getUsuario().setNascimento(new Nascimento());
				equipamento.getUsuario().getNascimento().setDia(rs.getString("dia"));
				equipamento.getUsuario().getNascimento().setMes(Mes.valueOf(rs.getInt("mes")));
				equipamento.getUsuario().getNascimento().setAno(rs.getString("ano"));
				equipamento.getUsuario().setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				// e os itens de equipamento?!!?
				ItemEquipamentoDAO dao = new ItemEquipamentoDAO(conn);
				equipamento.setListaItemEquipamento(dao.findByEquipamento(equipamento));

				listaEquipamento.add(equipamento);
			}

			if (listaEquipamento.isEmpty())
				return null;
			return listaEquipamento;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Equipamento> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  e.id, " + "  e.data, " + "  e.idusuario, "
					+ "  u.nome, " + "  u.login,  " + "  u.senha, " + "  u.nascimento, " + "  u.perfil " + "FROM "
					+ "  public.equipamento e, " + "  public.usuario u " + "WHERE " + "  e.idusuario = u.id ");
			ResultSet rs = stat.executeQuery();

			List<Equipamento> listaEquipamento = new ArrayList<Equipamento>();

			while (rs.next()) {
				Equipamento equipamento = new Equipamento();
				equipamento.setId(rs.getInt("id"));
				equipamento.setData(rs.getDate("data").toLocalDate());
				equipamento.setUsuario(new Usuario());
				equipamento.getUsuario().setId(rs.getInt("idusuario"));
				equipamento.getUsuario().setNome(rs.getString("nome"));
				equipamento.getUsuario().setLogin(rs.getString("login"));
				equipamento.getUsuario().setSenha(rs.getString("senha"));
				equipamento.getUsuario().setNascimento(new Nascimento());
				equipamento.getUsuario().getNascimento().setDia(rs.getString("dia"));
				equipamento.getUsuario().getNascimento().setMes(Mes.valueOf(rs.getInt("mes")));
				equipamento.getUsuario().getNascimento().setAno(rs.getString("ano"));
				equipamento.getUsuario().setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				listaEquipamento.add(equipamento);
			}

			if (listaEquipamento.isEmpty())
				return null;
			return listaEquipamento;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Equipamento findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  descricao, "
					+ "  valor " + "FROM " + "  public.equipamento " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Equipamento equipamento = null;

			if (rs.next()) {
				equipamento = new Equipamento();
				equipamento.setId(rs.getInt("id"));
			}

			return equipamento;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
