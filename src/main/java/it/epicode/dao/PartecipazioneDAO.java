package it.epicode.dao;

import it.epicode.gestione_eventi.entity.Partecipazione;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PartecipazioneDAO {
    private EntityManager em;

    public PartecipazioneDAO (EntityManager em){
        this.em = em;;
    }

    public void insertPartecipazione(Partecipazione partecipazione) {
        em.getTransaction().begin();
        em.persist(partecipazione);
        em.getTransaction().commit();
    }

    public Partecipazione findPartecipazioneById(Long id) {
        return em.find(Partecipazione.class, id);
    }

    public void updatePartecipazione(Partecipazione partecipazione) {
        em.getTransaction().begin();
        em.merge(partecipazione);
        em.getTransaction().commit();
    }

    public void updatePartecipazione(Long id, Partecipazione partecipazione) {
        Partecipazione pReale = findPartecipazioneById(id);
        pReale.setId(partecipazione.getId());
        pReale.setStato(partecipazione.getStato());
        pReale.setEvent(partecipazione.getEvent());
        pReale.setPersona(partecipazione.getPersona());


        em.getTransaction().begin();
        em.merge(pReale);
        em.getTransaction().commit();
    }

    public void deletePartecipazione(Long id) {
        Partecipazione pReale = findPartecipazioneById(id);
        em.getTransaction().begin();
        em.remove(pReale);
        em.getTransaction().commit();
    }

    public void deletePartecipazione(Partecipazione partecipazione) {
        em.getTransaction().begin();
        em.remove(partecipazione);
        em.getTransaction().commit();
    }

    public void insertAll(List<Partecipazione> lista) {
        em.getTransaction().begin();
        for (Partecipazione e : lista) {
            em.persist(e);
        }
        em.getTransaction().commit();
    }


}
