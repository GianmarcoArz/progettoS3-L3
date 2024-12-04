package it.epicode.dao;

import it.epicode.gestione_eventi.entity.Persona;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PersonaDAO {



    private EntityManager em;

    public PersonaDAO (EntityManager em){
        this.em = em;;
    }

    public void insertPersona(Persona persona) {
        em.getTransaction().begin();
        em.persist(persona);
        em.getTransaction().commit();
    }

    public Persona findPersonaById(Long id) {
        return em.find(Persona.class, id);
    }

    public void updatePersona(Persona persona) {
        em.getTransaction().begin();
        em.merge(persona);
        em.getTransaction().commit();
    }

    public void updatePersona(Long id, Persona persona) {
        Persona pReale = findPersonaById(id);
        pReale.setNome(persona.getNome());
        pReale.setCognome(persona.getCognome());
        pReale.setEmail(persona.getEmail());


        em.getTransaction().begin();
        em.merge(pReale);
        em.getTransaction().commit();
    }

    public void deletePersona(Long id) {
        Persona pReale = findPersonaById(id);
        em.getTransaction().begin();
        em.remove(pReale);
        em.getTransaction().commit();
    }

    public void deletePersona(Persona persona) {
        em.getTransaction().begin();
        em.remove(persona);
        em.getTransaction().commit();
    }

    public void insertAll(List<Persona> lista) {
        em.getTransaction().begin();
        for (Persona e : lista) {
            em.persist(e);
        }
        em.getTransaction().commit();
    }



}
