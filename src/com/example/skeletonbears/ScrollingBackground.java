package com.example.skeletonbears;

import org.andengine.entity.Entity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.vbo.ISpriteVertexBufferObject;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;

/**
 * This class takes an array of sprites and laces them together as one continuously scrolling background.
 */
public class ScrollingBackground extends Entity{
	
	Sprite[] sprites;
	Scene scene;
	public float scrollSpeed;
	public float totalLength;
	
	public ScrollingBackground(Sprite[] sprites, float scrollSpeed, Scene scene)
	{
		this.sprites = sprites;
		this.scrollSpeed = scrollSpeed;
		this.scene = scene;
		initBackgrounds();
	}
	
	private void initBackgrounds() 
	{
		float offset = 0;
		for (Sprite sprite : this.sprites)
		{
			sprite.setX(offset);
			offset += sprite.getWidth();
			this.scene.attachChild(sprite);
		}
		this.totalLength = offset;
	}

	/**
	 * Scrolls the sprites
	 */
	@Override
	public void onManagedUpdate(float pSecondsElapsed)
	{
		for(Sprite sprite : this.sprites)
		{
			sprite.setX(sprite.getX()-this.scrollSpeed);
			if (sprite.getX() <= -sprite.getWidth())
			{
				sprite.setX(this.totalLength - sprite.getWidth());
			}
		}
	}
}
