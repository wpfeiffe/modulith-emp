package com.wspfeiffer.modulithemp.league.internal;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.util.Objects;

/**
 * A League.
 */
@Entity
@Table(name = "league", schema = "team")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class League implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "league_id_seq")
    @SequenceGenerator(name = "league_id_seq", sequenceName = "league_id_seq", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(min = 5, max = 60)
    @Column(name = "league_name", length = 60, nullable = false)
    private String leagueName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public League leagueName(String leagueName) {
        this.leagueName = leagueName;
        return this;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        League league = (League) o;
        if (league.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, league.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "League{" +
            "id=" + id +
            ", leagueName='" + leagueName + "'" +
            '}';
    }
}
