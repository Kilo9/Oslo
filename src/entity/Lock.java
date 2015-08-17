package entity;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class Lock extends Item
{
    public static final int DURATION = 200;
    
    private float x;
    private float y;
    
    private static Animation a = new Animation(new Image[] {Main.spriteSheet.getSprite(0, 7), Main.spriteSheet.getSprite(1, 7)}, DURATION, true);
    
    public Lock(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void interact(Entity e) 
    {
        if (e instanceof Jonas)
        {
            if (((Jonas) e).hasKey())
            {
                remove();
            }
            //TODO sound
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
