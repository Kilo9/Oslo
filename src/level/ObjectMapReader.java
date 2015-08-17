package level;

import entity.Coin;
import entity.Jonas;
import entity.Key;
import entity.Lock;
import entity.Olan;
import entity.Refiller;
import entity.Sentry;
import entity.Torch;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class ObjectMapReader 
{
    public static final String KEY_PATH = "res/sprites/SpriteKey.png";
    
    private Image cKey;
    private Map map;
    private Input input;
    
    private Color coin;
    private Color jonas;
    private Color olan;
    private Color torch;
    private Color sentry;
    private Color key;
    private Color lock;
    private Color refiller;
    
    private Jonas jon;
    
    public ObjectMapReader(Map map)
    {
        try
        {
            cKey = new Image(KEY_PATH);
        } catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }
        
        this.map = map;
        
        input = Main.getInput();
        
        jonas = cKey.getColor(0, 0);
        olan = cKey.getColor(1,0);
        coin = cKey.getColor(2, 0);
        torch = cKey.getColor(3, 0);
        sentry = cKey.getColor(4, 0);
        key = cKey.getColor(5, 0);
        lock = cKey.getColor(6, 0);
        refiller = cKey.getColor(7, 0);
    }
    
    public ObjectMap read(String mapPath)
    {
        Image img = null;
        
        try
        {
            img = new Image(mapPath);
        }
        catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }
        
        ObjectMap obj = new ObjectMap(img.getWidth(), img.getHeight());
        
        boolean hasJonas = false;
        
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                float x = (float) (i * Main.TILE_WIDTH);
                float y =(float) (j * Main.TILE_HEIGHT);
                
                //DETERMINE & PLACE ENTITIES/ITEMS------------------------------
                if (img.getColor(i, j).equals(jonas) && !hasJonas)
                {
                    jon = new Jonas(x, y, input, map, obj);
                    obj.add(jon);
                    hasJonas = true;
                }
                else if (img.getColor(i, j).equals(olan))
                {
                    obj.add(new Olan(x, y, map, obj));
                }
                else if (img.getColor(i, j).equals(coin))
                {
                    obj.add(new Coin(x, y));
                }
                else if (img.getColor(i, j).equals(torch))
                {
                    obj.add(new Torch(x, y));
                }
                else if (img.getColor(i, j).equals(sentry))
                {
                    obj.add(new Sentry((int) x,(int) y, map, obj));
                }
                else if (img.getColor(i, j).equals(key))
                {
                    obj.add(new Key((int) x, (int) y));
                }
                else if (img.getColor(i, j).equals(lock))
                {
                    obj.add(new Lock((int) x, (int) y));
                }
                else if (img.getColor(i, j).equals(refiller))
                {
                    obj.add(new Refiller((int) x,(int) y, map, obj));
                }
                else
                {
                    
                }
                //--------------------------------------------------------------
            }
        }
        
        if (!hasJonas)
        {
            jon = new Jonas(0, 0, input, map, obj);
            obj.add(jon);
        }
        
        return obj;
    }
    
    public Jonas getJonas()
    {
        return jon;
    }
}
