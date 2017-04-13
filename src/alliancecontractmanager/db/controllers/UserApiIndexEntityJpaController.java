/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.db.controllers;

import alliancecontractmanager.db.controllers.exceptions.NonexistentEntityException;
import alliancecontractmanager.db.entities.UserApiIndexEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author lele
 */
public class UserApiIndexEntityJpaController implements Serializable {

    public UserApiIndexEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserApiIndexEntity userApiIndexEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(userApiIndexEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(UserApiIndexEntity userApiIndexEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            userApiIndexEntity = em.merge(userApiIndexEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = userApiIndexEntity.getId();
                if (findUserApiIndexEntity(id) == null) {
                    throw new NonexistentEntityException("The userApiIndexEntity with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            UserApiIndexEntity userApiIndexEntity;
            try {
                userApiIndexEntity = em.getReference(UserApiIndexEntity.class, id);
                userApiIndexEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userApiIndexEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(userApiIndexEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<UserApiIndexEntity> findUserApiIndexEntityEntities() {
        return findUserApiIndexEntityEntities(true, -1, -1);
    }

    public List<UserApiIndexEntity> findUserApiIndexEntityEntities(int maxResults, int firstResult) {
        return findUserApiIndexEntityEntities(false, maxResults, firstResult);
    }

    private List<UserApiIndexEntity> findUserApiIndexEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserApiIndexEntity.class));
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

    public UserApiIndexEntity findUserApiIndexEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserApiIndexEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserApiIndexEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserApiIndexEntity> rt = cq.from(UserApiIndexEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
