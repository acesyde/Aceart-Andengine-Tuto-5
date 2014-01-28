package com.formation.deplacement;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MonActivite extends BaseGameActivity {
	
	public static Camera camera;
	// Déclaration de la scéne du jeu
	private SceneJeu maScene;
	
	public final static int CAMERA_LARGEUR = 480;
	public final static int CAMERA_HAUTEUR = 320;
	
	@Override
	public Engine onLoadEngine() {
		// Initialisation de la caméra
		camera = new Camera(0, 0, CAMERA_LARGEUR, CAMERA_HAUTEUR);
		
		// Initialisation de la scéne du jeu
		maScene = new SceneJeu();
		
		// Retourne le moteur de jeu
		return new Engine(new EngineOptions(true, 
				ScreenOrientation.LANDSCAPE, 
				new RatioResolutionPolicy(CAMERA_LARGEUR, CAMERA_HAUTEUR), 
				camera));
	}

	@Override
	public void onLoadResources() {
		// Chargement des textures de la scéne
		maScene.LoadResources(getEngine(), this);	
	}

	@Override
	public Scene onLoadScene() {
		// Retourne la scéne
		return maScene;
	}

	@Override
	public void onLoadComplete() {
		
	}
}