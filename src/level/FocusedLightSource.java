package level;

/**
 * 
 * @author Jeremy
 */
public class FocusedLightSource extends DirectionalLightSource
{
    private int width;
    
    public FocusedLightSource(int x, int y, int lux, int radius, int angle, int width)
    {
        super(x, y, lux, radius, angle);
        this.width = width;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
}
