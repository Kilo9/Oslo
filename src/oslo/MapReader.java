/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oslo;

import level.Tile;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Jeremy Bassi
 */
public class MapReader 
{
    public static final String KEY_PATH = "res/maps/key.png";
    
    private Image key;
    
    private java.util.Map<Color, Tile> keyMap;
    
    private Color grass;
    private Color water;
    private Color dirt;
    private Color rock;
    
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
        
        keyMap = new java.util.HashMap<>();
        
        keyMap.put(grass, Tile.grassTile);
        keyMap.put(water, Tile.waterTile);     
        keyMap.put(dirt, Tile.dirtTile);
        keyMap.put(rock, Tile.rockTile);
    }
    
    public oslo.Map read(String mapPath)
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
                tiles[i][j] = match(img.getColor(i, j));
            }
        }
        
        return new oslo.Map(tiles);
    }
    
    private Tile match(Color c)
    {
        return keyMap.get(c);
    }   
}
