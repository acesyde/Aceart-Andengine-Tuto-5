package com.formation.deplacement;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.anddev.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.util.MathUtils;

import android.content.Context;

public class SceneJeu extends Scene implements IAnalogOnScreenControlListener {

	// Texture du tank
	private BitmapTextureAtlas texture;
	private TextureRegion textureRegionTank;
	
	// Textures des contrôles pour se déplacer
	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	
	// Controle analogique
	private AnalogOnScreenControl analogOnScreenControl;
	
	// Notre objet Tank
	private Tank tank;
	
	/**
	 * Constructeur 
	 * 
	 * @param pLayerCount
	 */
	public SceneJeu() {
		super();
		setBackground(new ColorBackground(0.52f, 0.75f, 0.03f));
	}
	
	/**
	 * Initialisation de la scéne
	 */
	private void init() {
		// Initialisation du controle analogique
		analogOnScreenControl = new AnalogOnScreenControl(0, 
				MonActivite.CAMERA_HAUTEUR - mOnScreenControlBaseTextureRegion.getHeight(), 
				MonActivite.camera, 
				mOnScreenControlBaseTextureRegion,
				mOnScreenControlKnobTextureRegion, 
				0.1f,
				200,
				this);
		
		// Spécifie que le contrôle analogie sera transparent 
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// Valeur de la transparence
        analogOnScreenControl.getControlBase().setAlpha(0.5f);
        // Taille du centre
        analogOnScreenControl.getControlBase().setScaleCenter(0, 128);
        // Redimensionnement de la base du controle
        analogOnScreenControl.getControlBase().setScale(0.5f);
        // Redimensionnement du joystick
        analogOnScreenControl.getControlKnob().setScale(0.5f);
        // Rafraichit le controle
        analogOnScreenControl.refreshControlKnobPosition();
         
        // Ajout du controle à la scéne enfant
		setChildScene(analogOnScreenControl);
		
		// Initialisation de notre tank
		tank = new Tank(0, 0, textureRegionTank.getWidth(), textureRegionTank.getHeight(), textureRegionTank);
		// Redimensionne notre tank
		tank.setScale(0.5f);
		
		// Ajout de notre tank à la scéne
		attachChild(tank);
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener#onControlChange(org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl, float, float)
	 */
	@Override
	public void onControlChange(BaseOnScreenControl pBaseOnScreenControl,float pValueX, float pValueY) {
		
		tank.setPosition( tank.getX() + (pValueX * 5), tank.getY() + (pValueY * 5));
		
		// Fait pivote notre tank selon la direction du joystick
		if(pValueX != 0 && pValueY != 0)		
			tank.setRotation(MathUtils.radToDeg((float)Math.atan2(pValueX, -pValueY)));		
	}

	/* (non-Javadoc)
	 * @see org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener#onControlClick(org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl)
	 */
	@Override
	public void onControlClick(AnalogOnScreenControl pAnalogOnScreenControl) {
		
	}
	
	/**
	 * Chargement des resources
	 * 
	 * @param engine
	 * @param context
	 */
	public void LoadResources(final Engine engine, Context context) {
		// Chargement des textures du tank
		texture = new BitmapTextureAtlas(128, 256);
		textureRegionTank = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texture, context, "gfx/tank.png", 0, 0);
		
		// Chargement des textures du contrôle analogique
		mOnScreenControlTexture = new BitmapTextureAtlas(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
        mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, context, "gfx/onscreen_control_base.png", 0, 0);
        mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, context, "gfx/onscreen_control_knob.png", 128, 0);
  
        // Chargement des textures dans le texture manager
        engine.getTextureManager().loadTextures(texture,mOnScreenControlTexture);
        
        // Lance l'initialisation de la scéne
        init();
	}

}
