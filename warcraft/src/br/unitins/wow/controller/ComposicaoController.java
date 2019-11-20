package br.unitins.wow.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.application.Session;
import br.unitins.wow.application.Util;
import br.unitins.wow.dao.HeroiDAO;
import br.unitins.wow.dao.ItemDAO;
import br.unitins.wow.dao.UsuarioDAO;
import br.unitins.wow.model.ItemComposicao;
import br.unitins.wow.model.Usuario;
import br.unitins.wow.model.Heroi;
import br.unitins.wow.model.Item;

@Named
@ViewScoped
public class ComposicaoController implements Serializable {

	private static final long serialVersionUID = -8413311224021825448L;

	private Heroi heroi;
	private String nome;
	private List<Heroi> listaHeroi = null;

	public void pesquisar() {
		listaHeroi = null;
	}

	public void adicionar(int idHeroi) {
		HeroiDAO dao = new HeroiDAO();
		Heroi heroi = dao.findById(idHeroi);
		// verifica se existe um partida na sessao
		if (Session.getInstance().getAttribute("partida") == null) {
			// adiciona um partida (de itens de venda) na sessao
			Session.getInstance().setAttribute("partida", new ArrayList<ItemComposicao>());
		}

		// obtendo o partida da sessao
		List<ItemComposicao> partida = (ArrayList<ItemComposicao>) Session.getInstance().getAttribute("partida");

		// criando um item de venda para adicionar no partida
		ItemComposicao i = new ItemComposicao();
		i.setHeroi(heroi);
		i.setValor(heroi.getCusto());

		// adicionando o item no partida (variavel local)
		partida.add(i);

		// atualizando o partida na sessao
		Session.getInstance().setAttribute("partida", partida);

		Util.addMessageInfo("Heroi adicionado no partida. " + "Quantidade: " + partida.size());

	}

	public List<Heroi> getListaHeroi() {
		if (listaHeroi == null) {
			HeroiDAO hd = new HeroiDAO();
			listaHeroi = hd.findByNome(getNome());
			if (listaHeroi == null)
				listaHeroi = new ArrayList<Heroi>();
			hd.closeConnection();
		}
		return listaHeroi;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String foto(String ori) {
		if(ori == "ELFOSANGRENTO") {
			return "es.jpg";
		}
		if(ori == "HUMANO") {
			return "humano.jpg";
		}
		if(ori == "ANAO") {
			return "anao.png";
		}
		if(ori == "ELFONOTURNO") {
			return "en.png";
		}
		if(ori == "GNOMO") {
			return "en.png";
		}
		return "usu.png";

	}

	public Heroi getHeroi() {
		return heroi;
	}

	public void setHeroi(Heroi heroi) {
		this.heroi = heroi;
	}

}
