/**
 * TP2
 * Created by Alexandre Clément
 * Code permanent CLEA16059309
 *
 */
    public class BatailleNavale {

        public static final String MSG_ERR_NIVEAU = "\nERREUR! Niveau invalide... Recommencez!\n";
        public static final String MSG_ERR_TIR = "ERREUR! Coordonnees de tir invalides... Recommencez!";
        public static final String MSG_ERR_JOUER = "ERREUR! Vous devez repondre par oui ou par non... Recommencez!";
        public static final String MUN_RESTANTE = "\nMUNITIONS RESTANTES : ";
        public static final String VAISSEAU_COULLER = "\nVAISSEAUX COULES : ";
        public static final String COORDONNES = "\nEntrez les coordonnees du prochain tir : ";
        public static final String TIR_ECHEC = "\n----------> TIR MANQUE ! <----------";
        public static final String TOUCHER= "\n----------> TIR REUSSI ! <----------";
        public static final String TIR_REDONDANT="\n---------> TIR REDONDANT ! <---------";
        public static final String FIN ="FIN NORMALE DU PROGRAMME";
        public static final String BRAVO ="    public static final String BRAVO =\"\"\n";
        public static final String DESOLE ="DESOLE! Vous avez epuise toutes vos munitions!";
        public static final String NBR_VAISSEAU_COULE ="Nombre de vaisseaux non coules : ";
        public static final String MUNITION_UTILISE ="Munitions utilisees : ";

        public static void titre(){
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
        public static void niveau() {

            System.out.println("NIVEAU DE DIFFICULTE :\n" +
                    "1. Debutant (45 munitions)\n" +
                    "2. Intermediaire (35 munitions)\n" +
                    "3. Expert (25 munitions)\n");
            System.out.print("Entrez votre choix (1, 2 ou 3): ");
        }

        public static int difficulte() {
            String choixUtilisateur;
            int reponse = 0;

            do {
                choixUtilisateur = Clavier.lireString();
                switch (choixUtilisateur) {
                    case "1":
                        reponse = reponse + 45;
                        break;
                    case "2":
                        reponse = reponse + 35;
                        break;
                    case "3":
                        reponse = reponse + 25;
                        break;
                    default:
                        System.out.println(MSG_ERR_NIVEAU);
                        niveau();
                        break;
                }
            } while (reponse == 0);
            return reponse;
        }

        public static String coordonneeLongeur() {
            String coordonnee;
            int chaine;

            System.out.print(COORDONNES);
            coordonnee = Clavier.lireString();
            chaine = coordonnee.length();
            while (chaine != 3) {
                System.out.print(MSG_ERR_TIR);
                System.out.print(COORDONNES);
                coordonnee = Clavier.lireString();
                chaine = coordonnee.length();
            }
            return coordonnee;
        }
        public static String coordonneeValid(String coordonnee){

            while ((coordonnee.charAt(0) > '7') | ((coordonnee.charAt(0) < '0') |
                    (coordonnee.charAt(1) != ',') |
                    (coordonnee.charAt(2) > '7')) | (coordonnee.charAt(2) < '0')){
                System.out.print(MSG_ERR_TIR);
                coordonnee = coordonneeLongeur();
            }
            return coordonnee;
        }

        public static int tir(String coordonnee){
            int tir;
            int ligne;
            int colone;

            ligne = Integer.parseInt(coordonnee.substring(0,1));
            colone = Integer.parseInt(coordonnee.substring(2,3));

            tir = (ligne * 8) + colone;

            return tir;
        }

        public static boolean touche(String solution,  int tir){
            boolean touchee=false;
            char solutionaire;

            solutionaire = solution.charAt(tir);
            if(solutionaire == 'B') {
                touchee = true;
            }
            return touchee;
        }

        public static String replace(String jeux,String solution, int tir, boolean touchee){
            String essaie;
            char test;

            test = jeux.charAt(tir);

            if(touchee & test == ' ') {
                essaie = jeux.substring(0, tir) + "B" + jeux.substring(tir, 63);
                System.out.println(TOUCHER);
                return essaie;
            }if(test == ' ') {
                essaie = jeux.substring(0, tir) + "X" + jeux.substring(tir, 63);
                System.out.println(TIR_ECHEC);
                return essaie;
            }else{
                System.out.println(TIR_REDONDANT);
                return jeux;
            }

        }

        public static String bateauPossition (String solution){
            String pos="";

            for(int i = 0; i < 10; i++){
                if (solution.charAt(i) == 'B') {
                    pos = pos + "0" + i;
                }
            }

            for(int i = 10; i < solution.length(); i++){
                if (solution.charAt(i) == 'B') {
                    pos = pos + i;
                }
            }
            return pos;
        }


        public static void main (String [] args){

            int munition;
            boolean rejouer = true;
            String solution;
            String jeux = "                                                                ";
            String coordonnes;
            int test=1;
            int tir;
            boolean touche;
            String bateauTout ;
            String cuirasse=null;
            String croiseur = null;
            String sousMarin = null;
            String destroyer = null;
            String test2;
            int test3;



            titre();

            do {
                niveau();
                munition = difficulte();
                solution = JeuUtils.genererGrilleSolution();
                System.out.println();
                JeuUtils.afficherGrille(jeux);

                do {
                    String bateau;
                    System.out.print(MUN_RESTANTE + munition);
                    System.out.println(VAISSEAU_COULLER);
                    tir = tir(coordonneeValid(coordonneeLongeur()));
                    jeux= replace(jeux, solution, tir,touche(solution,tir));

                    test2 = bateauPossition(solution);
                    test3 = Integer.decode(test2);
                    System.out.println(test3);




                    JeuUtils.afficherGrille(jeux);
                    JeuUtils.afficherGrille(solution);
                    munition--;


                }while(munition !=0);


            }while(rejouer);
            System.out.println(FIN);

        }
    }


