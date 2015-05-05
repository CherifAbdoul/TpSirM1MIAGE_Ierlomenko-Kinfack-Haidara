package fr.istic.sir.domain;


import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A Chauffage.
 */
@Entity
@DiscriminatorValue("Heater")
public class Chauffage extends Appareil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 170420152L;

}
