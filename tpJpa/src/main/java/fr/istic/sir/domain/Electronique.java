package fr.istic.sir.domain;


import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * A Electronique.
 */
@Entity
@DiscriminatorValue("Electronic")
public class Electronique extends Appareil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 170420153L;

}
