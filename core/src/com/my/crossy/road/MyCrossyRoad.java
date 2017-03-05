package com.my.crossy.road;

import com.badlogic.gdx.Game;
import com.my.crossy.road.screen.ScreenManager;

public class MyCrossyRoad extends Game {

	private ScreenManager _screenManager;

	@Override
	public void create () {
		_screenManager = ScreenManager.getInstance();
		_screenManager.setScreen(ScreenManager.ScreenType.MAIN_GAME_SCREEN);
		setScreen(_screenManager.get_currentScreenDisplayed());
	}

	@Override
	public void dispose () {
		_screenManager.dispose();
	}
}
