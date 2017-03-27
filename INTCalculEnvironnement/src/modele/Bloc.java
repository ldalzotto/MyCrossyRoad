package modele;

import enumeration.TypeBloc;

import java.util.stream.Stream;

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

    public Bloc copy(){
        boolean isOuvertureRetour = this.isOuverture;
        TypeBloc typeBlocRetour = Stream.of(TypeBloc.values()).filter(typeBloc1 -> typeBloc1.equals(this.getTypeBloc()))
                .findFirst().get();

        return new Bloc(typeBlocRetour, isOuvertureRetour);
    }

    public TypeBloc getTypeBloc() {
        return typeBloc;
    }

    @Override
    public String toString() {
        return "Bloc{" +
                "typeBloc=" + typeBloc +
                ", isOuverture=" + isOuverture +
                '}';
    }
}
