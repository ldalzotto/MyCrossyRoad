package com.my.crossy.road.screen.util.player.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.my.crossy.road.configuration.Configuration;
import com.my.crossy.road.configuration.PlayerConfiguration;
import com.my.crossy.road.constants.enumeration.Direction;
import com.my.crossy.road.entity.Entity;
import com.my.crossy.road.entity.component.abs.PhysicsComponent;
import com.my.crossy.road.exception.MaxPositionNonDeterminee;
import com.my.crossy.road.screen.util.MainGameScreenUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by ldalzotto on 06/03/2017.
 */
public class PlayerMovementManager implements IPlayerMovementManager {

    private static final String TAG = PlayerMovementManager.class.getSimpleName();

    private static PlayerMovementManager _instance = null;

    private Float _lastMaxBlocPosition = null;

    /**
     * Le nombre de bloc de largeur décalé par le joueur
     */
    private Integer _playerBlocWidthIndex = 0;

    public static PlayerMovementManager get_instance(){
        if(_instance == null){
            _instance = new PlayerMovementManager();
        }
        return _instance;
    }

    @Override
    public Boolean isAbleToCreateNewBlocs(List<Entity> blocs, Entity joueur) {

        Vector2 positionJoueur = joueur.get_physicsComponent().getHitbox().getPosition(new Vector2());

        //récupération de la valeur max
        Float maxPosition = null;
        try {
            maxPosition = MainGameScreenUtil.getMaxBlocPosition(blocs);
        } catch (MaxPositionNonDeterminee maxPositionNonDeterminee) {
            maxPositionNonDeterminee.printStackTrace();
        }
        if(_lastMaxBlocPosition == null){
            _lastMaxBlocPosition = maxPosition;
        }

        Optional<Rectangle> minRectangle = blocs.stream()
                .map(Entity::get_physicsComponent)
                .filter(Objects::nonNull)
                .map(PhysicsComponent::getHitbox)
                .filter(Objects::nonNull)
                .min(minRectangleCompoarator());

        //on a récupéré la valeur minimum y des blocs

        boolean isElligibleToCreate = true;

        if(minRectangle.isPresent()){
            Rectangle rectangle = minRectangle.get();
            //on calcul la distance entre ce bloc et le joueur
            Float distance = Math.abs(rectangle.getY() - positionJoueur.y);
            //si cette distance est plus petite que la taille max, on ne créé pas de blocs
            if(distance/Configuration.TAILLE_BLOC.get_valeur() < PlayerConfiguration.MAX_LINES_NB_TO_CREATE_NEW_BLOC.getValue()){
                Gdx.app.debug(TAG, "Pas de création de blocs pour ce mouvement : " +
                        Float.valueOf(distance/Configuration.TAILLE_BLOC.get_valeur()) + " < "
                        + PlayerConfiguration.MAX_LINES_NB_TO_CREATE_NEW_BLOC.getValue());
                isElligibleToCreate = false;
            }
        }

        //vérification si la position max actuelle est supérieur à celle du dernier mouvement éligible
        if(maxPosition > _lastMaxBlocPosition){
            isElligibleToCreate = false;
        }

        if(isElligibleToCreate){
            Gdx.app.debug(TAG, "Création de blocs pour ce mouvement.");
            _lastMaxBlocPosition = maxPosition;
        }

        return isElligibleToCreate;
    }

    public void updatePlayerWidthIndex(Direction direction){
        if(direction.equals(Direction.LEFT)){
            _playerBlocWidthIndex--;
        } else if(direction.equals(Direction.RIGHT)){
            _playerBlocWidthIndex++;
        }
    }

    public Integer getPlayerWidthIndex(){
        return _playerBlocWidthIndex;
    }

    private Comparator<Rectangle> minRectangleCompoarator() {
        return (o1, o2) -> {
            if(o1.getY() >= o2.getY()){
                return 1;
            } else {
                return -1;
            }
        };
    }



}
