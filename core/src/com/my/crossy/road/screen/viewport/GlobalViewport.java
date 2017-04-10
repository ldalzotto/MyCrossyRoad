package com.my.crossy.road.screen.viewport;

import com.badlogic.gdx.Gdx;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public abstract class GlobalViewport {

    private static final String TAG = GlobalViewport.class.getSimpleName();

    public static class VIEWPORT {
        static float viewportWidth;
        static float viewportHeight;
        static float virtualWidth;
        static float virtualHeight;
        static float physicalWidth;
        static float physicalHeight;
        static float aspectRation;

        VIEWPORT(){
            throw new InstantiationError("This class cannot be instantiated !");
        }

        public static float getViewportWidth() {
            return viewportWidth;
        }

        public static float getViewportHeight() {
            return viewportHeight;
        }

    }

    public static void setupViewport(int width, int height){
        //Makethe viewport a percentage of the total display area
        VIEWPORT.virtualWidth = width;
        VIEWPORT.virtualHeight = height;

        //Current viewport dimensions
        VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
        VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;

        //pixel dimensions of diplay
        VIEWPORT.physicalWidth = Gdx.graphics.getWidth();
        VIEWPORT.physicalHeight = Gdx.graphics.getHeight();

        //aspect ratio for current viewport
        VIEWPORT.aspectRation = VIEWPORT.virtualWidth/ VIEWPORT.virtualHeight;

        //update viewport if there could be skewing
        if(VIEWPORT.physicalWidth / VIEWPORT.physicalHeight >= VIEWPORT.aspectRation) {
            //letterbox left and right
            VIEWPORT.viewportWidth = VIEWPORT.viewportHeight * (VIEWPORT.physicalWidth / VIEWPORT.physicalHeight);
            VIEWPORT.viewportHeight = VIEWPORT.virtualHeight;
        } else {
            //letterbox above and below
            VIEWPORT.viewportWidth = VIEWPORT.virtualWidth;
            VIEWPORT.viewportHeight = VIEWPORT.viewportWidth * (VIEWPORT.physicalHeight / VIEWPORT.physicalWidth);
        }

        Gdx.app.debug(TAG, "WorldRenderer: virtual: (" + VIEWPORT.virtualWidth + "," + VIEWPORT.virtualHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: viewport: (" + VIEWPORT.viewportWidth + "," + VIEWPORT.viewportHeight + ")");
        Gdx.app.debug(TAG, "WorldRenderer: physical: (" + VIEWPORT.physicalWidth + "," + VIEWPORT.physicalHeight + ")");

    }

}
