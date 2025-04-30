/*package servlet;
import java.io.IOException;
import java.io.PrintWriter;


import domain.TypeUtilisateur;
import domain.Utilisateur;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name="userinfo",
        urlPatterns={"/UserInfo"})
public class UserInfo extends HttpServlet {

    private UtilisateurDAO utilisateurDAO;
    private TypeUtilisateurDAO typeUtilisateurDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        this.utilisateurDAO = new UtilisateurDAO();
        this.typeUtilisateurDAO = new TypeUtilisateurDAO();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Récupérer les données du formulaire
            String nom = request.getParameter("nom");
            String email = request.getParameter("email");
            String motDePasse = request.getParameter("motDePasse");
            Long typeUtilisateurId = Long.parseLong(request.getParameter("typeUtilisateur"));

            // Vérifier si le type d'utilisateur existe
            TypeUtilisateur typeUtilisateur = typeUtilisateurDAO.findById(typeUtilisateurId);
            if (typeUtilisateur == null) {
                out.println("<HTML><BODY><H1>Erreur : Type d'utilisateur invalide</H1></BODY></HTML>");
                return;
            }

            // Création et sauvegarde de l'utilisateur
            Utilisateur user = new Utilisateur();
            user.setNom(nom);
            user.setEmail(email);
            user.setMotDePasse(motDePasse);
            user.setTypeUtilisateur(typeUtilisateur);

            utilisateurDAO.save(user);

            // Confirmation
            out.println("<HTML>\n<BODY>\n" +
                    "<H1>Utilisateur ajouté avec succès !</H1>\n" +
                    "<UL>\n" +
                    " <LI>Nom: " + nom + "\n" +
                    " <LI>Email: " + email + "\n" +
                    " <LI>Type: " + typeUtilisateur.getRole() + "\n" +
                    "</UL>\n" +
                    "</BODY></HTML>");
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<HTML><BODY><H1>Erreur lors de l'ajout</H1></BODY></HTML>");
        }
    }

        /*out.println("<HTML>\n<BODY>\n" +
                "<H1>Recapitulatif des informations</H1>\n" +
                "<UL>\n" +
                " <LI>Nom: "
                + request.getParameter("name") + "\n" +
                " <LI>Prenom: "
                + request.getParameter("firstname") + "\n" +
                " <LI>Age: "
                + request.getParameter("age") + "\n" +
                "</UL>\n" +
                "</BODY></HTML>");
    }*/
    //}


