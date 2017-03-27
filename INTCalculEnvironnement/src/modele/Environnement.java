package modele;

import enumeration.Configuration;
import exception.ConstructionLigneOrdonnee;
import exception.EnvironnementLigneNonRenseignee;
import exception.LigneNonRenseignee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class Environnement {

    private int longueur;
    private List<Ligne> lignes;
    private int lignesCurseur;

    public Environnement(){
        longueur = Configuration.ENVIRONNEMENT_LONGUEUR.getValeur();
        lignes = new ArrayList<>();
        lignesCurseur = 0;
    }

    /**
     * Cette méthode permet d'ajouter une ligne à l'environnement. La position du curseur représente la
     * position future sur laquelle la nouvelle ligne sera insérée.
     * @param ligne la ligne à insérer
     * @return la position du curseur où la ligne a été insérée
     * @throws LigneNonRenseignee si la ligne ne peut pas être insérée
     */
    public int ajoutLigne(Ligne ligne) throws LigneNonRenseignee{
        if(ligne != null){
            if(lignesCurseur >= longueur){
                lignesCurseur = 0;
            }

            if(lignes.size() == longueur){
                lignes.set(lignesCurseur, ligne);
            } else {
                lignes.add(ligne);
            }
            int lignesCurseurAvant = lignesCurseur;
            lignesCurseur++;
            return lignesCurseurAvant;
        } else {
            throw new LigneNonRenseignee("La ligne à ajouter est nulle", null);
        }
    }

    /**
     * Permet retourner la dernière ligne créé
     * @return dernière ligne créé
     * @throws EnvironnementLigneNonRenseignee si l'{@link Environnement} n'est pas correctement renseigné
     */
    public Ligne getLigneActuelle() throws EnvironnementLigneNonRenseignee {
        if(lignesCurseur == 0){
            throw new EnvironnementLigneNonRenseignee("Le curseur de ligne est hors de la liste de ligne !", null);
        } else {
            return lignes.get(lignesCurseur -1);
        }
    }

    /**
     * Permet de renvoyer un stream de ligne. Le premier index de ce stream correspond à la ligne associée
     * à lignesCurseur
     * @return Le stream de ligne ordonné
     */
    public Stream<Ligne> getLignesDepuisCurseur() throws ConstructionLigneOrdonnee{
        try {
            //dernière ligne renseignée
            Ligne curseurLigne = getLigneActuelle();

            //Ligne après le curseur
            List<Ligne> apresCurseurLignes = new ArrayList<>();
            apresCurseurLignes = lignes.subList(lignesCurseur, lignes.size());

            //Ligne avant le curseur
            List<Ligne> avantCurseurLignes = lignes.subList(0, lignesCurseur -1);

            List<Ligne> lignesOrdonnees = new ArrayList<>();
            lignesOrdonnees.addAll(apresCurseurLignes);
            lignesOrdonnees.addAll(avantCurseurLignes);
            lignesOrdonnees.add(curseurLigne);

            return lignesOrdonnees.stream();

        } catch (Exception ex) {
            throw new ConstructionLigneOrdonnee("Une erreur est survenue lors de la création de la ligne ordonnee", ex);
        }
    }

    public int getLignesCurseur(){
        return lignesCurseur;
    }

    public List<Ligne> getLignes() {return lignes;}
}
