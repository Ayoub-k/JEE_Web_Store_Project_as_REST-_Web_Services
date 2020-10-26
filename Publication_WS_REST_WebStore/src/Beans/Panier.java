package Beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class Panier implements Serializable{


	 private int id;
	 private String nom_livre;
	 private Double prix_livre;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getNom_livre() {
		return nom_livre;
	}
	public void setNom_livre(String nom_livre) {
		this.nom_livre = nom_livre;
	}
	public Double getPrix_livre() {
		return prix_livre;
	}
	public void setPrix_livre(Double prix_livre) {
		this.prix_livre = prix_livre;
	}
	public Panier(int id, String nom_livre, Double prix_livre) {
		super();
		this.id = id;

		this.nom_livre = nom_livre;
		this.prix_livre = prix_livre;
	}
	public Panier() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 
}
