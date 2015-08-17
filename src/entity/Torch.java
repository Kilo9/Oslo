package entity;

import level.LightSource;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class Torch extends LightItem
{
    public static final int DEFAULT_LUX = 100;
    public static final int DEFAULT_RADIUS = 5;
    public static final int DURATION = 300;
    
    private float x;
    private float y;
    
    private boolean on;
    
    private static Animation a = new Animation(new Image[] {Main.spriteSheet.getSprite(1,6), Main.spriteSheet.getSprite(2, 6)}, DURATION, true);
    
    private LightSource l;
    
    public Torch(float x, float y)
    {
        this.x = x;
        this.y = y;
        l = new LightSource((int) x + Jonas.SPRITE_WIDTH / 2,(int) y + Jonas.SPRITE_HEIGHT / 2, DEFAULT_LUX, DEFAULT_RADIUS);
        on = true;
        l.setMode(on);
    }

    @Override
    public LightSource getLightSource() 
    {
        return l;
    }
    
    public void setMode(boolean on)
    {
        this.on = on;
        l.setMode(on);
    }

    @Override
    public void interact(Entity e) 
    {
        if (e instanceof Jonas && ((Jonas) e).isAction())
        {
            setMode(!on);
        }
    }

    @Override
    public void render(float x, float y) 
    {
        if (!on)
        {
            Main.spriteSheet.getSprite(3, 6).draw(x, y);
            return;
        }
        
        a.draw(x, y);
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
