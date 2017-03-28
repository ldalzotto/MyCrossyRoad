package internal.environnement.manager.modele;

import common.enumeration.TypeLigne;

import java.util.List;

/**
 * Created by ldalzotto on 25/02/2017.
 */
public class LigneAffichage {

    private TypeLigne typeLigne;
    private List<BlocAffichage> blocList;

    public LigneAffichage(TypeLigne typeLigne, List<BlocAffichage> blocs){
        blocList = blocs;
        this.typeLigne = typeLigne;
    }

    public TypeLigne getTypeLigne() {
        return typeLigne;
    }

    public List<BlocAffichage> getBlocList() {
        return blocList;
    }

}
