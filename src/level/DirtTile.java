/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import org.newdawn.slick.Image;
import oslo.Main;

/**
 *
 * @author Jeremy
 */
public class DirtTile extends Tile
{
    private static Image img = Main.textureSheet.getSprite(3, 0);
    
    public DirtTile()
    {
        super("Dirt");
    }

    @Override
    public void render(float x, float y) 
    {
        img.draw(x, y);
    }

    @Override
    public boolean canPass() 
    {
        return true;
    }

    @Override
    public Image getImage()
    {
        return img;
    }
}
