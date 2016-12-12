/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.controller;

import com.maxroland.mavenfx.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.maxroland.mavenfx.modelo.Persona;
import com.maxroland.mavenfx.modelo.TelPerso;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rolando
 */
public class TelPersoJpaController implements Serializable {

    public TelPersoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TelPerso telPerso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = telPerso.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                telPerso.setPersonaId(personaId);
            }
            em.persist(telPerso);
            if (personaId != null) {
                personaId.getTelPersoList().add(telPerso);
                personaId = em.merge(personaId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TelPerso telPerso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelPerso persistentTelPerso = em.find(TelPerso.class, telPerso.getId());
            Persona personaIdOld = persistentTelPerso.getPersonaId();
            Persona personaIdNew = telPerso.getPersonaId();
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                telPerso.setPersonaId(personaIdNew);
            }
            telPerso = em.merge(telPerso);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getTelPersoList().remove(telPerso);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getTelPersoList().add(telPerso);
                personaIdNew = em.merge(personaIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = telPerso.getId();
                if (findTelPerso(id) == null) {
                    throw new NonexistentEntityException("The telPerso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TelPerso telPerso;
            try {
                telPerso = em.getReference(TelPerso.class, id);
                telPerso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The telPerso with id " + id + " no longer exists.", enfe);
            }
            Persona personaId = telPerso.getPersonaId();
            if (personaId != null) {
                personaId.getTelPersoList().remove(telPerso);
                personaId = em.merge(personaId);
            }
            em.remove(telPerso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TelPerso> findTelPersoEntities() {
        return findTelPersoEntities(true, -1, -1);
    }

    public List<TelPerso> findTelPersoEntities(int maxResults, int firstResult) {
        return findTelPersoEntities(false, maxResults, firstResult);
    }

    private List<TelPerso> findTelPersoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TelPerso.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TelPerso findTelPerso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TelPerso.class, id);
        } finally {
            em.close();
        }
    }

    public int getTelPersoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TelPerso> rt = cq.from(TelPerso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
