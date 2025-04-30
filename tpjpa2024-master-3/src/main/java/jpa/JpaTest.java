package jpa;

import java.time.LocalDateTime;
import java.util.List;

import dao.TicketDao;
import dao.TypeUtilisateurDao;
import domain.Evenement;
import domain.Ticket;
import jakarta.persistence.*;
import domain.Utilisateur;
import domain.TypeUtilisateur;
import dao.UtilisateurDao;

public class JpaTest {

	private EntityManager manager;
	private UtilisateurDao utilisateurDao;
	private TicketDao ticketDao;

	public JpaTest(EntityManager manager) {
		this.manager = manager;
		this.utilisateurDao = new UtilisateurDao();
		this.ticketDao = new TicketDao();
	}

	public static void main(String[] args) {
		EntityManager manager = EntityManagerHelper.getEntityManager();

		JpaTest test = new JpaTest(manager);

		EntityTransaction tx = manager.getTransaction();
		tx.begin();  // Commencer la transaction ici

		try {
			test.createUtilisateur();  // Appel explicite pour créer l'utilisateur
			test.createEvenement();
			test.createTicket();
			test.updateUtilisateurRole(1L, "Administrateur");
			tx.commit();  // Commit après la création
			System.out.println("Transaction réussie, commit effectué.");
		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();  // Rollback en cas d'erreur
				System.out.println("Transaction annulée, rollback effectué.");
			}
		}

		test.listUtilisateurs();
		test.updateUtilisateur();
		test.deleteTicket();
		//test.testTypeUtilisateur();

		// test.deleteUtilisateur();

