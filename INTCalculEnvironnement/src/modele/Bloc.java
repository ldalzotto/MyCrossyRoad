package modele;

import enumeration.TypeBloc;
import exception.ObectCopyException;

import java.util.Optional;
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

    public Bloc copy() {
        boolean isOuvertureRetour = this.isOuverture;
        Optional<TypeBloc> oTypeBlocRetour = Stream.of(TypeBloc.values()).filter(typeBloc1 -> typeBloc1.equals(this.getTypeBloc()))
                .findFirst();

        if(oTypeBlocRetour.isPresent()){
            return new Bloc(oTypeBlocRetour.get(), isOuvertureRetour);
        } else {
            throw new ObectCopyException("This instance cannot be copied !");
        }
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
