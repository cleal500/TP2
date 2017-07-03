/**
 * TP2
 * Created by Alexandre Clément
 * Code permanent CLEA16059309
 */
public class BatailleNavale {

    public static final String MSG_ERR_NIVEAU = "\nERREUR! Niveau invalide... Recommencez!\n";
    public static final String MSG_ERR_TIR = "ERREUR! Coordonnees de tir invalides... Recommencez!";
    public static final String MSG_ERR_JOUER = "ERREUR! Vous devez repondre par oui ou par non... Recommencez!";
    public static final String MUN_RESTANTE = "\nMUNITIONS RESTANTES : %s";
    public static final String VAISSEAU_COULLER = "\nVAISSEAUX COULES : ";
    public static final String COORDONNES = "\nEntrez les coordonnees du prochain tir : ";
    public static final String TIR_ECHEC = "\n----------> TIR MANQUE ! <----------";
    public static final String TOUCHER = "\n----------> TIR REUSSI ! <----------";
    public static final String TIR_REDONDANT = "\n---------> TIR REDONDANT ! <---------";
    public static final String FIN = "FIN NORMALE DU PROGRAMME";
    public static final String SYMBOLE_MANQUE = "X";

    public static final String BRAVO = "    public static final String BRAVO =\"\"\n";
    public static final String DESOLE = "DESOLE! Vous avez epuise toutes vos munitions!";
    public static final String NBR_VAISSEAU_COULE = "Nombre de vaisseaux non coules : ";
    public static final String MUNITION_UTILISE = "Munitions utilisees : ";

    public static void afficherTitre() {
        System.out.println(" ____    ____  ______   ____  ____  _      _        ___ ");
        System.out.println("|    \\  /    ||      | /    ||    || |    | |      /  _]");
        System.out.println("|  o  )|  o  ||      ||  o  | |  | | |    | |     /  [_ ");
        System.out.println("|     ||     ||_|  |_||     | |  | | |___ | |___ |    _]");
        System.out.println("|  O  ||  _  |  |  |  |  _  | |  | |     ||     ||   [_ ");
        System.out.println("|     ||  |  |  |  |  |  |  | |  | |     ||     ||     |");
        System.out.println("|_____||__|__|  |__|  |__|__||____||_____||_____||_____|");
        System.out.println("       ____    ____  __ __   ____  _        ___         ");
        System.out.println("      |    \\  /    ||  |  | /    || |      /  _]        ");
        System.out.println("      |  _  ||  o  ||  |  ||  o  || |     /  [_         ");
        System.out.println("      |  |  ||     ||  |  ||     || |___ |    _]        ");
        System.out.println("      |  |  ||  _  ||  :  ||  _  ||     ||   [_          ");
        System.out.println("      |  |  ||  |  | \\   / |  |  ||     ||     |         ");
        System.out.println("      |__|__||__|__|  \\_/  |__|__||_____||_____|    ");
        System.out.println("Appuyez sur <ENTREE> pour continuer... ");
        Clavier.lireFinLigne();
    }

    public static void main(String[] args) {

        boolean rejouer = true;

        afficherTitre();

        do {
            int munition;
            String solution;
            String jeux = "                                                                ";
            int nbVaisseauxCoules = 0;
            String bateauxCoules = "";

            // Obtenir la difficulté
            afficherSelectionDifficulte();
            munition = obtenirDifficulte();

            // Générer la solution
            solution = JeuUtils.genererGrilleSolution();

            System.out.println();
            //JeuUtils.afficherGrille(jeux); // TODO: uncomment
            JeuUtils.afficherGrille(solution); // TODO: remove

            do {
                // Afficher munitions restantes
                String message = String.format(MUN_RESTANTE, munition);
                System.out.println(message);

                // Aficher nombre de vaisseaux coulés
                System.out.println(VAISSEAU_COULLER + nbVaisseauxCoules);

                String coordonnees = obtenirCoordonnees();
                int position = extraireTir(coordonnees);

                jeux = mettreAJourJeux(jeux, solution, position);

                verifierSiBateauCouler(solution, jeux, position);

                JeuUtils.afficherGrille(jeux);
            } while (--munition > 0);
        } while (rejouer);
        System.out.println(FIN);
    }

