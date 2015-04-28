package level;

import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class LightingTile extends Tile
{   
    private float alpha;
    
    private static Image image = Main.textureSheet.getSprite(5, 0);
    
    public LightingTile()
    {
        super("Lighting");
        alpha = 1.0f;
    }

    @Override
    public void render(float x, float y) 
    {
        render(x, y, alpha);
    }
    
    public void render(float x, float y, float a) 
    {
        Image tile = image.copy();
        tile.setAlpha(a);
        tile.draw((int) x, (int) y);
    }
    
    public void setAlpha(float alpha)
    {
        this.alpha = alpha;
    }
    
    public float getAlpha()
    {
        return alpha;
    }

    @Override
    public boolean canPass() 
    {
        return true;
    }
}
