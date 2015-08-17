package level;

import entity.Item;
import entity.Lock;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class FlagTile extends ItemTile
{
    public static final int DURATION = 200;
    
    private static Animation a = new Animation(new Image[] {Main.textureSheet.getSprite(3, 1), Main.textureSheet.getSprite(4, 1), Main.textureSheet.getSprite(5, 1), Main.textureSheet.getSprite(6, 1)},  DURATION, true);

    private Lock lock;
    
    private float x;
    private float y;
    
    public FlagTile(float x, float y)
    {
        super("Flag");
        this.x = x;
        this.y = y;
        lock = new Lock(x, y);
    }

    @Override
    public void render(float x, float y) 
    {
        a.draw((int) x,(int) y);
    }

    @Override
    public boolean canPass() 
    {
        return true;
    }

    @Override
    public Image getImage() 
    {
        return a.getCurrentFrame();
    }

    @Override
    public Item getItem() 
    {
        return lock;
    }
    
    public boolean isUnlocked()
    {
        return lock.isRemoved();
    }
    
}
