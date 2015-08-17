package entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class Key extends Item
{
    public static final int DURATION = 600;
    
    private float x;
    private float y;
    
    private static Animation a = new Animation(new Image[] {Main.spriteSheet.getSprite(6, 6), Main.spriteSheet.getSprite(7, 6)}, DURATION, true);
    
    public Key(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void interact(Entity e) 
    {
        if (e instanceof Jonas)
        {
            ((Jonas) e).giveKey();
            remove();
        }
    }

    @Override
    public void render(float x, float y) 
    {
        a.draw((int) x, (int) y);
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
}
