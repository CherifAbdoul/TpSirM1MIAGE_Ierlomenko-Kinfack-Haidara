package fr.istic.shared;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import org.datanucleus.api.jpa.annotations.Extension;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Device {
	
	private String nomDevice;
	private int id;
	private String consomEnWatt;
	private Home home;

	//not necessary
	public Device(){
		
	}
	
	public String getNomDevice() {
		return nomDevice;
	}

	public void setNomDevice(String nomDevice) {
		this.nomDevice = nomDevice;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getConsomEnWatt() {
		return consomEnWatt;
	}

	public void setConsomEnWatt(String consomEnWatt) {
		this.consomEnWatt = consomEnWatt;
	}
	@ManyToOne
	public Home getHome() {
		return home;
	}

	public void setHome(Home home) {
		this.home = home;
	}

}
