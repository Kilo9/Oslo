package level;

import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class GrassTile extends Tile
{
    private float a;
    
    private static Image img = Main.textureSheet.getSprite(0, 0);
    
    public GrassTile()
    {
        super("Grass");
    }

    @Override
    public void render(float x, float y) 
    {
        img.draw((int) x, (int)y);
    }

    @Override
    public boolean canPass() 
    {
        return true;
    }
}
