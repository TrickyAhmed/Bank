package tn.iit.entities;

import java.io.Serializable;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Entity
@Table(name = "t_compte")
public class Compte implements Serializable {

    @Id
    @Include
    private Integer rib;

    @Column(name = "solde")
    private float solde;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    // Corrected constructor (no nomClient)
    public Compte(int rib, float solde, Client client) {
        this.rib = rib;
        this.solde = solde;
        this.client = client;
    }
}
