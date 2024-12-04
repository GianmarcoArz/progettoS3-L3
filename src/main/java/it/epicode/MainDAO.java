package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.dao.EventoDAO;
import it.epicode.dao.LocationDAO;
import it.epicode.dao.PartecipazioneDAO;
import it.epicode.dao.PersonaDAO;
import it.epicode.gestione_eventi.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainDAO {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

        EntityManager em = emf.createEntityManager();

        Faker faker = new Faker(new Locale("it"));

        //Persona
        PersonaDAO personaDAO = new PersonaDAO(em);
        Persona persona = new Persona();
        persona.setNome("Andrea");
        persona.setCognome("La Ventura");
        persona.setEmail("andreafocoso@gmail.com");
        persona.setDataDiNascita(LocalDate.of(2003, 01, 15));
        persona.setSesso(SessoEnum.F);
//        persona.setListaPartecipazioni();

        personaDAO.insertPersona(persona);

        //Location
        LocationDAO locationDAO = new LocationDAO(em);
        Location location = new Location();

        location.setNome("Palasport");
        location.setCitta("Milano");

        locationDAO.insertLocation(location);

        //Evento
        EventoDAO eventoDAO = new EventoDAO(em);
        Evento evento = new Evento();
        evento.setTitolo("Epicode Party");
        evento.setDataEvento(LocalDate.of(2024,12,1));
        evento.setDescrizione("Super mega party epicode,sarÃ uno sballo!");
        evento.setTipoEvento(EventoEnum.PUBBLICO);
        evento.setNumeroMassimoPartecipanti(1000);
        evento.setLocation(location);

//        evento.setListaPartecipazioni();

        eventoDAO.insertEvento(evento);

        //Partecipazione
        PartecipazioneDAO partecipazioneDAO = new PartecipazioneDAO(em);
        Partecipazione partecipazione = new Partecipazione();
        Partecipazione partecipazione1 = new Partecipazione();

        partecipazione.setEvent(evento);
        partecipazione.setPersona(persona);
        partecipazione.setStato(StatoPartecipazioneEnum.CONFERMATA);
        partecipazione1.setEvent(evento);
        partecipazione1.setPersona(persona);
        partecipazione1.setStato(StatoPartecipazioneEnum.DA_CONFERMARE);

        partecipazioneDAO.insertPartecipazione(partecipazione);
        partecipazioneDAO.insertPartecipazione(partecipazione1);

        evento.getListaPartecipazioni().add(partecipazione);
        evento.getListaPartecipazioni().add(partecipazione1);

        em.close();
    }
}
