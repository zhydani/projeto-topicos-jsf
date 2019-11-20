package br.unitins.wow.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.unitins.wow.model.ItemComposicao;
import br.unitins.wow.model.Origem;
import br.unitins.wow.model.Heroi;
import br.unitins.wow.model.Classe;
import br.unitins.wow.model.Composicao;

public class ItemComposicaoDAO extends DAO<ItemComposicao> {

	public ItemComposicaoDAO(Connection conn) {
		super(conn);
	}

	public ItemComposicaoDAO() {
		super(null);
	}

	@Override
	public void create(ItemComposicao itemComposicao) throws SQLException {

		Connection conn = getConnection();

		PreparedStatement stat = conn.prepareStatement("INSERT INTO " + "public.itemcomposicao "
				+ " (valor, idcomposicao, idheroi) " + "VALUES " + " (?, ?, ?) ");
		stat.setFloat(1, itemComposicao.getValor());
		stat.setInt(2, itemComposicao.getComposicao().getId());
		stat.setInt(3, itemComposicao.getHeroi().getId());

		stat.execute();

	}

	@Override
	public void update(ItemComposicao itemComposicao) throws SQLException {
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
	public List<ItemComposicao> findAll() {
		return null;
	}

	public List<ItemComposicao> findByComposicao(Composicao composicao) {
		Connection conn = getConnection();
		if (conn == null)
			return null;

		try {
			PreparedStatement stat = conn.prepareStatement("SELECT " + "  c.id, " + "  c.valor, " + "  c.idheroi, "
					+ "  h.nome, " + "  h.vida, " + "  h.mana, " + "  h.habilidade, " + "  h.dano, "
					+ "  h.velocidade, " + "  h.esquiva, " + "  h.critico, " + "  h.armadura, "
					+ "  h.resistenciamagica, " + "  h.custo as custoHeroi, " + "  h.classe, " + "  h.origem " + "FROM "
					+ "  public.itemcomposicao c, " + "  public.heroi h " + "WHERE " + "  c.idheroi = h.id AND "
					+ "  c.idcomposicao = ? ");

			stat.setInt(1, composicao.getId());
			ResultSet rs = stat.executeQuery();

			List<ItemComposicao> listaItemComposicao = new ArrayList<ItemComposicao>();

			while (rs.next()) {
				ItemComposicao item = new ItemComposicao();
				item.setId(rs.getInt("id"));
				item.setValor(rs.getInt("valor"));

				Heroi heroi = new Heroi();
				heroi.setId(rs.getInt("idheroi"));
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
				heroi.setCusto(rs.getInt("custoHeroi"));
				heroi.setClasse(Classe.valueOf(rs.getInt("classe")));
				heroi.setOrigem(Origem.valueOf(rs.getInt("origem")));

				item.setHeroi(heroi);

				item.setComposicao(composicao);

				listaItemComposicao.add(item);
			}

			if (listaItemComposicao.isEmpty())
				return null;
			return listaItemComposicao;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
