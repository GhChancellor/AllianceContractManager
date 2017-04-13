/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alliancecontractmanager.db.controllers;

import alliancecontractmanager.db.controllers.exceptions.NonexistentEntityException;
import alliancecontractmanager.db.entities.ContractEntity;
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
public class ContractEntityJpaController implements Serializable {

    public ContractEntityJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContractEntity contractEntity) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(contractEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ContractEntity contractEntity) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            contractEntity = em.merge(contractEntity);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = contractEntity.getId();
                if (findContractEntity(id) == null) {
                    throw new NonexistentEntityException("The contractEntity with id " + id + " no longer exists.");
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
            ContractEntity contractEntity;
            try {
                contractEntity = em.getReference(ContractEntity.class, id);
                contractEntity.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contractEntity with id " + id + " no longer exists.", enfe);
            }
            em.remove(contractEntity);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContractEntity> findContractEntityEntities() {
        return findContractEntityEntities(true, -1, -1);
    }

    public List<ContractEntity> findContractEntityEntities(int maxResults, int firstResult) {
        return findContractEntityEntities(false, maxResults, firstResult);
    }

    private List<ContractEntity> findContractEntityEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContractEntity.class));
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

    public ContractEntity findContractEntity(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContractEntity.class, id);
        } finally {
            em.close();
        }
    }

    public int getContractEntityCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContractEntity> rt = cq.from(ContractEntity.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
