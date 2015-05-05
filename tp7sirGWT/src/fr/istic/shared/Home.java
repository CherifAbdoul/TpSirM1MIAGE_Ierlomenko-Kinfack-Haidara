package fr.istic.shared;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.datanucleus.api.jpa.annotations.Extension;

@Entity
public class Home {

	private long idHome;
	private int superficie;
	private String adresseIP;
	private int nbpiece;
	private List<Device> equipements = new ArrayList<Device>();
	private Person person;
	
	
	@Embedded
    private Address adresse;
	

	//constructeur
	public Home(Address adresse, int superficie, String adresseIP, int nbpiece, Person person){
		this.adresse = adresse;
		this.setSuperficie(superficie);
		this.setAdresseIP(adresseIP);
		this.setNbpiece(nbpiece);
		this.setPerson(person);
	}

    
	public Home() {
	
	}
	
	public  Home(Address adresse, int superficie, String adresseIP, int nbpiece){
		this.adresse = adresse;
		this.setSuperficie(superficie);
		this.setAdresseIP(adresseIP);
		this.setNbpiece(nbpiece);
		
	}


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	public long getIdHome() {
		return idHome;
	}

	public void setIdHome(long idHome) {
		this.idHome = idHome;
	}

	
	public int getSuperficie() {
		return superficie;
	}
	public void setSuperficie(int superficie) {
		this.superficie = superficie;
	}

	
	public String getAdresseIP() {
		return adresseIP;
	}

	public void setAdresseIP(String adresseIP) {
		this.adresseIP = adresseIP;
	}
	
	@ManyToOne
	public Person getPerson() {
		return person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@OneToMany(mappedBy = "home", cascade = CascadeType.PERSIST)
	public List<Device> getEquipements() {
		return equipements;
	}

	public void setEquipements(List<Device> equipements) {
		this.equipements = equipements;
	}

	public void addDevice(Chauffage heater) {
		 Home h0 = new Home();
		 h0.addDevice(heater);
	}



	public int getNbpiece() {
		return nbpiece;
	}



	public void setNbpiece(int nbpiece) {
		this.nbpiece = nbpiece;
	}


}
