package com.tp.ex3;

import com.tp.ex3.beans.Femme;
import com.tp.ex3.beans.Homme;
import com.tp.ex3.beans.Mariage;
import com.tp.ex3.service.FemmeService;
import com.tp.ex3.service.HommeService;
import com.tp.ex3.service.MariageService;
import com.tp.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class TestApplication {

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Transaction transaction = null;

        FemmeService femmeService = new FemmeService();
        HommeService hommeService = new HommeService();
        MariageService mariageService = new MariageService();

        try {
            // Démarrer une session et une transaction
            Session session = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = session.beginTransaction();

            System.out.println("=== CRÉATION DES DONNÉES ===\n");

            Femme f1 = new Femme("RAMI", "SALIMA", "0612345671", "Casablanca", sdf.parse("15/03/1970"));
            Femme f2 = new Femme("ALI", "AMAL", "0612345672", "Rabat", sdf.parse("20/05/1975"));
            Femme f3 = new Femme("ALAOUI", "WAFA", "0612345673", "Fès", sdf.parse("10/08/1980"));
            Femme f4 = new Femme("ALAMI", "KARIMA", "0612345674", "Marrakech", sdf.parse("25/12/1968"));
            Femme f5 = new Femme("BENNANI", "FATIMA", "0612345675", "Tanger", sdf.parse("05/07/1985"));
            Femme f6 = new Femme("IDRISSI", "SOFIA", "0612345676", "Agadir", sdf.parse("18/11/1978"));
            Femme f7 = new Femme("BERRADA", "NADIA", "0612345677", "Meknès", sdf.parse("12/02/1982"));
            Femme f8 = new Femme("TAZI", "LAILA", "0612345678", "Oujda", sdf.parse("30/09/1965"));
            Femme f9 = new Femme("FASSI", "ZINEB", "0612345679", "Tétouan", sdf.parse("22/04/1990"));
            Femme f10 = new Femme("SENHAJI", "KHADIJA", "0612345680", "Kenitra", sdf.parse("08/06/1973"));

            femmeService.create(f1);
            femmeService.create(f2);
            femmeService.create(f3);
            femmeService.create(f4);
            femmeService.create(f5);
            femmeService.create(f6);
            femmeService.create(f7);
            femmeService.create(f8);
            femmeService.create(f9);
            femmeService.create(f10);

            System.out.println("10 femmes créées avec succès");

            Homme h1 = new Homme("SAFI", "SAID", "0612345681", "Casablanca", sdf.parse("10/01/1965"));
            Homme h2 = new Homme("BENJELLOUN", "AHMED", "0612345682", "Rabat", sdf.parse("15/06/1970"));
            Homme h3 = new Homme("CHRAIBI", "KARIM", "0612345683", "Fès", sdf.parse("20/08/1968"));
            Homme h4 = new Homme("LAZRAK", "YOUSSEF", "0612345684", "Marrakech", sdf.parse("05/03/1975"));
            Homme h5 = new Homme("FILALI", "OMAR", "0612345685", "Tanger", sdf.parse("12/11/1972"));

            hommeService.create(h1);
            hommeService.create(h2);
            hommeService.create(h3);
            hommeService.create(h4);
            hommeService.create(h5);

            System.out.println("5 hommes créés avec succès\n");

            Mariage m1 = new Mariage(sdf.parse("03/09/1989"), sdf.parse("03/09/1990"), 0, h1, f4);
            Mariage m2 = new Mariage(sdf.parse("03/09/1990"), null, 4, h1, f1);
            Mariage m3 = new Mariage(sdf.parse("03/09/1995"), null, 2, h1, f2);
            Mariage m4 = new Mariage(sdf.parse("04/11/2000"), null, 3, h1, f3);

            mariageService.create(m1);
            mariageService.create(m2);
            mariageService.create(m3);
            mariageService.create(m4);

            Mariage m5 = new Mariage(sdf.parse("10/05/1995"), sdf.parse("15/06/2000"), 2, h2, f5);
            Mariage m6 = new Mariage(sdf.parse("20/07/2001"), null, 3, h2, f6);
            mariageService.create(m5);
            mariageService.create(m6);

            Mariage m7 = new Mariage(sdf.parse("12/01/1998"), null, 2, h3, f7);
            mariageService.create(m7);

            Mariage m8 = new Mariage(sdf.parse("10/03/2005"), sdf.parse("20/08/2010"), 1, h4, f6);
            mariageService.create(m8);

            Mariage m9 = new Mariage(sdf.parse("01/01/2000"), null, 1, h5, f8);
            Mariage m10 = new Mariage(sdf.parse("01/06/2000"), null, 2, h5, f9);
            Mariage m11 = new Mariage(sdf.parse("01/09/2000"), null, 1, h5, f10);
            Mariage m12 = new Mariage(sdf.parse("01/12/2000"), null, 3, h5, f5);
            mariageService.create(m9);
            mariageService.create(m10);
            mariageService.create(m11);
            mariageService.create(m12);

            System.out.println("Mariages créés avec succès\n");

            // Commit de la transaction
            transaction.commit();

            // Nouvelle session et transaction pour les lectures
            Session newSession = HibernateUtil.getSessionFactory().getCurrentSession();
            transaction = newSession.beginTransaction();

            System.out.println("=== 1. LISTE DES FEMMES ===");
            List<Femme> femmes = femmeService.findAll();
            for (Femme f : femmes) {
                System.out.println("- " + f.getPrenom() + " " + f.getNom() +
                        " (Née le: " + sdf.format(f.getDateNaissance()) + ")");
            }

            System.out.println("\n=== 2. FEMME LA PLUS ÂGÉE ===");
            Femme femmePlusAgee = femmeService.getFemmeLaPlusAgee();
            if (femmePlusAgee != null) {
                System.out.println("La femme la plus âgée est : " + femmePlusAgee.getPrenom() +
                        " " + femmePlusAgee.getNom() +
                        " (Née le: " + sdf.format(femmePlusAgee.getDateNaissance()) + ")");
            }

            System.out.println("\n=== 3. ÉPOUSES DE L'HOMME 1 ENTRE 1990 ET 2000 ===");
            List<Femme> epouses = hommeService.getEpousesEntreDates(
                    h1,
                    sdf.parse("01/01/1990"),
                    sdf.parse("31/12/2000")
            );
            System.out.println("Épouses de " + h1.getPrenom() + " " + h1.getNom() + " :");
            for (Femme epouse : epouses) {
                System.out.println("- " + epouse.getPrenom() + " " + epouse.getNom());
            }

            System.out.println("\n=== 4. NOMBRE D'ENFANTS DE SALIMA RAMI ENTRE 1990 ET 2000 ===");
            int nbrEnfants = femmeService.getNombreEnfantsEntreDates(
                    f1,
                    sdf.parse("01/01/1990"),
                    sdf.parse("31/12/2000")
            );
            System.out.println("Nombre d'enfants de " + f1.getPrenom() + " " + f1.getNom() +
                    " entre 1990 et 2000 : " + nbrEnfants);

            System.out.println("\n=== 5. FEMMES MARIÉES AU MOINS DEUX FOIS ===");
            List<Femme> femmesMariees = femmeService.getFemmesMarieeDeuxFois();
            System.out.println("Femmes mariées au moins 2 fois :");
            for (Femme f : femmesMariees) {
                System.out.println("- " + f.getPrenom() + " " + f.getNom() +
                        " (Nombre de mariages: " + f.getMariages().size() + ")");
            }

            System.out.println("\n=== 6. HOMMES MARIÉS À 4 FEMMES ENTRE 2000 ET 2001 ===");
            int nbrHommes = hommeService.getNombreHommesMarieQuatreFemmes(
                    sdf.parse("01/01/2000"),
                    sdf.parse("31/12/2001")
            );
            System.out.println("Nombre d'hommes mariés à 4 femmes entre 2000 et 2001 : " + nbrHommes);

            System.out.println("\n=== 7. DÉTAILS DES MARIAGES DE SAFI SAID ===");
            hommeService.afficherMariagesHomme(h1);

            System.out.println("\n\n=== 8. DÉTAILS DES MARIAGES DE BENJELLOUN AHMED ===");
            hommeService.afficherMariagesHomme(h2);

            transaction.commit();

        } catch (ParseException e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            System.err.println("Erreur lors de l'exécution: " + e.getMessage());
            e.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
            System.out.println("\n\n=== FIN DU PROGRAMME ===");
        }
    }
}