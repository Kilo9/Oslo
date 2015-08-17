package level;

/**
 * Models a source of light, storing the information to be used to render the light
 * @author Jeremy Bassi
 */
public class LightSource 
{
    private static int num;
            
    //information
    private int lux;
    private int radius;
    private int x;
    private int y;
    private int id;
    
    private boolean isRemoved;
    
    private boolean on;
    
    /**
     * Creates a light source with the given location and size
     * @param x - x position of the light
     * @param y - y position of the light
     * @param lux - the brightness of the light
     * @param radius  - size of the light
     */
    public LightSource(int x, int y, int lux, int radius)
    {
        this.x = x;
        this.y = y;
        this.lux = lux;
        this.radius = radius;
        
        id = num++;
        
        on = true;
        isRemoved = false;
    }

    /**
     * @return the lux
     */
    public int getLux() {
        return lux;
    }

    /**
     * @param lux the lux to set
     */
    public void setLux(int lux) {
        this.lux = lux;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public int getID()
    {
        return id;
    }
    
    public void setMode(boolean on)
    {
        this.on = on;
    }
    
    public boolean isOn()
    {
        return on;
    }
    
    public void remove()
    {
        isRemoved = true;
    }
    
    public boolean isRemoved()
    {
        return isRemoved;
    }
    
    @Override
    public String toString()
    {
        return "lightsource " + id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + this.lux;
        hash = 61 * hash + this.radius;
        hash = 61 * hash + this.x;
        hash = 61 * hash + this.y;
        hash = 61 * hash + this.id;
        return hash;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o instanceof LightSource)
        {
            return getID() == ((LightSource) o).getID();
        }
        
        return false;
    }
}
