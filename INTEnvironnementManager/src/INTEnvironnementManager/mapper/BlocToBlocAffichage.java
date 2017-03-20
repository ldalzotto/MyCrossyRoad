package INTEnvironnementManager.mapper;

import INTEnvironnementManager.enumeration.TypeBlocAffichage;
import INTEnvironnementManager.mapper.interfaceMapper.IConverter;
import INTEnvironnementManager.modele.BlocAffichage;
import enumeration.TypeBloc;
import modele.Bloc;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class BlocToBlocAffichage implements IConverter<Bloc, BlocAffichage>{

    private static BlocToBlocAffichage _instance = null;

    public static BlocToBlocAffichage getInstance(){
        if(_instance == null){
            _instance = new BlocToBlocAffichage();
        }
        return _instance;
    }

    @Override
    public BlocAffichage apply(Bloc bloc) {
        TypeBloc typeBloc = bloc.get_typeBloc();
        TypeBlocAffichage typeBlocAffichage = TypeBlocAffichage.getValueFromString(typeBloc.name());
        return new BlocAffichage(typeBlocAffichage, bloc.getIsOuverture());
    }
}
