package fr.istic.client;

import java.util.Calendar;

import com.google.gwt.user.client.rpc.AsyncCallback;

import fr.istic.shared.Address;
import fr.istic.shared.Home;
import fr.istic.shared.Person;

public interface ServicePersonAsync {

	void addPerson(String nom, String prenom, String mail, Calendar dateNaiss, String profil, AsyncCallback<Person> callback);

	void deletePerson(long idPers, AsyncCallback<Void> callback);

	void addHome(Address adresse, int superficie, String adresseIP, int nbpiece, Person person,
			AsyncCallback<Home> callback);


	
	
}
