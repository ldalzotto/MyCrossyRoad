package com.my.crossy.road.screen;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import runner.GdxTestRunner;


/**
 * Created by ldalzotto on 27/03/2017.
 */
@RunWith(GdxTestRunner.class)
public class MainGameScreenTest {
    @Test
    public void show() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();
    }

    @Test
    public void render() throws Exception {
        MainGameScreen mainGameScreen = Mockito.spy(MainGameScreen.class);
        Mockito.doReturn(Mockito.mock(ModelBatch.class)).when(mainGameScreen).createModelBatch();

        mainGameScreen.show();
        mainGameScreen.render(0.01f);
    }


}