		manager.close();  // Fermeture de l'EntityManager à la fin
		System.out.println(".. done");
	}

	private void createUtilisateur() {
		if (manager.createQuery("Select u From Utilisateur u", Utilisateur.class).getResultList().isEmpty()) {
			// Créer et persister TypeUtilisateur
			TypeUtilisateur typeUtilisateur = manager.find(TypeUtilisateur.class, 1L);
			if (typeUtilisateur == null) {
				typeUtilisateur = new TypeUtilisateur();
				typeUtilisateur.setRole("Utilisateur");
				manager.persist(typeUtilisateur);
			}

			// Créer l'utilisateur Jean Dupont
			Utilisateur utilisateur = new Utilisateur();
			utilisateur.setNom("Jean Dupont");
			utilisateur.setEmail("jean.dupont@example.com");
			utilisateur.setMotDePasse("password123");
			utilisateur.setTypeUtilisateur(typeUtilisateur);
			utilisateurDao.save(utilisateur);  // Sauvegarder l'utilisateur

			manager.flush();  // S'assurer que l'utilisateur est bien persisté avant de valider la transaction
		}
	}

	private void listUtilisateurs() {
		List<Utilisateur> utilisateurs = manager.createQuery("Select u From Utilisateur u", Utilisateur.class).getResultList();
		System.out.println("Nombre d'utilisateurs: " + utilisateurs.size());
		for (Utilisateur u : utilisateurs) {
			System.out.println("Utilisateur: " + u.getNom() + " - Email: " + u.getEmail());
		}
	}

	private void updateUtilisateur() {
		EntityTransaction tx = manager.getTransaction();
		try {
			// Commencer la transaction
			tx.begin();
			List<Utilisateur> utilisateurs = manager.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
			if (!utilisateurs.isEmpty()) {
				Utilisateur utilisateur = utilisateurs.get(0);
				utilisateur.setNom("Jean Dupont Modifié");
				utilisateurDao.update(utilisateur);

				// S'assurer que la modification est bien persistée
				manager.flush();  // Synchronise les modifications avec la base de données
				System.out.println("Utilisateur mis à jour : " + utilisateur.getNom());
			}
			// Commit après les modifications
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();  // Rollback en cas d'erreur
			}
			e.printStackTrace();
		}
	}


	/*private void deleteUtilisateur() {
        List<Utilisateur> utilisateurs = manager.createQuery("Select u From Utilisateur u", Utilisateur.class).getResultList();
        if (!utilisateurs.isEmpty()) {
            Utilisateur utilisateur = utilisateurs.get(0);
            utilisateurDao.delete(utilisateur);
            System.out.println("Utilisateur supprimé: " + utilisateur.getNom());
        }
    }*/
	private void updateUtilisateurRole(Long utilisateurId, String newRole) {
		// Trouver l'utilisateur par son ID
		Utilisateur utilisateur = manager.find(Utilisateur.class, utilisateurId);

		if (utilisateur != null) {
			// Vérifier si le rôle existe déjà dans la base de données
			TypeUtilisateur newTypeUtilisateur = null;
			try {
				// Essayer de récupérer le rôle à partir de la base de données
				newTypeUtilisateur = manager.createQuery("SELECT t FROM TypeUtilisateur t WHERE t.role = :role", TypeUtilisateur.class)
						.setParameter("role", newRole)
						.getSingleResult();
			} catch (NoResultException e) {
				// Si aucun rôle trouvé, créer et persister le rôle
				newTypeUtilisateur = new TypeUtilisateur();
				newTypeUtilisateur.setRole(newRole);
				manager.persist(newTypeUtilisateur);
				System.out.println("Rôle " + newRole + " créé et persistant.");
			}

			// Mettre à jour le rôle de l'utilisateur
			utilisateur.setTypeUtilisateur(newTypeUtilisateur);

			// Sauvegarder l'utilisateur mis à jour
			utilisateurDao.update(utilisateur);
			manager.flush();
			System.out.println("L'utilisateur " + utilisateur.getNom() + " a maintenant le rôle " + newTypeUtilisateur.getRole());
		} else {
			System.out.println("Utilisateur avec ID " + utilisateurId + " non trouvé.");
		}
	}

	private void createTicket() {
		// On suppose que tu as déjà au moins un utilisateur et un événement existants en base
		Utilisateur utilisateur = manager.find(Utilisateur.class, 1L);
		Evenement evenement = manager.find(Evenement.class, 1L);

		if (utilisateur != null && evenement != null) {
			Ticket ticket = new Ticket();
			ticket.setPrix(49.99f);
			ticket.setStatut("Valide");
			ticket.setUtilisateur(utilisateur);
			ticket.setEvenement(evenement);

			ticketDao.save(ticket);
			System.out.println("Ticket créé avec ID : " + ticket.getId());
		} else {
			System.out.println("Utilisateur ou Événement introuvable, impossible de créer un ticket.");
		}
	}

	private void createEvenement() {
		// On récupère un utilisateur existant (assume ID 1)
		Utilisateur utilisateur = manager.find(Utilisateur.class, 1L);

		if (utilisateur != null) {
			Evenement evenement = new Evenement();
			evenement.setNom("Concert Test");
			evenement.setArtiste("Mathieu Riles");
			evenement.setGenre("Hip-Hop");
			evenement.setDescription("Un concert de test pour valider la création.");
			evenement.setLieu("Paris Bercy");
			evenement.setCapacite(5000);
			evenement.setPrix(39.99f);
			evenement.setDate(LocalDateTime.now().plusDays(30)); // Événement dans 30 jours
			evenement.setUtilisateur(utilisateur);

			manager.persist(evenement);
			System.out.println("Événement créé avec ID : " + evenement.getId());
		} else {
			System.out.println("Aucun utilisateur trouvé pour associer l'événement.");
		}
	}


	private void listTickets() {
		TicketDao ticketDao = new TicketDao();
		List<Ticket> tickets = manager.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
		System.out.println("Nombre de tickets: " + tickets.size());
		for (Ticket t : tickets) {
			System.out.println("Ticket: " + t.getPrix() + " - Statut: " + t.getStatut());
		}
	}

	private void updateTicket() {
		TicketDao ticketDao = new TicketDao();
		Ticket ticket = manager.find(Ticket.class, 1L);  // Trouver un ticket par son ID
		if (ticket != null) {
			ticket.setStatut("Vendu");  // Mise à jour du statut
			ticketDao.update(ticket);
			System.out.println("Ticket mis à jour.");
		}
	}

	private void deleteTicket() {
		EntityTransaction tx = manager.getTransaction();
		try {
			tx.begin();
			Ticket ticket = manager.find(Ticket.class, 1L);
			if (ticket != null) {
				ticketDao.delete(ticket); // méthode DAO avec remove
				System.out.println("Ticket supprimé.");
			} else {
				System.out.println("Aucun ticket trouvé à supprimer.");
			}
			tx.commit();
		} catch (Exception e) {
			if (tx.isActive()) tx.rollback();
			e.printStackTrace();
		}
	}



	private void testTypeUtilisateur() {
		TypeUtilisateurDao typeDao = new TypeUtilisateurDao();

		// Create
		TypeUtilisateur admin = new TypeUtilisateur();
		admin.setRole("Administrateur");
		typeDao.save(admin);
		System.out.println("TypeUtilisateur sauvegardé : " + admin.getRole());

		// Read
		TypeUtilisateur fetched = typeDao.read(admin.getId());
		System.out.println("TypeUtilisateur récupéré : " + fetched.getRole());

		// Update
		fetched.setRole("Admin modifié");
		typeDao.update(fetched);
		System.out.println("TypeUtilisateur mis à jour : " + fetched.getRole());

		// Delete
		// typeDao.delete(fetched);
		// System.out.println("TypeUtilisateur supprimé.");
	}
}
