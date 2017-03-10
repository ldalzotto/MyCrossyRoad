package INTEnvironnementManager.modele;

import INTEnvironnementManager.enumeration.TypeBlocAffichage;
import enumeration.TypeBloc;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class BlocAffichage {
    private TypeBlocAffichage _typeBloc;
    private boolean _isOuverture;

    public BlocAffichage(TypeBlocAffichage typeBloc, boolean isOuverture){
        _typeBloc = typeBloc;
        _isOuverture = isOuverture;
    }

    public boolean getIsOuverture(){
        return _isOuverture;
    }

    public TypeBlocAffichage get_typeBloc() {
        return _typeBloc;
    }

    public Boolean isAnObstacle(){
        return _typeBloc.equals(TypeBlocAffichage.Obstacle) && !_isOuverture;
    }

    public Boolean isAPhantomObstacle(){
        return _typeBloc.equals(TypeBlocAffichage.PhantomObstacle);
    }

}
