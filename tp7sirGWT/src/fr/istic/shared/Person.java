package fr.istic.shared;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.datanucleus.api.jpa.annotations.Extension;

@Entity
public class Person {
	
	
	private long idPers;
	private String nomPers;
	private String prenomPers;

	private String mail;
	private Calendar dateNaiss;
	private String profil;
	private String genre;
	private List<Person> amis = new ArrayList<Person>();
	
	private List<Home> maison = new ArrayList<Home>();
	

	public Person(String nom, String prenom, String genre, String mail, Calendar dateNaiss, String profil){
		this.setNomPers(nom);
		this.setPrenomPers(prenom);
		this.setGenre(genre);
		this.setMail(mail);
		this.setDateNaiss(dateNaiss);
		this.setProfil(profil);
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	public long getIdPers() {
		return idPers;
	}

	public void setIdPers(long idPers) {
		this.idPers = idPers;
	}
	
	public String getNomPers() {
		return nomPers;
	}

	public void setNomPers(String nomPers) {
		this.nomPers = nomPers;
	}
	
	public String getPrenomPers() {
		return prenomPers;
	}

	public void setPrenomPers(String prenomPres) {
		this.prenomPers = prenomPres;
	}
	
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	@Temporal(TemporalType.DATE)
	public Calendar getDateNaiss() {
		return dateNaiss;
	}

	public void setDateNaiss(Calendar dateNaiss) {
		this.dateNaiss = dateNaiss;
	}
	
	@Transient
	public String getProfil() {
		return profil;
	}

	public void setProfil(String profil) {
		this.profil = profil;
	}
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.PERSIST)
	public List<Home> getMaison() {
		return maison;
	}

	public void setMaison(List<Home> maison) {
		this.maison = maison;
	}

	@ManyToMany
	public List<Person> getAmis() {
		return amis;
	}

	public void setAmis(List<Person> amis) {
		this.amis = amis;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}
