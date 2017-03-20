package INTEnvironnementManager.modele;

import common.enumeration.TypeLigne;
import modele.Bloc;

import java.util.List;

/**
 * Created by ldalzotto on 25/02/2017.
 */
public class LigneAffichage {

    public LigneAffichage(TypeLigne typeLigne, List<BlocAffichage> blocs, Integer menace){
        _blocList = blocs;
        _typeLigne = typeLigne;
        _menace = menace;
    }

    private TypeLigne _typeLigne;
    private List<BlocAffichage> _blocList;
    private Integer _menace;

    public TypeLigne get_typeLigne() {
        return _typeLigne;
    }

    public List<BlocAffichage> get_blocList() {
        return _blocList;
    }

    public Integer get_menace() {
        return _menace;
    }
}
