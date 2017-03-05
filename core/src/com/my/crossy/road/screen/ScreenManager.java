package com.my.crossy.road.screen;

import com.badlogic.gdx.Screen;

/**
 * Created by ldalzotto on 12/11/2016.
 */
public class ScreenManager {

    private static ScreenManager _instance = null;

    protected MainGameScreen _mainGameScreen;

    public enum ScreenType{
        MAIN_GAME_SCREEN;
    }

    private Screen _currentScreenDisplayed;

    private ScreenManager() {
        _mainGameScreen = new MainGameScreen();
    }

    public static ScreenManager getInstance(){
        if(_instance == null){
            _instance = new ScreenManager();
        }
        return _instance;
    }

    public void setScreen(ScreenType screenType){
        switch (screenType){
            case MAIN_GAME_SCREEN:
                _currentScreenDisplayed = getScreen(screenType);
                break;
            default:
                _currentScreenDisplayed = null;
                break;
        }
    }


    public Screen getScreen(ScreenType screenType){
        switch (screenType){
            case MAIN_GAME_SCREEN:
                return _mainGameScreen;
            default:
                return null;
        }
    }

    public void dispose(){
        _mainGameScreen.dispose();
    }

    public Screen get_currentScreenDisplayed() {
        return _currentScreenDisplayed;
    }
}
