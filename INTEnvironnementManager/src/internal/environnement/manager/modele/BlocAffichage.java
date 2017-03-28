package internal.environnement.manager.modele;

import internal.environnement.manager.enumeration.TypeBlocAffichage;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class BlocAffichage {
    private TypeBlocAffichage typeBloc;
    private boolean isOuverture;

    public BlocAffichage(TypeBlocAffichage typeBloc, boolean isOuverture){
        this.typeBloc = typeBloc;
        this.isOuverture = isOuverture;
    }

    public Boolean isAnObstacle(){
        return typeBloc.equals(TypeBlocAffichage.OBSTACLE)
                && !isOuverture;
    }

    public Boolean isAPhantomObstacle(){
        return typeBloc.equals(TypeBlocAffichage.PHANTOM_OBSTACLE);
    }

}
