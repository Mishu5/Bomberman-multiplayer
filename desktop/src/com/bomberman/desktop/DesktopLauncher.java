package com.bomberman.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bomberman.client.Bomberman;

import static com.bomberman.common.utils.GraphicUtils.*;

/**
 * Please note that on macOS your application needs to
 * be started with the -XstartOnFirstThread JVM argument
 */
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(FPS);
		config.setTitle("Bomberman");
		config.setWindowSizeLimits(
				WINDOW_WIDTH / 2,
				WINDOW_HEIGHT / 2,
				WINDOW_WIDTH,
				WINDOW_HEIGHT
		);

		System.out.println("TEST");

		new Lwjgl3Application(new Bomberman(), config);
	}
}
