package dao;

import domain.Utilisateur;
import jakarta.persistence.EntityManager;
import jpa.EntityManagerHelper;

public class UtilisateurDao {

    // Méthode pour sauvegarder un Utilisateur
    public Utilisateur save(Utilisateur utilisateur) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        System.err.println("pass par la");
        em.persist(utilisateur);  // Persiste l'entité Utilisateur
        return utilisateur;
    }

    // Méthode pour lire un Utilisateur par son ID
    public Utilisateur read(Long id) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        Utilisateur utilisateur = em.find(Utilisateur.class, id);  // Trouve l'utilisateur par ID
        return utilisateur;
    }

    // Méthode pour mettre à jour un Utilisateur
    public Utilisateur update(Utilisateur utilisateur) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        Utilisateur updatedUtilisateur = em.merge(utilisateur);  // Met à jour l'entité Utilisateur
        return updatedUtilisateur;
    }

    // Méthode pour supprimer un Utilisateur
    public void delete(Utilisateur utilisateur) {
        EntityManager em = EntityManagerHelper.getEntityManager();
        em.remove(utilisateur);  // Supprime l'entité Utilisateur
    }
}
