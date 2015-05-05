package fr.istic.server;

import java.util.Calendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import fr.istic.client.ServicePerson;
import fr.istic.shared.Address;
import fr.istic.shared.Home;
import fr.istic.shared.Person;

public class ServicePersonImpl extends RemoteServiceServlet implements ServicePerson {

		/**
	 * 
	 */
	private static final long serialVersionUID = -3303214357108854485L;

		EntityManager manager;
		
		EntityManagerFactory factory;// = Persistence.createEntityManagerFactory("example");
		//EntityManager manager = factory.createEntityManager();
		
		
		@Override
		public void init() throws ServletException {
			super.init();
			manager = factory.createEntityManager();
			
		}
	

		
		@Override
		public Home addHome(Address adresse, int superficie, String adresseIP, int nbpiece, Person person){
	     Home h = new Home(adresse, superficie, adresseIP, nbpiece, person);
			
			EntityTransaction t =  manager.getTransaction();
			if (!t.isActive())
				t.begin();
			manager.persist(h);
			t.commit();
			
			return h;
		}

		@Override
		public Person addPerson(String nom, String prenom, String genre, String mail,	Calendar dateNaiss, String profil) {
			Person p = new Person(nom, prenom, genre, mail, dateNaiss, profil);
			EntityTransaction t =  manager.getTransaction();
			if (!t.isActive())
				t.begin();
			manager.persist(p);
			t.commit();
			
			return p;
			
		}

		@Override
		public void deletePerson(long idPers) {
			EntityTransaction t =  manager.getTransaction();
			t.begin();
			manager.remove(manager.find(Person.class, idPers));
			t.commit();		
			
		}
	
}
