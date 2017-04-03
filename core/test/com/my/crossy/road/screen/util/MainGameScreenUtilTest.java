package com.my.crossy.road.screen.util;

import internal.environnement.manager.enumeration.TypeBlocAffichage;
import internal.environnement.manager.modele.BlocAffichage;
import com.my.crossy.road.entity.Entity;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ldalzotto on 18/03/2017.
 */
public class MainGameScreenUtilTest {
    @Test
    public void generateEntity() throws Exception {
        BlocAffichage blocAffichage = new BlocAffichage(TypeBlocAffichage.DECOR, false);
        Entity entity = MainGameScreenUtil.generateEntity(blocAffichage);

        Assert.assertTrue(entity != null);
        Assert.assertTrue(entity.getPhysicsComponent() == null);

        Assert.assertTrue(true);
    }

}