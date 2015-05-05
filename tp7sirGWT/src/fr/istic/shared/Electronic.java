package fr.istic.shared;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("E")
public class Electronic extends Device {

	public Electronic() {
	}	
}
