package br.unitins.wow.controller;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.unitins.wow.model.Composicao;

@Named
@ViewScoped
public class DetalheComposicaoController implements Serializable {

	private static final long serialVersionUID = -6719949836747729139L;

	private Composicao composicao;

	public DetalheComposicaoController() {
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.keep("detalheComposicao");
		composicao = (Composicao) flash.get("detalheComposicao");
	}

	public Composicao getComposicao() {
		return composicao;
	}

	public void setComposicao(Composicao composicao) {
		this.composicao = composicao;
	}

}
