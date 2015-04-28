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
    private static Image img = Main.textureSheet.getSprite(2, 0);
    
    public RockTile()
    {
        super("rock");
    }

    @Override
    public void render(float x, float y) 
    {
        img.draw((int) x, (int) y);
    }

    @Override
    public boolean canPass() 
    {
        return false;
    }
}
