package br.unitins.wow.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.application.Session;
import br.unitins.wow.dao.ComposicaoDAO;
import br.unitins.wow.model.Usuario;
import br.unitins.wow.model.Composicao;

@Named
@ViewScoped
public class HistoricoComposicaoController implements Serializable {

	private static final long serialVersionUID = -8585030860902916284L;

	private List<Composicao> listaComposicao = null;

	public List<Composicao> getListaComposicao() {
		if (listaComposicao == null) {
			ComposicaoDAO dao = new ComposicaoDAO();
			Usuario usuario = (Usuario) Session.getInstance().getAttribute("usuarioLogado");
			listaComposicao = dao.findByUsuario(usuario.getId());
			if (listaComposicao == null)
				listaComposicao = new ArrayList<Composicao>();
			dao.closeConnection();
		}
		return listaComposicao;
	}

	public String detalhes(Composicao composicao) {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.put("detalheComposicao", composicao);

		return "detalhescomposicao.xhtml?faces-redirect=true";
	}

}
