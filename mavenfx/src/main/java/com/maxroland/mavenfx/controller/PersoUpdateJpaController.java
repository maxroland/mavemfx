/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.controller;

import com.maxroland.mavenfx.controller.exceptions.NonexistentEntityException;
import com.maxroland.mavenfx.modelo.PersoUpdate;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.maxroland.mavenfx.modelo.Persona;
import com.maxroland.mavenfx.modelo.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rolando
 */
public class PersoUpdateJpaController implements Serializable {

    public PersoUpdateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PersoUpdate persoUpdate) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona personaId = persoUpdate.getPersonaId();
            if (personaId != null) {
                personaId = em.getReference(personaId.getClass(), personaId.getId());
                persoUpdate.setPersonaId(personaId);
            }
            Usuario usuarioId = persoUpdate.getUsuarioId();
            if (usuarioId != null) {
                usuarioId = em.getReference(usuarioId.getClass(), usuarioId.getId());
                persoUpdate.setUsuarioId(usuarioId);
            }
            em.persist(persoUpdate);
            if (personaId != null) {
                personaId.getPersoUpdateList().add(persoUpdate);
                personaId = em.merge(personaId);
            }
            if (usuarioId != null) {
                usuarioId.getPersoUpdateList().add(persoUpdate);
                usuarioId = em.merge(usuarioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PersoUpdate persoUpdate) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PersoUpdate persistentPersoUpdate = em.find(PersoUpdate.class, persoUpdate.getId());
            Persona personaIdOld = persistentPersoUpdate.getPersonaId();
            Persona personaIdNew = persoUpdate.getPersonaId();
            Usuario usuarioIdOld = persistentPersoUpdate.getUsuarioId();
            Usuario usuarioIdNew = persoUpdate.getUsuarioId();
            if (personaIdNew != null) {
                personaIdNew = em.getReference(personaIdNew.getClass(), personaIdNew.getId());
                persoUpdate.setPersonaId(personaIdNew);
            }
            if (usuarioIdNew != null) {
                usuarioIdNew = em.getReference(usuarioIdNew.getClass(), usuarioIdNew.getId());
                persoUpdate.setUsuarioId(usuarioIdNew);
            }
            persoUpdate = em.merge(persoUpdate);
            if (personaIdOld != null && !personaIdOld.equals(personaIdNew)) {
                personaIdOld.getPersoUpdateList().remove(persoUpdate);
                personaIdOld = em.merge(personaIdOld);
            }
            if (personaIdNew != null && !personaIdNew.equals(personaIdOld)) {
                personaIdNew.getPersoUpdateList().add(persoUpdate);
                personaIdNew = em.merge(personaIdNew);
            }
            if (usuarioIdOld != null && !usuarioIdOld.equals(usuarioIdNew)) {
                usuarioIdOld.getPersoUpdateList().remove(persoUpdate);
                usuarioIdOld = em.merge(usuarioIdOld);
            }
            if (usuarioIdNew != null && !usuarioIdNew.equals(usuarioIdOld)) {
                usuarioIdNew.getPersoUpdateList().add(persoUpdate);
                usuarioIdNew = em.merge(usuarioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persoUpdate.getId();
                if (findPersoUpdate(id) == null) {
                    throw new NonexistentEntityException("The persoUpdate with id " + id + " no longer exists.");
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
            PersoUpdate persoUpdate;
            try {
                persoUpdate = em.getReference(PersoUpdate.class, id);
                persoUpdate.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persoUpdate with id " + id + " no longer exists.", enfe);
            }
            Persona personaId = persoUpdate.getPersonaId();
            if (personaId != null) {
                personaId.getPersoUpdateList().remove(persoUpdate);
                personaId = em.merge(personaId);
            }
            Usuario usuarioId = persoUpdate.getUsuarioId();
            if (usuarioId != null) {
                usuarioId.getPersoUpdateList().remove(persoUpdate);
                usuarioId = em.merge(usuarioId);
            }
            em.remove(persoUpdate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PersoUpdate> findPersoUpdateEntities() {
        return findPersoUpdateEntities(true, -1, -1);
    }

    public List<PersoUpdate> findPersoUpdateEntities(int maxResults, int firstResult) {
        return findPersoUpdateEntities(false, maxResults, firstResult);
    }

    private List<PersoUpdate> findPersoUpdateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PersoUpdate.class));
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

    public PersoUpdate findPersoUpdate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PersoUpdate.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersoUpdateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PersoUpdate> rt = cq.from(PersoUpdate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
