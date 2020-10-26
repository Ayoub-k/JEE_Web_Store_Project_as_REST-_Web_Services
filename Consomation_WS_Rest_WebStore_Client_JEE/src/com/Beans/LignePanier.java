package com.Beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.Beans.Livre;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class LignePanier implements Serializable{

	private Livre livre;
	private int quantite;

	public LignePanier(Livre art, int qte) {
		this.livre = art;
		this.quantite = qte;
	}

	public LignePanier() {
		super();
	}

	public Livre getLivre() {
		return livre;
	}

	public void setLivre(Livre livre) {
		this.livre = livre;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

}
