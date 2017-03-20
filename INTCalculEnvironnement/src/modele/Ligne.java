package modele;

import common.enumeration.TypeLigne;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class Ligne {

    private TypeLigne typeLigne;
    private List<Bloc> blocs;

    public Ligne(TypeLigne typeLigne, List<Bloc> blocs) {
        this.typeLigne = typeLigne;
        this.blocs = blocs;
    }

    public List<Bloc> getOuvertures(){
        return blocs.stream().filter(Bloc::getIsOuverture)
                .collect(Collectors.toList());
    }

    /**
     * Retoure les index d'ouvertures. L'index est relatif et démarre à 0
     * @return valeur de 0 à environnementLargeur-1
     */
    public List<Integer> getOuverturesIndex(){
        return getOuvertures().stream()
                .map(this::indexOfBloc)
                .collect(Collectors.toList());
    }

    public TypeLigne getTypeLigne() {
        return typeLigne;
    }

    public List<Bloc> getBlocs() {
        return blocs;
    }

    private int indexOfBloc(Bloc bloc){
        return blocs.indexOf(bloc);
    }
}
