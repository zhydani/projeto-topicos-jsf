package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.unitins.wow.model.Mes;
import br.unitins.wow.model.Nascimento;
import br.unitins.wow.model.Perfil;
import br.unitins.wow.model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

	public UsuarioDAO(Connection conn) {
		super(conn);
	}

	public UsuarioDAO() {
		super(null);
	}

	public Usuario login(String login, String senha) {

		Connection conn = getConnection();

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, "
					+ "  perfil " + "FROM " + "  public.usuario " + "WHERE login = ? AND senha = ? ");

			stat.setString(1, login);
			stat.setString(2, senha);

			ResultSet rs = stat.executeQuery();

			Usuario usuario = null;

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setNascimento(new Nascimento());
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
			}

			return usuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void create(Usuario usuario) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement(
				"INSERT INTO " + "public.usuario " + " (nome, login, senha, perfil) " + "VALUES " + " (?, ?, ?, ?) ",
				Statement.RETURN_GENERATED_KEYS);
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getLogin());
		stat.setString(3, usuario.getSenha());
		stat.setInt(4, usuario.getPerfil().getValue());

		stat.execute();

		// obtendo o id gerado pela tabela do banco de dados
		ResultSet rs = stat.getGeneratedKeys();
		rs.next();
		usuario.getNascimento().setId(rs.getInt("id"));

		NascimentoDAO dao = new NascimentoDAO(conn);
		dao.create(usuario.getNascimento());

	}

	@Override
	public void update(Usuario usuario) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE public.usuario SET " + " nome = ?, " + " login = ?, "
				+ " senha = ?, " + " perfil = ? " + "WHERE " + " id = ? ");
		stat.setString(1, usuario.getNome());
		stat.setString(2, usuario.getLogin());
		stat.setString(3, usuario.getSenha());
		stat.setInt(4, usuario.getPerfil().getValue());
		stat.setInt(5, usuario.getId());

		stat.execute();

	}

	@Override
	public void delete(int id) throws SQLException {

		Connection conn = getConnection();
		// deletando o nascimento (pq possui um relacionamento de fk)
		// passando o conn para manter a mesma transacao
		NascimentoDAO dao = new NascimentoDAO(conn);
		// nascimento tem um relecionamento 1 pra 1, ou seja, o id do usuario eh o mesmo
		// do nascimento.
		dao.delete(id);

		ComposicaoDAO dao2 = new ComposicaoDAO(conn);
		dao2.delete(id);

		// deletando o usuario
		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.usuario WHERE id = ?");
		stat.setInt(1, id);

		stat.execute();

	}

	@Override
	public List<Usuario> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  u.id, " + "  u.nome, " + "  u.login, "
					+ "  u.senha, " + "  u.perfil, n.dia, n.mes, n.ano " + "FROM "
					+ " usuario u, nascimento n WHERE u.id = n.id");
			ResultSet rs = stat.executeQuery();

			List<Usuario> listaUsuario = new ArrayList<Usuario>();

			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));
				usuario.setNascimento(new Nascimento());
				usuario.getNascimento().setDia(rs.getString("dia"));
				usuario.getNascimento().setMes(Mes.valueOf(rs.getInt("mes")));
				usuario.getNascimento().setAno(rs.getString("ano"));
				listaUsuario.add(usuario);
			}

			if (listaUsuario.isEmpty())
				return null;
			return listaUsuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Usuario findId(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  login, " + "  senha, "
					+ "  perfil " + "FROM " + "  public.usuario " + "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Usuario usuario = null;

			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setLogin(rs.getString("login"));
				usuario.setSenha(rs.getString("senha"));
				usuario.setPerfil(Perfil.valueOf(rs.getInt("perfil")));

				NascimentoDAO dao = new NascimentoDAO(conn);
				usuario.setNascimento(dao.findById(usuario.getId()));
				// caso o retorno do telefone seja nulo, instanciar um telefone
				if (usuario.getNascimento() == null)
					usuario.setNascimento(new Nascimento());

			}

			return usuario;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
