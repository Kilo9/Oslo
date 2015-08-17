package level;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class WaterTile extends Tile
{
    public static final int DURATION = 400;
    
    private static Animation anim = new Animation(new Image[] {Main.textureSheet.getSprite(5, 0), Main.textureSheet.getSprite(6, 0)}, DURATION, true);
    
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
    public void render(float x, float y) 
    {
        anim.draw(x, y);
    }

    @Override
    public Image getImage()
    {
        return anim.getCurrentFrame();
    }
    
}
