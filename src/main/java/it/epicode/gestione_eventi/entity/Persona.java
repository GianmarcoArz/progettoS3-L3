package it.epicode.gestione_eventi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "persone")



public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false)
    private String email;




    @Column(name = "data_di_nascita",nullable = false)
    private LocalDate dataDiNascita;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private SessoEnum sesso;

    @OneToMany(mappedBy = "persona")
    private List<Partecipazione> listaPartecipazioni = new ArrayList<>();


}
