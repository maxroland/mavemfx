/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.maxroland.mavenfx.controller;

import com.maxroland.mavenfx.controller.exceptions.IllegalOrphanException;
import com.maxroland.mavenfx.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.maxroland.mavenfx.modelo.TelPerso;
import java.util.ArrayList;
import java.util.List;
import com.maxroland.mavenfx.modelo.PersoUpdate;
import com.maxroland.mavenfx.modelo.Persona;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rolando
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        if (persona.getTelPersoList() == null) {
            persona.setTelPersoList(new ArrayList<TelPerso>());
        }
        if (persona.getPersoUpdateList() == null) {
            persona.setPersoUpdateList(new ArrayList<PersoUpdate>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<TelPerso> attachedTelPersoList = new ArrayList<TelPerso>();
            for (TelPerso telPersoListTelPersoToAttach : persona.getTelPersoList()) {
                telPersoListTelPersoToAttach = em.getReference(telPersoListTelPersoToAttach.getClass(), telPersoListTelPersoToAttach.getId());
                attachedTelPersoList.add(telPersoListTelPersoToAttach);
            }
            persona.setTelPersoList(attachedTelPersoList);
            List<PersoUpdate> attachedPersoUpdateList = new ArrayList<PersoUpdate>();
            for (PersoUpdate persoUpdateListPersoUpdateToAttach : persona.getPersoUpdateList()) {
                persoUpdateListPersoUpdateToAttach = em.getReference(persoUpdateListPersoUpdateToAttach.getClass(), persoUpdateListPersoUpdateToAttach.getId());
                attachedPersoUpdateList.add(persoUpdateListPersoUpdateToAttach);
            }
            persona.setPersoUpdateList(attachedPersoUpdateList);
            em.persist(persona);
            for (TelPerso telPersoListTelPerso : persona.getTelPersoList()) {
                Persona oldPersonaIdOfTelPersoListTelPerso = telPersoListTelPerso.getPersonaId();
                telPersoListTelPerso.setPersonaId(persona);
                telPersoListTelPerso = em.merge(telPersoListTelPerso);
                if (oldPersonaIdOfTelPersoListTelPerso != null) {
                    oldPersonaIdOfTelPersoListTelPerso.getTelPersoList().remove(telPersoListTelPerso);
                    oldPersonaIdOfTelPersoListTelPerso = em.merge(oldPersonaIdOfTelPersoListTelPerso);
                }
            }
            for (PersoUpdate persoUpdateListPersoUpdate : persona.getPersoUpdateList()) {
                Persona oldPersonaIdOfPersoUpdateListPersoUpdate = persoUpdateListPersoUpdate.getPersonaId();
                persoUpdateListPersoUpdate.setPersonaId(persona);
                persoUpdateListPersoUpdate = em.merge(persoUpdateListPersoUpdate);
                if (oldPersonaIdOfPersoUpdateListPersoUpdate != null) {
                    oldPersonaIdOfPersoUpdateListPersoUpdate.getPersoUpdateList().remove(persoUpdateListPersoUpdate);
                    oldPersonaIdOfPersoUpdateListPersoUpdate = em.merge(oldPersonaIdOfPersoUpdateListPersoUpdate);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId());
            List<TelPerso> telPersoListOld = persistentPersona.getTelPersoList();
            List<TelPerso> telPersoListNew = persona.getTelPersoList();
            List<PersoUpdate> persoUpdateListOld = persistentPersona.getPersoUpdateList();
            List<PersoUpdate> persoUpdateListNew = persona.getPersoUpdateList();
            List<String> illegalOrphanMessages = null;
            for (TelPerso telPersoListOldTelPerso : telPersoListOld) {
                if (!telPersoListNew.contains(telPersoListOldTelPerso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain TelPerso " + telPersoListOldTelPerso + " since its personaId field is not nullable.");
                }
            }
            for (PersoUpdate persoUpdateListOldPersoUpdate : persoUpdateListOld) {
                if (!persoUpdateListNew.contains(persoUpdateListOldPersoUpdate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersoUpdate " + persoUpdateListOldPersoUpdate + " since its personaId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<TelPerso> attachedTelPersoListNew = new ArrayList<TelPerso>();
            for (TelPerso telPersoListNewTelPersoToAttach : telPersoListNew) {
                telPersoListNewTelPersoToAttach = em.getReference(telPersoListNewTelPersoToAttach.getClass(), telPersoListNewTelPersoToAttach.getId());
                attachedTelPersoListNew.add(telPersoListNewTelPersoToAttach);
            }
            telPersoListNew = attachedTelPersoListNew;
            persona.setTelPersoList(telPersoListNew);
            List<PersoUpdate> attachedPersoUpdateListNew = new ArrayList<PersoUpdate>();
            for (PersoUpdate persoUpdateListNewPersoUpdateToAttach : persoUpdateListNew) {
                persoUpdateListNewPersoUpdateToAttach = em.getReference(persoUpdateListNewPersoUpdateToAttach.getClass(), persoUpdateListNewPersoUpdateToAttach.getId());
                attachedPersoUpdateListNew.add(persoUpdateListNewPersoUpdateToAttach);
            }
            persoUpdateListNew = attachedPersoUpdateListNew;
            persona.setPersoUpdateList(persoUpdateListNew);
            persona = em.merge(persona);
            for (TelPerso telPersoListNewTelPerso : telPersoListNew) {
                if (!telPersoListOld.contains(telPersoListNewTelPerso)) {
                    Persona oldPersonaIdOfTelPersoListNewTelPerso = telPersoListNewTelPerso.getPersonaId();
                    telPersoListNewTelPerso.setPersonaId(persona);
                    telPersoListNewTelPerso = em.merge(telPersoListNewTelPerso);
                    if (oldPersonaIdOfTelPersoListNewTelPerso != null && !oldPersonaIdOfTelPersoListNewTelPerso.equals(persona)) {
                        oldPersonaIdOfTelPersoListNewTelPerso.getTelPersoList().remove(telPersoListNewTelPerso);
                        oldPersonaIdOfTelPersoListNewTelPerso = em.merge(oldPersonaIdOfTelPersoListNewTelPerso);
                    }
                }
            }
            for (PersoUpdate persoUpdateListNewPersoUpdate : persoUpdateListNew) {
                if (!persoUpdateListOld.contains(persoUpdateListNewPersoUpdate)) {
                    Persona oldPersonaIdOfPersoUpdateListNewPersoUpdate = persoUpdateListNewPersoUpdate.getPersonaId();
                    persoUpdateListNewPersoUpdate.setPersonaId(persona);
                    persoUpdateListNewPersoUpdate = em.merge(persoUpdateListNewPersoUpdate);
                    if (oldPersonaIdOfPersoUpdateListNewPersoUpdate != null && !oldPersonaIdOfPersoUpdateListNewPersoUpdate.equals(persona)) {
                        oldPersonaIdOfPersoUpdateListNewPersoUpdate.getPersoUpdateList().remove(persoUpdateListNewPersoUpdate);
                        oldPersonaIdOfPersoUpdateListNewPersoUpdate = em.merge(oldPersonaIdOfPersoUpdateListNewPersoUpdate);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getId();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<TelPerso> telPersoListOrphanCheck = persona.getTelPersoList();
            for (TelPerso telPersoListOrphanCheckTelPerso : telPersoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the TelPerso " + telPersoListOrphanCheckTelPerso + " in its telPersoList field has a non-nullable personaId field.");
            }
            List<PersoUpdate> persoUpdateListOrphanCheck = persona.getPersoUpdateList();
            for (PersoUpdate persoUpdateListOrphanCheckPersoUpdate : persoUpdateListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the PersoUpdate " + persoUpdateListOrphanCheckPersoUpdate + " in its persoUpdateList field has a non-nullable personaId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
