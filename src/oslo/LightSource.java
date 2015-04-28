package oslo;

/**
 *
 * @author Jeremy Bassi
 */
public class LightSource 
{
    private int lux;
    private int radius;
    private int x;
    private int y;
    
    public LightSource(int x, int y, int lux, int radius)
    {
        this.x = x;
        this.y = y;
        this.lux = lux;
        this.radius = radius;
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
}