    public static void afficherSelectionDifficulte() {
        System.out.println("NIVEAU DE DIFFICULTE :\n" +
                "1. Debutant (45 munitions)\n" +
                "2. Intermediaire (35 munitions)\n" +
                "3. Expert (25 munitions)\n");
        System.out.print("Entrez votre choix (1, 2 ou 3): ");
    }

    public static int obtenirDifficulte() {
        String choixUtilisateur;
        int reponse = 0;

        do {
            choixUtilisateur = Clavier.lireString();
            switch (choixUtilisateur) {
                case "1":
                    reponse = 45;
                    break;
                case "2":
                    reponse = 35;
                    break;
                case "3":
                    reponse = 25;
                    break;
                default:
                    System.out.println(MSG_ERR_NIVEAU);
                    afficherSelectionDifficulte();
                    break;
            }
        } while (reponse == 0);
        return reponse;
    }


    public static String obtenirCoordonnees() {
        String userInput;

        do {
            System.out.print(COORDONNES);

            userInput = Clavier.lireString();

            boolean estValide = coordoneesSontValide(userInput);

            if (estValide) {
                break;
            }

            System.out.print(MSG_ERR_TIR);
        } while (true);

        return userInput;
    }

    public static boolean coordoneesSontValide(String coordonnes) {
        if (coordonnes.length() == 3) {
            char char1 = coordonnes.charAt(0);
            boolean char1Valid = char1 >= '0' && char1 <= '7';

            char char2 = coordonnes.charAt(1);
            boolean char2Valid = char2 == ',';

            char char3 = coordonnes.charAt(2);
            boolean char3Valid = char3 >= '0' && char3 <= '7';

            return char1Valid && char2Valid && char3Valid;
        } else {
            return false;
        }
    }

    public static int extraireTir(String coordonnee) {
        int ligne = Integer.parseInt(coordonnee.substring(0, 1));
        int colone = Integer.parseInt(coordonnee.substring(2, 3));

        return ligne * 8 + colone;
    }

    public static String mettreAJourJeux(String jeux, String solution, int position) {
        char valeur = jeux.charAt(position);

        // Soit X ou B, donc tir redondant
        if (valeur != ' ') {
            System.out.println(TIR_REDONDANT);
            return jeux;
        }

        boolean aToucheBateau = verifierSiToucher(solution, position);

        if (!aToucheBateau) {
            // Si tir raté
            System.out.println(TIR_ECHEC);
            return jeux.substring(0, position) + SYMBOLE_MANQUE + jeux.substring(position + 1, 64);
        } else {
            // Si nouvellement touché
            System.out.println(TOUCHER);
            return jeux.substring(0, position) + JeuUtils.SYMBOLE_VAISSEAU + jeux.substring(position + 1, 64);
        }
    }

    public static boolean verifierSiToucher(String solution, int position) {
        return solution.charAt(position) == JeuUtils.SYMBOLE_VAISSEAU;
    }

    public static void verifierSiBateauCouler(String solution, String jeux, int position) {
        int nSolution = verifierBateauTouche(solution, position);
        int nJeux = verifierBateauTouche(jeux, position);

        if (nSolution == nJeux) {
            System.out.println("Coulé: " + nSolution);
        } else {
            System.out.println("Pas coulé");
        }
    }

    public static int verifierBateauTouche(String jeux, int position) {
        int n = verifierHorizontal(jeux, position);

        if (n == 1) {
            n = verifierVertical(jeux, position);
        }

        return n;
    }

    public static int verifierHorizontal(String solution, int position) {
        int n = 1;

        int postLeft = position;
        while (postLeft > 0 && (--postLeft + 1) % 8 != 0 && solution.charAt(postLeft) == 'B') {
            n++;
        }

        int posRight = position;
        while (posRight < 64 && ++posRight % 8 != 0 && solution.charAt(posRight) == 'B') {
            n++;
        }

        return n;
    }

    public static int verifierVertical(String solution, int position) {
        int n = 1;

        int posTop = position;
        while ((posTop -= 8) >= 0 && solution.charAt(posTop) == 'B') {
            n++;
        }

        int posBottom = position;
        while ((posBottom += 8) < 64 && solution.charAt(posBottom) == 'B') {
            n++;
        }

        return n;
    }
}
