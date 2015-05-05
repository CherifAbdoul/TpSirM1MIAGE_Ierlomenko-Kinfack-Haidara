package fr.istic.client;

import java.util.Calendar;

import fr.istic.shared.Address;
import fr.istic.shared.Home;
import fr.istic.shared.Person;

public interface ServicePerson {

	Person addPerson(String nom, String prenom, String genre, String mail, Calendar dateNaiss, String profil);
	Home addHome(Address adresse, int superficie, String adresseIP, int nbpiece, Person person);
	void deletePerson(long idPers);
	
}
