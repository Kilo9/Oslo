/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import level.Tile;
import oslo.Main;

/**
 *
 * @author Jeremy
 */
public class DirtTile extends Tile
{
    public DirtTile()
    {
        super("Dirt");
    }

    @Override
    public void render(float x, float y) 
    {
        Main.textureSheet.getSprite(1, 0).draw((int) x, (int) y);
    }

    @Override
    public boolean canPass() 
    {
        return true;
    }
}
