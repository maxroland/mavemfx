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
import com.maxroland.mavenfx.modelo.PersoUpdate;
import com.maxroland.mavenfx.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rolando
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getPersoUpdateList() == null) {
            usuario.setPersoUpdateList(new ArrayList<PersoUpdate>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PersoUpdate> attachedPersoUpdateList = new ArrayList<PersoUpdate>();
            for (PersoUpdate persoUpdateListPersoUpdateToAttach : usuario.getPersoUpdateList()) {
                persoUpdateListPersoUpdateToAttach = em.getReference(persoUpdateListPersoUpdateToAttach.getClass(), persoUpdateListPersoUpdateToAttach.getId());
                attachedPersoUpdateList.add(persoUpdateListPersoUpdateToAttach);
            }
            usuario.setPersoUpdateList(attachedPersoUpdateList);
            em.persist(usuario);
            for (PersoUpdate persoUpdateListPersoUpdate : usuario.getPersoUpdateList()) {
                Usuario oldUsuarioIdOfPersoUpdateListPersoUpdate = persoUpdateListPersoUpdate.getUsuarioId();
                persoUpdateListPersoUpdate.setUsuarioId(usuario);
                persoUpdateListPersoUpdate = em.merge(persoUpdateListPersoUpdate);
                if (oldUsuarioIdOfPersoUpdateListPersoUpdate != null) {
                    oldUsuarioIdOfPersoUpdateListPersoUpdate.getPersoUpdateList().remove(persoUpdateListPersoUpdate);
                    oldUsuarioIdOfPersoUpdateListPersoUpdate = em.merge(oldUsuarioIdOfPersoUpdateListPersoUpdate);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            List<PersoUpdate> persoUpdateListOld = persistentUsuario.getPersoUpdateList();
            List<PersoUpdate> persoUpdateListNew = usuario.getPersoUpdateList();
            List<String> illegalOrphanMessages = null;
            for (PersoUpdate persoUpdateListOldPersoUpdate : persoUpdateListOld) {
                if (!persoUpdateListNew.contains(persoUpdateListOldPersoUpdate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PersoUpdate " + persoUpdateListOldPersoUpdate + " since its usuarioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PersoUpdate> attachedPersoUpdateListNew = new ArrayList<PersoUpdate>();
            for (PersoUpdate persoUpdateListNewPersoUpdateToAttach : persoUpdateListNew) {
                persoUpdateListNewPersoUpdateToAttach = em.getReference(persoUpdateListNewPersoUpdateToAttach.getClass(), persoUpdateListNewPersoUpdateToAttach.getId());
                attachedPersoUpdateListNew.add(persoUpdateListNewPersoUpdateToAttach);
            }
            persoUpdateListNew = attachedPersoUpdateListNew;
            usuario.setPersoUpdateList(persoUpdateListNew);
            usuario = em.merge(usuario);
            for (PersoUpdate persoUpdateListNewPersoUpdate : persoUpdateListNew) {
                if (!persoUpdateListOld.contains(persoUpdateListNewPersoUpdate)) {
                    Usuario oldUsuarioIdOfPersoUpdateListNewPersoUpdate = persoUpdateListNewPersoUpdate.getUsuarioId();
                    persoUpdateListNewPersoUpdate.setUsuarioId(usuario);
                    persoUpdateListNewPersoUpdate = em.merge(persoUpdateListNewPersoUpdate);
                    if (oldUsuarioIdOfPersoUpdateListNewPersoUpdate != null && !oldUsuarioIdOfPersoUpdateListNewPersoUpdate.equals(usuario)) {
                        oldUsuarioIdOfPersoUpdateListNewPersoUpdate.getPersoUpdateList().remove(persoUpdateListNewPersoUpdate);
                        oldUsuarioIdOfPersoUpdateListNewPersoUpdate = em.merge(oldUsuarioIdOfPersoUpdateListNewPersoUpdate);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PersoUpdate> persoUpdateListOrphanCheck = usuario.getPersoUpdateList();
            for (PersoUpdate persoUpdateListOrphanCheckPersoUpdate : persoUpdateListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuario (" + usuario + ") cannot be destroyed since the PersoUpdate " + persoUpdateListOrphanCheckPersoUpdate + " in its persoUpdateList field has a non-nullable usuarioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
