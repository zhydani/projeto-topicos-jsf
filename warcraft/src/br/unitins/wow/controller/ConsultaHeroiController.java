package br.unitins.wow.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.dao.HeroiDAO;
import br.unitins.wow.model.Heroi;

@Named
@ViewScoped
public class ConsultaHeroiController implements Serializable {

	private static final long serialVersionUID = -5361541934905883966L;

	private String nome;
	private List<Heroi> listaHeroi = null;

	public void pesquisar() {
		listaHeroi = null;

	}

	public String editar(int id) {
		HeroiDAO dao = new HeroiDAO();
		// buscando um usuario pelo id
		Heroi heroi = dao.findById(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("heroiFlash", heroi);

		return "heroi.xhtml?faces-redirect=true";
	}

	public List<Heroi> getListaHeroi() {
		if (listaHeroi == null) {
			HeroiDAO dao = new HeroiDAO();
			listaHeroi = dao.findByNome(getNome());
			if (listaHeroi == null)
				listaHeroi = new ArrayList<Heroi>();
			dao.closeConnection();
		}
		return listaHeroi;
	}
	
	public String goCadastroPage() {
		return "heroi.xhtml?faces-redirect=true";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
