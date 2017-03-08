package modele;

import enumeration.Configuration;
import enumeration.TypeLigne;
import exception.MalformedLineException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class Ligne {

    private int _menace;
    private TypeLigne _typeLigne;
    private List<Bloc> _blocs;

    public Ligne(int menace, TypeLigne typeLigne, List<Bloc> blocs) throws MalformedLineException{

        int environnementLargeur = Configuration.EnvironnementLargeur.get_valeur();

        if(blocs == null || blocs.size() > environnementLargeur){
            throw new MalformedLineException("La ligne possède un format incorrect", null);
        }

        _menace = menace;
        _typeLigne = typeLigne;
        _blocs = blocs;
    }

    public List<Bloc> getOuvertures(){
        return _blocs.stream().filter((bloc -> bloc.getIsOuverture()))
                .collect(Collectors.toList());
    }

    /**
     * Retoure les index d'ouvertures.
     * @return valeur de 0 à environnementLargeur-1
     */
    public List<Integer> getOuverturesIndex(){
        return getOuvertures().stream()
                .map((bloc -> _blocs.indexOf(bloc)))
                .collect(Collectors.toList());
    }

    public TypeLigne get_typeLigne() {
        return _typeLigne;
    }

    public List<Bloc> get_blocs() {
        return _blocs;
    }
}
