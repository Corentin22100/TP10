package domain;

import jakarta.persistence.*;

@Entity
public class Statistiques {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int nbTicketsVendus;
    private float revenusGeneres;

    @OneToOne
    @JoinColumn(name = "evenement_id")
    private Evenement evenement;
}