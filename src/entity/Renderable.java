package entity;

/**
 * Interface for objects that can be rendered in the game.
 * @author Jeremy Bassi
 */
public abstract class Renderable 
{
    /**
     * flag telling if the object should be removed from the map
     */
    boolean isRemoved;
    
    /**
     * draw the object at the given x and y coordinates
     * @param x - x position relative to map
     * @param y - y position relative to map
     */
    public abstract void render(float x, float y);
    
    /**
     * Retrieve the x position
     * @return x position
     */
    public abstract float getX();
    
    /**
     * Retrieve the y position
     * @return y position
     */
    public abstract float getY();
    
    /**
     * set a flag to have the item removed
     */
    public void remove()
    {
        isRemoved = true;
    }
    
    /**
     * Retrieve the flag telling if the object should be removed
     * @return if the object should be removed
     */
    public boolean isRemoved()
    {
        return isRemoved;
    }
}
