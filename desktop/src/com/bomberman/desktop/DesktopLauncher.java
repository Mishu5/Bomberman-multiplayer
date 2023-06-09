package com.bomberman.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.bomberman.client.Bomberman;

import static com.bomberman.common.utils.GraphicUtils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.IOException;

/**
 * Please note that on macOS your application needs to
 * be started with the -XstartOnFirstThread JVM argument
 */
public class DesktopLauncher {
	public static void main (String[] arg) {

		/*
		//getting ip
		String ip = getIp("config.txt");
		System.out.println(ip);
		*/

		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(FPS);
		config.setTitle("Bomberman");
		config.setWindowSizeLimits(
				WINDOW_WIDTH / 2,
				WINDOW_HEIGHT / 2,
				WINDOW_WIDTH,
				WINDOW_HEIGHT
		);

		new Lwjgl3Application(new Bomberman(), config);
	}

	private static String getIp(String filename){

		String ip = null;
		File config;
		Scanner file;

		try{

			config = new File(filename);
			file = new Scanner(config);

			ip = file.nextLine();

			file.close();
		}catch (FileNotFoundException e){
			System.out.println("No file");
			return null;
		}

		return ip;
	}

}
