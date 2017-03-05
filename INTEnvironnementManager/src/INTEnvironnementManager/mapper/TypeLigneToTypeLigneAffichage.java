package INTEnvironnementManager.mapper;

import INTEnvironnementManager.enumeration.TypeLigneAffichage;
import INTEnvironnementManager.mapper.interfaceMapper.IConverter;
import enumeration.TypeLigne;

import java.util.Arrays;

/**
 * Created by ldalzotto on 27/02/2017.
 */
public class TypeLigneToTypeLigneAffichage implements IConverter<TypeLigne, TypeLigneAffichage> {

    private static TypeLigneToTypeLigneAffichage _instance = null;

    public static TypeLigneToTypeLigneAffichage getInstance(){
        if(_instance == null){
            _instance = new TypeLigneToTypeLigneAffichage();
        }
        return _instance;
    }

    @Override
    public TypeLigneAffichage apply(TypeLigne typeLigne) {
        return Arrays.asList(TypeLigneAffichage.values()).stream()
                .filter(typeLigneAffichage -> typeLigneAffichage.name().equals(typeLigne.name()))
                .findAny()
                .orElse(null);
    }
}
