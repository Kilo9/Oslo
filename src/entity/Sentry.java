package entity;

import level.DirectionalLightSource;
import level.LightSource;
import level.Map;
import level.ObjectMap;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class Sentry extends LightEntity
{
    private static int num;
    
    public static final int DURATION = 400;
    public static final int LUX = 100;
    public static final int RADIUS = 6;
    public static final int SHIFT_RATE = 40;
    
    private DirectionalLightSource l;
    
    private static Animation a = new Animation(new Image[] {Main.spriteSheet.getSprite(4, 6), Main.spriteSheet.getSprite(5, 6)}, DURATION, true);
    
    private int tick;
    private int angle;
    private int id;
    
    public Sentry(int x, int y, Map map, ObjectMap obj)
    {
        super(x, y, map, obj);
        
        tick = 0;
        angle = 0;
        
        id = num++;
        
        l = new DirectionalLightSource(x, y, LUX, RADIUS, angle);
    }

    @Override
    public LightSource getLightSource() 
    {
        return l;
    }

    @Override
    public void update(int delta) 
    {
        if (++tick == SHIFT_RATE)
        {
            angle = shiftAngle();
            l.setAngle(angle);
            tick = 0;
        }
    }

    @Override
    public void render(float x, float y) 
    {
        a.draw((int) x,(int) y);
    } 
    
    private int shiftAngle()
    {
        if (++angle >= 8)
        {
            angle = 0;
        }
        
        return angle;
    }
    
    @Override
    public boolean show()
    {
        return true;
    }

    @Override
    public void interact(Entity e) 
    {
        if (e instanceof Jonas)
        {
            ((Jonas) e).enemyTouch();
        }
    }
    
    @Override
    public String toString()
    {
        return "Sentry " + id;
    }
}
