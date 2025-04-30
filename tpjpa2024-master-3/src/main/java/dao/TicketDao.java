package dao;

import domain.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa.EntityManagerHelper;

import java.util.List;

public class TicketDao {

    public void save(Ticket ticket) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        em.persist(ticket);
    }

    public Ticket findById(Long id) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        return em.find(Ticket.class, id);
    }

    public List<Ticket> findAll() {
        EntityManager em = EntityManagerHelper.getEntityManager();
        TypedQuery<Ticket> query = em.createQuery("SELECT t FROM Ticket t", Ticket.class);
        return query.getResultList();
    }

    public void update(Ticket ticket) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        em.merge(ticket);
    }

    public void delete(Ticket ticket) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        if (!em.contains(ticket)) {
            ticket = em.merge(ticket); // S'assurer que l'entité est managée
        }
        em.remove(ticket);
    }
}
