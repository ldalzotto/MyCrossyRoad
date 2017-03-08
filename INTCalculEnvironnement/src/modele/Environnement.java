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

    private int _longueur;
    private int _largeur;
    private List<Ligne> _lignes;
    private int _lignesCurseur;

    public Environnement(){
        _largeur = Configuration.EnvironnementLargeur.get_valeur();
        _longueur = Configuration.EnvironnementLongueur.get_valeur();
        _lignes = new ArrayList<>();
        _lignesCurseur = 0;
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

            if(_lignesCurseur == 49){
                System.out.println("feef");
            }

            if(_lignesCurseur >= _longueur){
                _lignesCurseur = 0;
            }

            if(_lignes.size() == _longueur){
                _lignes.set(_lignesCurseur, ligne);
            } else {
                _lignes.add(ligne);
            }
            int lignesCurseurAvant = _lignesCurseur;
            _lignesCurseur ++;
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
        if(_lignesCurseur-1 > _lignes.size() || _lignesCurseur == 0){
            throw new EnvironnementLigneNonRenseignee("Le curseur de ligne est hors de la liste de ligne !", null);
        } else {
            return _lignes.get(_lignesCurseur-1);
        }
    }

    /**
     * Permet de renvoyer un stream de ligne. Le premier index de ce stream correspond à la ligne associée
     * à _lignesCurseur
     * @return Le stream de ligne ordonné
     */
    public Stream<Ligne> getLignesDepuisCurseur() throws ConstructionLigneOrdonnee{
        try {
            //dernière ligne renseignée
            Ligne curseurLigne = getLigneActuelle();

            //Ligne après le curseur
            List<Ligne> apresCurseurLignes = new ArrayList<>();
            if(_lignesCurseur != _lignes.size()-1){
                apresCurseurLignes = _lignes.subList(_lignesCurseur, _lignes.size());
            }

            //Ligne avant le curseur
            List<Ligne> avantCurseurLignes = new ArrayList<>();
            if(_lignesCurseur-1 > 0){
                avantCurseurLignes = _lignes.subList(0, _lignesCurseur-1);
            }

            List<Ligne> lignesOrdonnees = new ArrayList<>();
            lignesOrdonnees.addAll(apresCurseurLignes);
            lignesOrdonnees.addAll(avantCurseurLignes);
            lignesOrdonnees.add(curseurLigne);

            if(lignesOrdonnees.size() != _lignes.size()){
                throw new ConstructionLigneOrdonnee("La ligne ordonee ne possède pas le bon format", null);
            }

            return lignesOrdonnees.stream();

        } catch (Exception ex) {
            throw new ConstructionLigneOrdonnee("Une erreur est survenue lors de la création de la ligne ordonnee", ex);
        }
    }

    public int get_lignesCurseur(){
        return _lignesCurseur;
    }

    public List<Ligne> get_lignes() {return _lignes;}
}
