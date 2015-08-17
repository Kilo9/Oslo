package entity;

import level.Map;
import level.ObjectMap;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class Refiller extends Entity
{
    public static final int DURATION = 100;
    
    private static Animation a = new Animation(new Image[] {Main.spriteSheet.getSprite(2, 7), Main.spriteSheet.getSprite(3, 7), Main.spriteSheet.getSprite(4, 7)}, DURATION, true);

    static 
    {
        a.setPingPong(true);
    }
    
    public Refiller(int x, int y, Map map, ObjectMap obj)
    {
        super(x, y, map, obj);
    }
    
    @Override
    public void update(int delta) 
    {
        
    }

    @Override
    public void interact(Entity e) 
    {
        if (e instanceof Jonas)
        {
            ((Jonas)e).refill();
        }
    }

    @Override
    public void render(float x, float y) 
    {
        a.draw((int) x, (int) y);
    }
    
}
