package level;

import entity.Coin;
import entity.Entity;
import entity.Item;
import entity.Jonas;
import entity.LightEntity;
import entity.LightItem;
import entity.Refiller;
import entity.Renderable;
import entity.Torch;
import java.util.ArrayList;
import oslo.Main;

/**
 *
 * @author Jeremy Bassi
 */
public class ObjectMap 
{
    public static final int SIGHT_WIDTH = 4;
    
    private int width; //tiles
    private int height; //tiles
    
    private ArrayList<Item> items;
    private ArrayList<Entity> entities;
    private volatile ArrayList<LightSource> lights;
    
    public ObjectMap(int width,int height)
    {
        items = new ArrayList<>();
        entities = new ArrayList<>();
        lights = new ArrayList<>();
    }
    
    /**
     * Add an item or entity to the map
     * @param r - object
     * @return was successful
     */
    public boolean add(Renderable r)
    {
        
        boolean success = true;
        
        if (r instanceof Entity)
        {
            if (r instanceof LightEntity)
            {
                lights.add(((LightEntity) r).getLightSource());
            }
            entities.add((Entity) r);
        }                
        else if (r instanceof Item)
        {
            if (r instanceof LightItem && !(r instanceof Jonas))
            {
                lights.add(((LightItem) r).getLightSource());
            }
            items.add((Item) r);
        }
        else
        {
            success = false;
        }
        
        return success;
    }
    
    
    
    public void update(int delta)
    {
        for (Entity e : entities)
        {
            if (e instanceof Jonas)
            {
                if (((Jonas) e).isDropTorch()) {
                    add(new Torch((int) (((int) ((e.getX() + Jonas.SPRITE_WIDTH / 2) / Main.TILE_WIDTH)) * Main.TILE_WIDTH), (int) (((int)((e.getY() + Jonas.SPRITE_HEIGHT / 2) / Main.TILE_HEIGHT)) * Main.TILE_HEIGHT)));
                }
            }
            
            e.update(delta);
        }
        handleInteractions();
    }
    
    public void handleInteractions()
    {
        //Item interactions
        for (Item i : items)
        {
            for (Entity e : entities)
            {
                if (i.contact(e))
                {
                    i.interact(e);
                }
            }
        }
        
        boolean b = false;
        
        for (int i = 0; i < entities.size() - 1; i++)
        {
            for (int j = 0; j < entities.size() - 1; j++)
            {
                if (entities.get(i) == entities.get(j))
                {
                    continue;
                }

                if (entities.get(i) instanceof Refiller)
                {
                    if (entities.get(i).contact(getJonas()))
                    {
                        b = true;
                    }
                }
                
                if (entities.get(i).contact(entities.get(j)))
                {
                    entities.get(i).interact(entities.get(j));
                }
            }
        }
        
        if (!b)
        {
            getJonas().stopRefill();
        }
    }
    
    public void render(float x, float y)
    {
        for (Item i : items)
        {
            i.render(i.getX() + (int) x, i.getY() + (int) y);
        }
        
        for (Entity e : entities)
        {  
            if (e instanceof LightEntity && ((LightEntity) e).show())
            {
                continue;
            }
            
            e.render(e.getX() + (int) x, e.getY() + (int) y);
        }
    }
    
    public void sweep()
    {
        for (int i = 0; i < entities.size(); i++)
        {
            if (entities.get(i).isRemoved())
            {
                entities.remove(i);
            }
        }
        for (int j = 0; j < items.size(); j++)
        {
            if (items.get(j).isRemoved())
            {
                items.remove(j);
            }
        }
    }
    
    public ArrayList<LightSource> getLightArray()
    {
        return lights;
    }

    public void renderLightEntities(float x, float y) 
    {
        for (Entity e : entities)
        {
            if (e instanceof LightEntity && ((LightEntity)e).show())
            {
                e.render(e.getX() + (int) x, e.getY() + (int) y);
            }
        }
    }
    
    public int getCoins()
    {
        int x = 0;
        for (Item i : items)
        {
            if (i instanceof Coin)
            {
                x++;
            }
        }
        return x;
    }
    
    public Jonas getJonas()
    {
        for (Entity e : entities)
        {
            if (e instanceof Jonas)
            {
                return (Jonas) e;
            }
        }
        
        return null;
    }
}
