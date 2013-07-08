package com.skeletonbears.activity;

import java.util.HashMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.primitive.Line;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import android.graphics.Point;
import android.view.Display;

import com.skeletonbears.filedata.LevelLoader;
import com.skeletonbears.level.LevelData;

public class MainActivity extends SimpleBaseGameActivity 
{

	/**
	 * The main Android Activity where the game begins
	 * 
	 */
	
	//=====================
	//Constants
	//=====================
	/** The fraction of the screen that is above the horizon */
	static final float HORIZON_POS = 2 / 5f;
	//=====================
	//Fields
	//=====================	
	/* HashMaps */
	HashMap<String, BitmapTextureAtlas> BTAMap;
	HashMap<String, ITextureRegion> ITRMap;
	HashMap<String, Sprite> SpriteMap;
	
	/* Screen info */
	public int screenWidth = 1920;
	public int screenHeight = 1080;
	public static float screenStretch;
	
	/* Misc */
	public Camera camera;
	public Scene scene;
	ScrollingBackground scrBackground;
			
	@SuppressWarnings("deprecation")
	public EngineOptions onCreateEngineOptions() 
	{
		/* get screen size */
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		try // Newer devices
		{
	        display.getSize(size);
	    }
		catch (java.lang.NoSuchMethodError whatevsdude) // Older device
	    { 
	        size.x = display.getWidth();
	        size.y = display.getHeight();
	    }
		screenWidth = size.x;
		screenHeight = size.y;
		screenStretch = screenHeight / 1080f;		
		camera = new Camera(0, 0, screenWidth, screenHeight);
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
		addTexture("BG1", "backgrounds/background.png", 0, 0, 1920, 1080);
	}

	@Override
	protected Scene onCreateScene() 
	{
		scene = new Scene();
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		addSprite("BG1", "BG1", 0, 0);
		addSprite("BG2", "BG1", 0, 0);
		/* Scale the background 7*/
		/*getSprite("BG1").setScaleCenter(0, 0);
		getSprite("BG2").setScaleCenter(0, 0);
		getSprite("BG1").setScale(screenStretch);
		getSprite("BG2").setScale(screenStretch);*/
		
		/* Create the background */
		Sprite[] bgSprites = {getSprite("BG1"), getSprite("BG2")};
		scrBackground = new ScrollingBackground(bgSprites, 10);
		scene.attachChild(scrBackground);
		
		/* Add the lanes */
		addLanes();
		
		scene.registerUpdateHandler(new FPSLogger());
		LevelData l = LevelLoader.ReadLevelData("levelone", this);
		
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
	 * @param key.
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
	
	/**
	 * Get a sprite from the map
	 * @param key
	 * @return sprite
	 */
	Sprite getSprite(String key)
	{
		return SpriteMap.get(key);
	}
	
	/**
	 * Draws lanes
	 */
	void addLanes()
	{
		float increment = (screenHeight * (1 - HORIZON_POS)) / 5;
		for(int i = 0; i < 5; i++)
		{
			Line line = new Line(0, screenHeight * HORIZON_POS + i * increment, screenWidth, screenHeight * HORIZON_POS + i * increment, this.getVertexBufferObjectManager());
			line.setColor(Color.BLACK);
			line.setLineWidth(5);
			scene.attachChild(line);
		}
	}

}
