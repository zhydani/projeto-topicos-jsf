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
import br.unitins.wow.model.Perfil;
import br.unitins.wow.model.Usuario;
import br.unitins.wow.model.Heroi;
import br.unitins.wow.model.Nascimento;

public class HeroiDAO extends DAO<Heroi> {

	public HeroiDAO(Connection conn) {
		super(conn);
	}

	public HeroiDAO() {
		super(null);
	}

	@Override
	public void create(Heroi heroi) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.heroi "
				+ " (nome, vida, mana, habilidade, dano, velocidade, esquiva, critico, armadura, resistenciamagica, custo, classe, origem) "
				+ "VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ");
		stat.setString(1, heroi.getNome());
		stat.setDouble(2, heroi.getVida());
		stat.setDouble(3, heroi.getHabilidade());
		stat.setDouble(4, heroi.getMana());
		stat.setDouble(5, heroi.getDano());
		stat.setDouble(6, heroi.getVelocidade());
		stat.setDouble(7, heroi.getEsquiva());
		stat.setDouble(8, heroi.getCritico());
		stat.setDouble(9, heroi.getArmadura());
		stat.setDouble(10, heroi.getResistenciamagica());
		stat.setInt(11, heroi.getCusto());
		stat.setInt(12, heroi.getClasse().getValue());
		stat.setInt(13, heroi.getOrigem().getValue());

		stat.execute();

	}

	@Override
	public void update(Heroi heroi) throws SQLException {
		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("UPDATE public.heroi SET " + " nome = ?, " + " vida = ?, "
				+ " mana = ?, " + " habilidade = ?, " + " dano = ?, " + " velocidade = ?, " + " esquiva = ?, "
				+ " critico = ?, " + " armadura = ?, " + " resistenciamagica = ?, " + " custo = ?, " + " classe = ?, "
				+ " origem = ? " + "WHERE " + " id = ? ");
		stat.setString(1, heroi.getNome());
		stat.setDouble(2, heroi.getVida());
		stat.setDouble(3, heroi.getHabilidade());
		stat.setDouble(4, heroi.getMana());
		stat.setDouble(5, heroi.getDano());
		stat.setDouble(6, heroi.getVelocidade());
		stat.setDouble(7, heroi.getEsquiva());
		stat.setDouble(8, heroi.getCritico());
		stat.setDouble(9, heroi.getArmadura());
		stat.setDouble(10, heroi.getResistenciamagica());
		stat.setInt(11, heroi.getCusto());
		stat.setInt(12, heroi.getClasse().getValue());
		stat.setInt(13, heroi.getOrigem().getValue());

		stat.setInt(14, heroi.getId());

		stat.execute();

	}

	@Override
	public List<Heroi> findAll() {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  vida, " + "  mana, "
					+ "  habilidade, " + "  dano, " + "  velocidade, " + "  esquiva, " + "  critico, " + "  armadura, "
					+ "  resistenciamagica, " + "  custo, " + "  classe, " + "  origem " + "FROM " + "  public.heroi ");
			ResultSet rs = stat.executeQuery();

			List<Heroi> listaheroi = new ArrayList<Heroi>();

			while (rs.next()) {
				Heroi heroi = new Heroi();
				heroi.setId(rs.getInt("id"));
				heroi.setNome(rs.getString("nome"));
				heroi.setVida(rs.getDouble("vida"));
				heroi.setMana(rs.getDouble("mana"));
				heroi.setHabilidade(rs.getDouble("habilidade"));
				heroi.setDano(rs.getDouble("dano"));
				heroi.setVelocidade(rs.getDouble("velocidade"));
				heroi.setEsquiva(rs.getDouble("esquiva"));
				heroi.setCritico(rs.getDouble("critico"));
				heroi.setArmadura(rs.getDouble("armadura"));
				heroi.setResistenciamagica(rs.getDouble("resistenciamagica"));
				heroi.setCusto(rs.getInt("custo"));
				heroi.setClasse(Classe.valueOf(rs.getInt("classe")));
				heroi.setOrigem(Origem.valueOf(rs.getInt("origem")));

				listaheroi.add(heroi);
			}

			if (listaheroi.isEmpty())
				return null;
			return listaheroi;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Heroi> findByNome(String nome) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  vida, " + "  mana, "
					+ "  habilidade, " + "  dano, " + "  velocidade, " + "  esquiva, " + "  critico, " + "  armadura, "
					+ "  resistenciamagica, " + "  custo, " + "  classe, " + "  origem " + "FROM " + "  public.heroi "
					+ "WHERE " + "  nome ilike ? ");

			stat.setString(1, nome == null ? "%" : "%" + nome + "%");
			ResultSet rs = stat.executeQuery();

			List<Heroi> listaheroi = new ArrayList<Heroi>();

			while (rs.next()) {
				Heroi heroi = new Heroi();
				heroi.setId(rs.getInt("id"));
				heroi.setNome(rs.getString("nome"));
				heroi.setVida(rs.getDouble("vida"));
				heroi.setMana(rs.getDouble("mana"));
				heroi.setHabilidade(rs.getDouble("habilidade"));
				heroi.setDano(rs.getDouble("dano"));
				heroi.setVelocidade(rs.getDouble("velocidade"));
				heroi.setEsquiva(rs.getDouble("esquiva"));
				heroi.setCritico(rs.getDouble("critico"));
				heroi.setArmadura(rs.getDouble("armadura"));
				heroi.setResistenciamagica(rs.getDouble("resistenciamagica"));
				heroi.setCusto(rs.getInt("custo"));
				heroi.setClasse(Classe.valueOf(rs.getInt("classe")));
				heroi.setOrigem(Origem.valueOf(rs.getInt("origem")));

				listaheroi.add(heroi);
			}

			if (listaheroi.isEmpty())
				return null;
			return listaheroi;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Heroi findById(Integer id) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  id, " + "  nome, " + "  vida, " + "  mana, "
					+ "  habilidade, " + "  dano, " + "  velocidade, " + "  esquiva, " + "  critico, " + "  armadura, "
					+ "  resistenciamagica, " + "  custo, " + "  classe, " + "  origem " + "FROM " + "  public.heroi "
					+ "WHERE id = ? ");

			stat.setInt(1, id);

			ResultSet rs = stat.executeQuery();

			Heroi heroi = null;

			if (rs.next()) {
				heroi = new Heroi();
				heroi.setId(rs.getInt("id"));
				heroi.setNome(rs.getString("nome"));
				heroi.setVida(rs.getDouble("vida"));
				heroi.setMana(rs.getDouble("mana"));
				heroi.setHabilidade(rs.getDouble("habilidade"));
				heroi.setDano(rs.getDouble("dano"));
				heroi.setVelocidade(rs.getDouble("velocidade"));
				heroi.setEsquiva(rs.getDouble("esquiva"));
				heroi.setCritico(rs.getDouble("critico"));
				heroi.setArmadura(rs.getDouble("armadura"));
				heroi.setResistenciamagica(rs.getDouble("resistenciamagica"));
				heroi.setCusto(rs.getInt("custo"));
				heroi.setClasse(Classe.valueOf(rs.getInt("classe")));
				heroi.setOrigem(Origem.valueOf(rs.getInt("origem")));
			}

			return heroi;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) throws SQLException {

		Connection conn = getConnection();
		// deletando o nascimento (pq possui um relacionamento de fk)
		// passando o conn para manter a mesma transacao
		ItemComposicaoDAO dao = new ItemComposicaoDAO(conn);
		dao.delete(id);

		// deletando o usuario
		PreparedStatement stat = conn.prepareStatement("DELETE FROM public.heroi WHERE id = ?");
		stat.setInt(1, id);

		stat.execute();

	}

}
