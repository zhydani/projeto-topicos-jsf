package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.wow.model.ItemComposicao;
import br.unitins.wow.model.Mes;
import br.unitins.wow.model.Nascimento;
import br.unitins.wow.model.Perfil;
import br.unitins.wow.model.Usuario;
import br.unitins.wow.model.Composicao;

public class ComposicaoDAO extends DAO<Composicao> {

	public ComposicaoDAO(Connection conn) {
		super(conn);
	}

	public ComposicaoDAO() {
		super(null);
	}

	@Override
	public void create(Composicao composicao) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "public.composicao " + " (data, idusuario) " + "VALUES " + " (?, ?) ",
				Statement.RETURN_GENERATED_KEYS);
		stat.setDate(1, Date.valueOf(composicao.getData()));
		stat.setInt(2, composicao.getUsuario().getId());

		stat.execute();

		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		composicao.setId(rs.getInt("id"));
		// inserindo os itens de composicao
		// compartilhando a conexao para ficar na mesma transacao
		ItemComposicaoDAO dao = new ItemComposicaoDAO(conn);
		for (ItemComposicao itemComposicao : composicao.getListaItemComposicao()) {
			// informando quem eh o pai da crianca
			itemComposicao.setComposicao(composicao);
			// salvando no banco de dados
			dao.create(itemComposicao);
		}
	}

	@Override
	public void update(Composicao composicao) throws SQLException {

	}

	@Override
	public void delete(int id) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.composicao WHERE idusuario =  ?");
		stat.setInt(1, id);

		stat.execute();
		stat.close();
	}

	public List<Composicao> findByUsuario(int idUsuario) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn
					.prepareStatement("SELECT " + "  c.id, " + "  c.data, " + "  u.id as idusuario, " + "  u.nome, "
							+ "  u.login,  " + "  u.senha, " + "  u.perfil " + "FROM " + "  public.composicao c, "
							+ "  public.usuario u " + "WHERE " + "  c.idusuario = u.id AND " + "  u.id = ? ");
			stat.setInt(1, idUsuario);

			ResultSet rs = stat.executeQuery();

			List<Composicao> listaComposicao = new ArrayList<Composicao>();

			while (rs.next()) {
				Composicao composicao = new Composicao();
				composicao.setId(rs.getInt("id"));
				composicao.setData(rs.getDate("data").toLocalDate());
				composicao.setUsuario(new Usuario());
				composicao.getUsuario().setId(rs.getInt("idusuario"));
				composicao.getUsuario().setNome(rs.getString("nome"));
				composicao.getUsuario().setLogin(rs.getString("login"));
				composicao.getUsuario().setSenha(rs.getString("senha"));
				composicao.getUsuario().setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				// e os itens de composicao?!!?
				ItemComposicaoDAO dao = new ItemComposicaoDAO(conn);
				composicao.setListaItemComposicao(dao.findByComposicao(composicao));

				listaComposicao.add(composicao);
			}

			if (listaComposicao.isEmpty())
				return null;
			return listaComposicao;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Composicao> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  c.id, " + "  c.data, " + "  c.idusuario, "
					+ "  u.nome, " + "  u.login,  " + "  u.senha, " + "  u.nascimento, " + "  u.perfil " + "FROM "
					+ "  public.composicao c, " + "  public.usuario u " + "WHERE " + "  c.idusuario = u.id ");
			ResultSet rs = stat.executeQuery();

			List<Composicao> listaComposicao = new ArrayList<Composicao>();

			while (rs.next()) {
				Composicao composicao = new Composicao();
				composicao.setId(rs.getInt("id"));
				composicao.setData(rs.getDate("data").toLocalDate());
				composicao.setUsuario(new Usuario());
				composicao.getUsuario().setId(rs.getInt("idusuario"));
				composicao.getUsuario().setNome(rs.getString("nome"));
				composicao.getUsuario().setLogin(rs.getString("login"));
				composicao.getUsuario().setSenha(rs.getString("senha"));
				composicao.getUsuario().setNascimento(new Nascimento());
				composicao.getUsuario().getNascimento().setDia(rs.getString("dia"));
				composicao.getUsuario().getNascimento().setMes(Mes.valueOf(rs.getInt("mes")));
				composicao.getUsuario().getNascimento().setAno(rs.getString("ano"));
				composicao.getUsuario().setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				// e os itens de composicao?!!?
				// composicao.setListaItemComposicao(listaItemComposicao);

				listaComposicao.add(composicao);
			}

			if (listaComposicao.isEmpty())
				return null;
			return listaComposicao;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Composicao findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  descricao, "
					+ "  valor " + "FROM " + "  public.composicao " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Composicao composicao = null;

			if (rs.next()) {
				composicao = new Composicao();
				composicao.setId(rs.getInt("id"));
			}

			return composicao;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
