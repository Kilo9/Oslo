package level;

import java.util.HashMap;
import java.util.Map;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class GrassTile extends Tile
{
    public static Image grass0 = Main.textureSheet.getSprite(0, 0);
    private static Image grass1 = Main.textureSheet.getSprite(1, 0);
    private static Image grass2 = Main.textureSheet.getSprite(2, 0);
    
    private static Map<Integer, Image> imgLoader = new HashMap<>();
    
    static
    {
        imgLoader.put(0, grass0);
        imgLoader.put(1, grass1);
        imgLoader.put(2, grass2);
    }
    
    private int ID;
    
    public GrassTile()
    {
        super("Grass");
        ID = (int) (Math.random() * 3);
    }

    @Override
    public void render(float x, float y) 
    {
        imgLoader.get(ID).draw(x, y);
    }
    

    @Override
    public boolean canPass() 
    {
        return true;
    }

    @Override
    public Image getImage()
    {
        return imgLoader.get(ID);
    }
}
