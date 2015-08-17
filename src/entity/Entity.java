package entity;

import level.Map;
import level.ObjectMap;

/**
 * General class for all living entities
 * @author Jeremy Bassi
 */
public abstract class Entity extends Renderable
{
    public static final float DEFAULT_MOVE_SPEED = 0.5f;
    private float x;
    private float y;
    
    private Map map;
    private ObjectMap obj;
    
    public Entity(float x, float y, Map map, ObjectMap obj)
    {
        super();
        this.x = x;
        this.y = y;
        this.map = map;
        this.obj = obj;
    }
    
    public abstract void update(int delta);
    
    @Override
    public float getX()
    {
        return x;
    }
    
    @Override
    public float getY()
    {
        return y;
    }
    
    public void setX(float x)
    {
        this.x = x;
    }
    
    public void setY(float y)
    {
        this.y = y;
    }
    
    public boolean canMove(int x, int y)
    {
        if (x < 0 || x + Jonas.SPRITE_WIDTH >= getMap().getPixelWidth() || y < 0 || y + Jonas.SPRITE_HEIGHT >= getMap().getPixelHeight())
        {
            return false;
        }
        
        
        //corner checking
        boolean UL = getMap().getTileAt((int) x, (int) y).canPass();
        boolean BR = getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH), (int) (y + Jonas.SPRITE_HEIGHT)).canPass();
        boolean UR = getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH), (int) y).canPass();
        boolean BL = getMap().getTileAt((int) (x), (int) (y + Jonas.SPRITE_HEIGHT)).canPass();
        
        return UL && BR && UR && BL;
    }
    
    public boolean canMove(int dir, int x, int y)
    {  
        if (dir < 0 || dir > 3)
        {
            return false; //out of range
        }
        
        if (dir == 0) //UP
        {
            if (y < 0 || !getMap().getTileAt((int) getX(), (int) y).canPass() || !getMap().getTileAt((int) getX() + Jonas.SPRITE_WIDTH - 1, (int) y).canPass())
            {
                return false;
            }
            
            return true;
        }
        else if (dir == 1) //RIGHT
        {
            if (x + Jonas.SPRITE_WIDTH - 1 > getMap().getPixelWidth() || getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH - 1), (int) getY()) == null  || !getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH - 1), (int) (getY() + Jonas.SPRITE_HEIGHT - 1)).canPass() || !getMap().getTileAt((int) (x + Jonas.SPRITE_WIDTH - 1), (int) getY()).canPass())
            {
                return false;
            }
            
            return true;
        }
        else if (dir == 2) //DOWN
        {
            if (y + Jonas.SPRITE_HEIGHT - 1 > getMap().getPixelHeight() || getMap().getTileAt((int) getX(), (int) (y + Jonas.SPRITE_HEIGHT - 1)) == null || !getMap().getTileAt((int) (getX() + Jonas.SPRITE_WIDTH - 1), (int) (y + Jonas.SPRITE_HEIGHT - 1)).canPass() || !getMap().getTileAt((int) getX(), (int) (y + Jonas.SPRITE_HEIGHT - 1)).canPass())
            {
                return false;
            }
            
            return true;
        }
        else if (dir == 3) //LEFT
        {
            if (x < 0 || !getMap().getTileAt((int) x, (int) getY()).canPass() || !getMap().getTileAt((int) x, (int) getY() + Jonas.SPRITE_HEIGHT - 1).canPass())
            {
                return false;
            }
            
            return true;
        }
        
        return false;
    }

    /**
     * @return the map
     */
    public Map getMap() {
        return map;
    }

    /**
     * @param map the map to set
     */
    public void setMap(Map map) {
        this.map = map;
    }
    
    public void moveLeft(int delta)
    {
        setX(getX() - delta * Jonas.WALK_SPEED * DEFAULT_MOVE_SPEED);
    }
    
    public void moveRight(int delta)
    {
        setX(getX() + delta * Jonas.WALK_SPEED * DEFAULT_MOVE_SPEED);
    }
    
    public void moveUp(int delta)
    {
        setY(getY() - delta * Jonas.WALK_SPEED * DEFAULT_MOVE_SPEED);
    }
    
    public void moveDown(int delta)
    {
        setY(getY() + delta * Jonas.WALK_SPEED * DEFAULT_MOVE_SPEED);
    }
    
    /**
     * Handle interaction between npc and human entities
     * @param e - the entity with which to interact
     */
    public abstract void interact(Entity e);
    
    public boolean contact(Entity e)
    {
        int half = (Jonas.SPRITE_WIDTH / 2);
        float ex = e.getX() + half;
        float ey = e.getY() + half ;
        float tx = getX();
        float ty = getY();
        float bx = tx + 2 * half;
        float by = ty + 2 * half;
        
        return (ex >= tx && ex <= bx) && (ey >= ty && ey <= by);  
    }
    
    @Override
    public String toString()
    {
        return "entity";
    }
}
