package entity;

import oslo.Audio;
import oslo.Main;

/**
 * 
 * @author Jeremy Bassi
 */
public class Coin extends Item
{
    private float x, y;
    
    public Coin(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(float x, float y) 
    {
        Main.spriteSheet.getSprite(0, 6).draw(x, y);
    }

    @Override
    public float getX() 
    {
        return x;
    }

    @Override
    public float getY() 
    {
        return y;
    }

    @Override
    public void interact(Entity e)
    {
        if (e instanceof Jonas)
        {
            Audio.coin();
            remove();
            ((Jonas) e).addCoin();
        }
    }
}
