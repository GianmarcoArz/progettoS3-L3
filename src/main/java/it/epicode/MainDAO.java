package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.dao.*;
import it.epicode.dao.EventoDAO;
import it.epicode.dao.LocationDAO;
import it.epicode.dao.PartecipazioneDAO;
import it.epicode.dao.PersonaDAO;

import it.epicode.gestione_eventi.entity.*;
import it.epicode.gestione_eventi.entity.Concerto;
import it.epicode.gestione_eventi.entity.EventoEnum;
import it.epicode.gestione_eventi.entity.GaraDiAtletica;
import it.epicode.gestione_eventi.entity.PartitaDiCalcio;



import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.*;

public class MainDAO {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

        EntityManager em = emf.createEntityManager();

        Faker faker = new Faker(new Locale("it"));

        //Persona
        List<Persona> listaPersone = new ArrayList<>();
        Set<Persona> atleti = new HashSet<>();
        it.epicode.dao.PersonaDAO personaDAO = new PersonaDAO(em);


        for (int i = 0; i < 20; i++) {
            Persona persona = new Persona();
            persona.setNome(faker.name().firstName());
            persona.setCognome(faker.name().lastName());
            persona.setEmail(faker.internet().emailAddress());
            persona.setDataDiNascita(LocalDate.now().minusYears(faker.number().numberBetween(18, 40)));
            persona.setSesso(faker.options().option(SessoEnum.class));
            listaPersone.add(persona);
        }

        personaDAO.insertAll(listaPersone);

        for (int i = 0; i < 10; i++) {
            atleti.add(listaPersone.get(i));
        }

        //Location
        List<Location> listaLocation = new ArrayList<>();
        LocationDAO locationDAO = new LocationDAO(em);

        for (int i = 0; i < 10; i++) {
            Location location = new Location();

            location.setNome(faker.company().name());
            location.setCitta(faker.address().city());
            listaLocation.add(location);
        }

        locationDAO.insertAll(listaLocation);

        //Eventi

        List<Evento> listaConcerti = new ArrayList<>();
        EventoDAO eventoDAO = new EventoDAO(em);

        for (int i = 0; i < 10; i++) {
            Concerto concerto = new Concerto();
            concerto.setTitolo(faker.rockBand().name());
            concerto.setDataEvento(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));
            concerto.setDescrizione("Concertone");
            concerto.setTipoEvento(faker.options().option(EventoEnum.class));
            concerto.setNumeroMassimoPartecipanti(faker.number().numberBetween(50, 10000));
            concerto.setGenere(faker.options().option(Genere.class));
            concerto.setInStreaming(faker.bool().bool());

            listaConcerti.add(concerto);
        }

        List<Evento> listaGareDiAtletica = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            GaraDiAtletica garaDiAtletica = new GaraDiAtletica();
            garaDiAtletica.setTitolo(faker.esports().event());
            garaDiAtletica.setDataEvento(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));
            garaDiAtletica.setDescrizione("Garona di Atletica");
            garaDiAtletica.setTipoEvento(faker.options().option(EventoEnum.class));
            garaDiAtletica.setNumeroMassimoPartecipanti(faker.number().numberBetween(10, 100));

            garaDiAtletica.setAtleti(atleti);
            garaDiAtletica.setVincitore(faker.options().nextElement(listaPersone));
            listaGareDiAtletica.add(garaDiAtletica);

        }

        List<Evento> listaPartiteDiCalcio = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            PartitaDiCalcio partitaDiCalcio = new PartitaDiCalcio();
            partitaDiCalcio.setTitolo(faker.team().name());
            partitaDiCalcio.setDataEvento(LocalDate.now().plusDays(faker.number().numberBetween(1, 365)));
            partitaDiCalcio.setDescrizione("Partitona di Calcio");
            partitaDiCalcio.setTipoEvento(faker.options().option(EventoEnum.class));
            partitaDiCalcio.setNumeroMassimoPartecipanti(faker.number().numberBetween(50, 50000));
            partitaDiCalcio.setSquadraDiCasa(faker.team().name());
            partitaDiCalcio.setSquadraOspite(faker.team().name());
            partitaDiCalcio.setGolSquadraDiCasa(faker.number().numberBetween(0, 7));
            partitaDiCalcio.setGolSquadraOspite(faker.number().numberBetween(0, 7));

            if (partitaDiCalcio.getGolSquadraDiCasa() > partitaDiCalcio.getGolSquadraOspite()) {
                partitaDiCalcio.setSquadraVincente(partitaDiCalcio.getSquadraDiCasa());

            } else if (partitaDiCalcio.getGolSquadraDiCasa() < partitaDiCalcio.getGolSquadraOspite()) {
                partitaDiCalcio.setSquadraVincente(partitaDiCalcio.getSquadraOspite());
            } else {
                partitaDiCalcio.setSquadraVincente("Pareggio");
            }

            listaPartiteDiCalcio.add(partitaDiCalcio);
        }

        eventoDAO.insertAll(listaConcerti);
        eventoDAO.insertAll(listaGareDiAtletica);
        eventoDAO.insertAll(listaPartiteDiCalcio);


        //Partecipazione
        List<Partecipazione> listaPartecipazioni = new ArrayList<>();

        PartecipazioneDAO partecipazioneDAO = new PartecipazioneDAO(em);

        for (int i = 0; i < 20; i++) {
            Partecipazione partecipazione = new Partecipazione();
            partecipazione.setStato(faker.options().option(StatoPartecipazioneEnum.class));
            partecipazione.setEvent(faker.options().nextElement(listaConcerti));
            partecipazione.setPersona(faker.options().nextElement(listaPersone));
            listaPartecipazioni.add(partecipazione);
        }

        for (int i = 0; i < 10; i++) {
            Partecipazione partecipazione2 = new Partecipazione();
            partecipazione2.setStato(faker.options().option(StatoPartecipazioneEnum.class));
            partecipazione2.setEvent(faker.options().nextElement(listaGareDiAtletica));
            partecipazione2.setPersona(faker.options().nextElement(listaPersone));
            listaPartecipazioni.add(partecipazione2);
        }

        for (int i = 0; i < 5; i++) {
            Partecipazione partecipazione3 = new Partecipazione();
            partecipazione3.setStato(faker.options().option(StatoPartecipazioneEnum.class));
            partecipazione3.setEvent(faker.options().nextElement(listaPartiteDiCalcio));
            partecipazione3.setPersona(faker.options().nextElement(listaPersone));
            listaPartecipazioni.add(partecipazione3);
        }

        partecipazioneDAO.insertAll(listaPartecipazioni);


        em.close();
        emf.close();
    }

}