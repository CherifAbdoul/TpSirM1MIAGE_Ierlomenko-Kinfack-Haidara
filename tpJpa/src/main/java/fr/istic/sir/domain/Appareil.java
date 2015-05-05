package fr.istic.sir.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Appareil.
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE")
@Table(name = "T_APPAREIL")
public class Appareil implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 170420151L;

	/**
	 * 
	 */
	//private static final long serialVersionUID = 6043675606453288511L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nomapp")
    private String nomapp;

    @Column(name = "conso")
    private String conso;

    @ManyToOne
    private Maison maison;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomapp() {
        return nomapp;
    }

    public void setNomapp(String nomapp) {
        this.nomapp = nomapp;
    }

    public String getConso() {
        return conso;
    }

    public void setConso(String conso) {
        this.conso = conso;
    }

    public Maison getMaison() {
        return maison;
    }

    public void setMaison(Maison maison) {
        this.maison = maison;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Appareil appareil = (Appareil) o;

        if ( ! Objects.equals(id, appareil.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Appareil{" +
                "id=" + id +
                ", nomapp='" + nomapp + "'" +
                ", conso='" + conso + "'" +
                '}';
    }
}
