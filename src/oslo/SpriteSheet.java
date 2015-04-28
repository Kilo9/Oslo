package oslo;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Jeremy Bassi
 */
public class SpriteSheet 
{
    private int width;
    private int height;
    
    private Image img;
    
    public SpriteSheet(String path, int width, int height)
    {
        this.width = width;
        this.height = height;
        
        try
        {
            img = new Image(path);
        } catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }        
    }
    
    public Image getSprite(int x, int y)
    {
        return img.getSubImage(x * width, y * height, width, height);
    }
}
