package com.skeletonbears.activity;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;

/**
 * This class takes an array of sprites and laces them together as one continuously scrolling background.
 */
public class ScrollingBackground extends Entity{
	
	Sprite[] sprites;
	public float scrollSpeed;
	public float totalLength;
	
	public ScrollingBackground(Sprite[] sprites, float scrollSpeed)
	{
		this.sprites = sprites;
		this.scrollSpeed = scrollSpeed * MainActivity.screenStretch;
		initBackgrounds();
	}
	
	private void initBackgrounds() 
	{
		float offset = 0;
		for (Sprite sprite : this.sprites)
		{
			sprite.setX(offset);
			offset += getSpriteWidth(sprite);
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
			if (sprite.getX() <= -getSpriteWidth(sprite))
			{
				sprite.setX(this.totalLength - getSpriteWidth(sprite));
			}
		}
	}
	
	public float getSpriteWidth(Sprite sprite)
	{
		return sprite.getWidth() * sprite.getScaleX();
	}
}
