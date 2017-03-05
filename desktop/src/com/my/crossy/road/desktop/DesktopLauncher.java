package com.my.crossy.road.desktop;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.my.crossy.road.MyCrossyRoad;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		LwjglApplication app = new LwjglApplication(new MyCrossyRoad(), config);
		config.height = 480;
		config.width = 320;
		app.setLogLevel(Application.LOG_DEBUG);
	}
}
