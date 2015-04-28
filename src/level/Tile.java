/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public abstract class Tile 
{
    public static Tile grassTile = new GrassTile();
    public static AnimationTile waterTile = new WaterTile();
    public static Tile dirtTile = new DirtTile();
    public static Tile rockTile = new RockTile();

    private String name;
   
    public Tile(String name)
    {
        this.name = name;
    }
    
    public abstract void render(float x, float y);
    
    public abstract boolean canPass();
    
    /**
     * utility method to get textures from the sheet
     * @param x - x val of loc on sheet
     * @param y - y val of loc on sheet
     * @return image at loc on sheet
     */
    public static Image getFromSheet(int x, int y)
    {
       return Main.textureSheet.getSprite(x, y);
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
