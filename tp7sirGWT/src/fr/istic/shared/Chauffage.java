package fr.istic.shared;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("C")
public class Chauffage extends Device {
	

	public Chauffage() {
	}
	
}
