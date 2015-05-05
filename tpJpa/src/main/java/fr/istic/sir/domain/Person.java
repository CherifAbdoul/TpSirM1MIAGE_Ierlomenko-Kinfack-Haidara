package fr.istic.sir.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.istic.sir.domain.util.CustomLocalDateSerializer;
import fr.istic.sir.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Personne.
 */
@Entity
@Table(name = "T_PERSONNE")
public class Person implements Serializable {

	private static final long serialVersionUID = -4978667278374344497L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "datenais")
    private LocalDate datenais;

    @Column(name = "mail")
    private String mail;

    @Column(name = "profil")
    private String profil;

    @OneToMany(mappedBy = "personne")
    @JsonIgnore
    private Set<Maison> residencess = new HashSet<>();
    
    @ManyToMany
    private Set<Person> friends = new HashSet<>();

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public LocalDate getDatenais() {
        return datenais;
    }

    public void setDatenais(LocalDate datenais) {
        this.datenais = datenais;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
    }

    public Set<Maison> getResidencess() {
        return residencess;
    }

    public void setResidencess(Set<Maison> maisons) {
        this.residencess = maisons;
    }
    
    public Set<Person> getFriends() {
		return friends;
	}

	public void setFriends(Set<Person> friends) {
		this.friends = friends;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Person personne = (Person) o;

        if ( ! Objects.equals(id, personne.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + "'" +
                ", prenom='" + prenom + "'" +
                ", datenais='" + datenais + "'" +
                ", mail='" + mail + "'" +
                ", profil='" + profil + "'" +
                '}';
    }
}
