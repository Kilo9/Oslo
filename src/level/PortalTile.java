package level;

import java.util.ArrayList;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import oslo.Main;

/**
 * Portals which will allow transportation.
 * Cycles through the list of portals.
 * 
 * @author Jeremy Bassi
 */
public class PortalTile extends Tile
{
    public static final int DURATION = 200;
    
    private static int count = 0;

    private static Animation a = new Animation(new Image[] {Main.textureSheet.getSprite(0, 1), Main.textureSheet.getSprite(1, 1), Main.textureSheet.getSprite(2, 1)}, DURATION);
    
    private static ArrayList<PortalTile> portals = new ArrayList<>();
    
    private int x;
    private int y;
    private int id; 
    
    public PortalTile(int x, int y)
    {
        super("Portal");
        
        this.x = x;
        this.y = y;
        
        id = count++;
        
        portals.add(this);
    }

    @Override
    public void render(float x, float y) 
    {
        a.draw(x, y);
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
    
    public static ArrayList<PortalTile> getPortals()
    {
        return portals;
    }
    
    public int getID()
    {
        return id;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }  
}
