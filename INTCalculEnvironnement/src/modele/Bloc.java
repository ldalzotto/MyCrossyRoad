package modele;

import enumeration.TypeBloc;

/**
 * Created by ldalzotto on 19/02/2017.
 */
public class Bloc {

    private TypeBloc typeBloc;
    private boolean isOuverture;

    public Bloc(TypeBloc typeBloc, boolean isOuverture){
        this.typeBloc = typeBloc;
        this.isOuverture = isOuverture;
    }

    public boolean getIsOuverture(){
        return isOuverture;
    }

    public TypeBloc getTypeBloc() {
        return typeBloc;
    }
}
