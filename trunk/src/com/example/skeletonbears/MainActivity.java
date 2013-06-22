package com.example.skeletonbears;

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
	
		/* Graphics */
			BitmapTextureAtlas atlBG1;
			ITextureRegion texBG1;
			
			BitmapTextureAtlas atlBG2;
			ITextureRegion texBG2;
		
		/* Sprites */
			ScrollingBackground scrBackground;
			Sprite sprBG1;
			Sprite sprBG2;
			
		/* Misc */
			public Camera camera;
			public Scene scene;
			
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
		//Create Resources
		atlBG1 = new BitmapTextureAtlas(this.getTextureManager(), 1920, 1080, TextureOptions.BILINEAR);
		texBG1 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlBG1, this, "kitties1.jpg", 0, 0);
		
		atlBG2 = new BitmapTextureAtlas(this.getTextureManager(), 1920, 1080, TextureOptions.BILINEAR);
		texBG2 = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlBG2, this, "kitties2.png", 0, 0);
		
		//Load them up
		atlBG1.load();
		atlBG2.load();
	}

	@Override
	protected Scene onCreateScene() 
	{
		scene = new Scene();
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		sprBG1 = new Sprite(0, 0, texBG1, this.getVertexBufferObjectManager());
		sprBG2 = new Sprite(0, 0, texBG2, this.getVertexBufferObjectManager());
		
		/* Create the background */
		Sprite[] bgSprites = 
			{
				sprBG1,
				sprBG2
			};
		scrBackground = new ScrollingBackground(bgSprites, 10, scene);
		
		//Attach them to the scene
		scene.attachChild(scrBackground);
		
		return scene;
	}

}
