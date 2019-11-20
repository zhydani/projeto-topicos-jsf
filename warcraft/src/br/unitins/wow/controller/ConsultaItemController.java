package br.unitins.wow.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.dao.ItemDAO;
import br.unitins.wow.model.Item;

@Named
@ViewScoped
public class ConsultaItemController implements Serializable {

	private static final long serialVersionUID = -5361541934905883966L;

	private String nome;
	private List<Item> listaItem = null;

	public void pesquisar() {
		listaItem = null;

	}

	public String editar(int id) {
		ItemDAO dao = new ItemDAO();
		// buscando um usuario pelo id
		Item item = dao.findById(id);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("itemFlash", item);

		return "item.xhtml?faces-redirect=true";
	}

	public List<Item> getListaItem() {
		if (listaItem == null) {
			ItemDAO dao = new ItemDAO();
			listaItem = dao.findByNome(getNome());
			if (listaItem == null)
				listaItem = new ArrayList<Item>();
			dao.closeConnection();
		}
		return listaItem;
	}
	
	public String goCadastroPage() {
		return "item.xhtml?faces-redirect=true";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
