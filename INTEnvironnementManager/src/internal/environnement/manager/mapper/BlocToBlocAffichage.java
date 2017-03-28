package internal.environnement.manager.mapper;

import internal.environnement.manager.enumeration.TypeBlocAffichage;
import internal.environnement.manager.mapper.interfaceMapper.IConverter;
import internal.environnement.manager.modele.BlocAffichage;
import enumeration.TypeBloc;
import modele.Bloc;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class BlocToBlocAffichage implements IConverter<Bloc, BlocAffichage>{

    private static BlocToBlocAffichage instance = null;

    public static BlocToBlocAffichage getInstance(){
        if(instance == null){
            instance = new BlocToBlocAffichage();
        }
        return instance;
    }

    @Override
    public BlocAffichage apply(Bloc bloc) {
        TypeBloc typeBloc = bloc.getTypeBloc();
        TypeBlocAffichage typeBlocAffichage = TypeBlocAffichage.getValueFromString(typeBloc.name());
        return new BlocAffichage(typeBlocAffichage, bloc.getIsOuverture());
    }
}
