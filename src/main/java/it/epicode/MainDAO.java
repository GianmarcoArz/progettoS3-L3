package it.epicode;

import com.github.javafaker.Faker;
import it.epicode.dao.EventoDAO;
import it.epicode.gestione_eventi.entity.Evento;
import it.epicode.gestione_eventi.entity.EventoEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainDAO {

    public static void main(String[] args) {

        Faker faker = new Faker(new Locale("it"));


        EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

        EntityManager em = emf.createEntityManager();

        EventoDAO eventoDAO = new EventoDAO(em);
        Evento evento1 = new Evento();

        evento1.setTitolo("Notte Serena");
        evento1.setDataEvento(faker.date().future(10, java.util.concurrent.TimeUnit.DAYS));
        evento1.setDescrizione("Main event tonight for us");
        evento1.setTipoEvento(EventoEnum.PUBBLICO);
        evento1.setNumeroMassimoPartecipanti(150);



        eventoDAO.insertEvento(evento1);

        em.close();
    }

}
