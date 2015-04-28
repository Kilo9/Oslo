/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package level;

import level.Tile;

/**
 *
 * @author Jeremy Bassi
 */
public abstract class AnimationTile extends Tile 
{  
    public AnimationTile(String name)
    {
       super(name);
    }

    public abstract void update(int delta);
}
