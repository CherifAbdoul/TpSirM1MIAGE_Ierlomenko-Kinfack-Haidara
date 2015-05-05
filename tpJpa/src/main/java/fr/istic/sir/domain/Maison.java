package fr.istic.sir.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Maison.
 */
@Entity
@Table(name = "T_MAISON")
public class Maison implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -781132821239135975L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "adresse")
    private String adresse;

    @Column(name = "adresseip")
    private String adresseip;

    @Column(name = "superficie", precision=10, scale=2)
    private BigDecimal superficie;

    @Column(name = "nbPiece", precision=10, scale=2)
    private BigDecimal nbPiece;

    @OneToMany(mappedBy = "maison")
    @JsonIgnore
    private Set<Appareil> appareilss = new HashSet<>();

    @ManyToOne
    private Person personne;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getAdresseip() {
        return adresseip;
    }

    public void setAdresseip(String adresseip) {
        this.adresseip = adresseip;
    }

    public BigDecimal getSuperficie() {
        return superficie;
    }

    public void setSuperficie(BigDecimal superficie) {
        this.superficie = superficie;
    }

    public BigDecimal getNbPiece() {
        return nbPiece;
    }

    public void setNbPiece(BigDecimal nbPiece) {
        this.nbPiece = nbPiece;
    }

    public Set<Appareil> getAppareilss() {
        return appareilss;
    }

    public void setAppareilss(Set<Appareil> appareils) {
        this.appareilss = appareils;
    }

    public Person getPersonne() {
        return personne;
    }

    public void setPersonne(Person personne) {
        this.personne = personne;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Maison maison = (Maison) o;

        if ( ! Objects.equals(id, maison.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override 
    public String toString() {
        return "Maison{" +
                "id=" + id +
                ", adresse='" + adresse + "'" +
                ", adresseip='" + adresseip + "'" +
                ", superficie='" + superficie + "'" +
                ", nombrepiece='" + nbPiece + "'" +
                '}';
    }
}
