package level;

import entity.Item;

/**
 *
 * @author Jeremy Bassi
 */
public abstract class ItemTile extends Tile
{
    public ItemTile(String name)
    {
        super(name);
    }
    
    public abstract Item getItem();
}
