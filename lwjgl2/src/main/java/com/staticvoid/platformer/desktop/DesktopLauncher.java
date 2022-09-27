package com.staticvoid.platformer.desktop;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.staticvoid.platformer.PlatformerGame;

/** Launches the desktop (LWJGL) application. */
public class DesktopLauncher {
	public static void main(String[] args) {
		createApplication();
	}

	private static LwjglApplication createApplication() {
		return new LwjglApplication(new PlatformerGame(), getDefaultConfiguration());
	}

	private static LwjglApplicationConfiguration getDefaultConfiguration() {
		LwjglApplicationConfiguration configuration = new LwjglApplicationConfiguration();
		configuration.title = "HollowBitPlatformer";
//		configuration.width = 640;
//		configuration.height = 480;
		configuration.width = 800;
		configuration.height = 600;
		//// This prevents a confusing error that would appear after exiting normally.
		configuration.forceExit = false;

		for (int size : new int[] { 128, 64, 32, 16 }) {
			configuration.addIcon("libgdx" + size + ".png", FileType.Internal);
		}
		return configuration;
	}

}

// TODO:  note -->  git push --set-upstream origin player-collisions