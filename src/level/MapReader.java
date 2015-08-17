package level;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import oslo.Main;

/**
 * Interprets a map from the defined key for creating Map objects
 * @author Jeremy Bassi
 */
public class MapReader 
{
    /**
     * URL to the image of the key
     */
    public static final String KEY_PATH = "res/tex/TexKey.png";
    
    private Image key;
    
    //colors to be read from the key
    private Color grass;
    private Color water;
    private Color dirt;
    private Color rock;
    private Color portal;
    private Color flag;
    
    /**
     * Creates a mapreader by reading the colors and setting up the map 
     */
    public MapReader()
    {
        try
        {
            key = new Image(KEY_PATH);
        }
        catch (SlickException e)
        {
            System.err.println(e.getMessage());
        }
        
        grass = key.getColor(0, 0);
        water = key.getColor(1, 0);
        dirt = key.getColor(2, 0);
        rock = key.getColor(3, 0);
        portal = key.getColor(4, 0);
        flag = key.getColor(5, 0);
    }
    
    /**
     * reads an image and creates a map from that image with each pixel corresponding to one tile.
     * @param mapPath - URL to the image to be interpreted to create a map 
     * @return a Map read from the provided image
     */
    public Map read(String mapPath)
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
        
        Tile[][] tiles = new Tile[img.getWidth()][img.getHeight()];
        
        for (int i = 0; i < img.getWidth(); i++)
        {
            for (int j = 0; j < img.getHeight(); j++)
            {
                Color c = img.getColor(i, j);
                
                //start tile matching manually
                if (c.equals(grass))
                {
                    tiles[i][j] = new GrassTile();
                }
                else if (c.equals(dirt))
                {
                    tiles[i][j] = new DirtTile();
                }
                else if (c.equals(water))
                {
                    tiles[i][j] = new WaterTile();
                }
                else if (c.equals(rock))
                {
                    tiles[i][j] = new RockTile();
                }
                else if (c.equals(portal))
                {
                    tiles[i][j] = new PortalTile(i * Main.TILE_WIDTH, j * Main.TILE_HEIGHT);
                }
                else if (c.equals(flag))
                {
                    tiles[i][j] = new FlagTile(i * Main.TILE_WIDTH, j * Main.TILE_HEIGHT);
                }
            }
        }
        
        return new level.Map(tiles);
    }
}
