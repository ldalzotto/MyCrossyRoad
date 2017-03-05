package modele;

import enumeration.TypeBloc;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class Bloc {

    private TypeBloc _typeBloc;
    private boolean _isOuverture;

    public Bloc(TypeBloc typeBloc, boolean isOuverture){
        _typeBloc = typeBloc;
        _isOuverture = isOuverture;
    }

    public boolean getIsOuverture(){
        return _isOuverture;
    }

    public TypeBloc get_typeBloc() {
        return _typeBloc;
    }
}
