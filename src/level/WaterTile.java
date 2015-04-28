package level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class WaterTile extends AnimationTile
{
    public static final int DURATION = 400;
    
    private static Animation anim = new Animation(new Image[] {Main.textureSheet.getSprite(3, 0), Main.textureSheet.getSprite(4, 0)}, DURATION, false);
    
    public WaterTile()
    {
        super("Water");
    }

    @Override
    public boolean canPass() 
    {
        return true;
    }

    @Override
    public void update(int delta) 
    {
        anim.update(delta);
    }

    @Override
    public void render(float x, float y) 
    {
        anim.draw((int) x,(int) y);
    }
    
}
