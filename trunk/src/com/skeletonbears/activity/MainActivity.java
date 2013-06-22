package com.skeletonbears.activity;

import java.util.HashMap;

import nu.xom.Element;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.ButtonSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends SimpleBaseGameActivity {

	/**
	 * The main Android Activity where the game begins
	 * 
	 */
	
	//=====================
	//Constants
	//=====================
	
		public static int CAMERA_WIDTH = 1920;
		public static int CAMERA_HEIGHT = 1080;
	
	//=====================
	//Fields
	//=====================	
	
		/* HashMaps */
			HashMap<String, BitmapTextureAtlas> BTAMap;
			HashMap<String, ITextureRegion> ITRMap;
			HashMap<String, Sprite> SpriteMap;
			
		/* Misc */
			public Camera camera;
			public Scene scene;
			ScrollingBackground scrBackground;
			
	public EngineOptions onCreateEngineOptions() 
	{
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() 
	{
		/*Initialize the hashmaps*/
		BTAMap = new HashMap<String, BitmapTextureAtlas>();
		ITRMap = new HashMap<String, ITextureRegion>();
		SpriteMap = new HashMap<String, Sprite>();
		
		/*Create the textures*/
		addTexture("BG1", "kitties1.jpg", 0, 0, 1920, 1080);
		addTexture("BG2", "kitties2.png", 0, 0, 1920, 1080);
	}

	@Override
	protected Scene onCreateScene() 
	{
		scene = new Scene();
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		addSprite("BG1", "BG1", 0, 0);
		addSprite("BG2", "BG2", 0, 0);
		
		/* Create the background */
		Sprite[] bgSprites = 
			{
				getSprite("BG1"),
				getSprite("BG2")
			};
		scrBackground = new ScrollingBackground(bgSprites, 10);
		scene.attachChild(scrBackground);
		
		return scene;
	}
	
	/**
	 * Creates a texture resource
	 * @param key
	 * @param assetPath
	 * @param textureX
	 * @param textureY
	 * @param width
	 * @param height
	 */
	void addTexture(String key, String assetPath, int textureX, int textureY, int width, int height)
	{
		BitmapTextureAtlas BTA = new BitmapTextureAtlas(this.getTextureManager(), width, height, TextureOptions.BILINEAR);
		ITextureRegion ITR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(BTA, this, assetPath, textureX, textureY);
		BTAMap.put(key, BTA);
		ITRMap.put(key, ITR);
		BTA.load();
	}
	
	/**
	 * Creates a sprite
	 * @param key
	 * @param textureKey
	 * @param x
	 * @param y
	 */
	void addSprite(String key, String textureKey, int x, int y)
	{
		Sprite sprite = new Sprite(x, y, ITRMap.get(textureKey), this.getVertexBufferObjectManager());
		SpriteMap.put(key, sprite);
		scene.attachChild(sprite);
	}
	
	Sprite getSprite(String key)
	{
		return SpriteMap.get(key);
	}

}
