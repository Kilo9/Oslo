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
    private String name;
   
    public Tile(String name)
    {
        this.name = name;
    }
    
    public abstract void render(float x, float y);
    
    /**
     * Tells whether the entity can move onto the tile
     * @return can pass through
     */
    public abstract boolean canPass();
    
    public abstract Image getImage();
    
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
