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
public class RockTile extends Tile
{
    private static Image img = Main.textureSheet.getSprite(4, 0);
    
    public RockTile()
    {
        super("rock");
    }

    @Override
    public void render(float x, float y) 
    {
        img.draw(x, y);
    }

    @Override
    public boolean canPass() 
    {
        return false;
    }

    @Override
    public Image getImage()
    {
        return img;
    }
}
