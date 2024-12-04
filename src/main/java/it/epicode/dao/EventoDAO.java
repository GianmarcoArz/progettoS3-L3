package it.epicode.dao;


import it.epicode.gestione_eventi.entity.Evento;
import jakarta.persistence.EntityManager;

import java.util.List;

public class EventoDAO {

    private EntityManager em;

    public EventoDAO (EntityManager em){
        this.em = em;;
    }

    public void insertEvento(Evento evento) {
        em.getTransaction().begin();
        em.persist(evento);
        em.getTransaction().commit();
    }

    public Evento findEventoById(Long id) {
        return em.find(Evento.class, id);
    }

    public void updateEvento(Evento evento) {
        em.getTransaction().begin();
        em.merge(evento);
        em.getTransaction().commit();
    }

    public void updateEvento(Long id, Evento evento) {
        Evento eReale = findEventoById(id);
        eReale.setTitolo(evento.getTitolo());
        eReale.setDataEvento(evento.getDataEvento());
        eReale.setDescrizione(evento.getDescrizione());
        eReale.setTipoEvento(evento.getTipoEvento());
        eReale.setNumeroMassimoPartecipanti(evento.getNumeroMassimoPartecipanti());

        em.getTransaction().begin();
        em.merge(eReale);
        em.getTransaction().commit();
    }

    public void deleteEvento(Long id) {
        Evento eReale = findEventoById(id);
        em.getTransaction().begin();
        em.remove(eReale);
        em.getTransaction().commit();
    }

    public void deleteEvento(Evento evento) {
        em.getTransaction().begin();
        em.remove(evento);
        em.getTransaction().commit();
    }

    public void insertAll(List<Evento> lista) {
        em.getTransaction().begin();
        for (Evento e : lista) {
            em.persist(e);
        }
        em.getTransaction().commit();
    }



}
